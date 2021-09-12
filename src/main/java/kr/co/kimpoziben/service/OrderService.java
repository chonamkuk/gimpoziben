package kr.co.kimpoziben.service;

import kr.co.kimpoziben.PropertyConfig;
import kr.co.kimpoziben.domain.code.OrderStat;
import kr.co.kimpoziben.domain.entity.Order;
import kr.co.kimpoziben.domain.entity.OrderProduct;
import kr.co.kimpoziben.domain.repository.OrderProductRepository;
import kr.co.kimpoziben.domain.repository.OrderRepository;
import kr.co.kimpoziben.dto.OrderDto;
import kr.co.kimpoziben.dto.OrderProductDto;
import lombok.AllArgsConstructor;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private EmailSendService emailUtil;

    @Autowired
    private PropertyConfig propertyConfig;

    private ModelMapper modelMapper = null;

    private OrderService() {
        modelMapper = new ModelMapper();
    }

    @Transactional
    public List<OrderDto> save(List<OrderDto> cartDtoList) {
        // 주문 데이터 생성
        OrderDto newOrderDto = new OrderDto();
        newOrderDto.setIdCustomer("test"); // todo: 고객아이디 셋팅
        newOrderDto.setStatOrder(OrderStat.A);
        newOrderDto.setDtOrder(LocalDateTime.now());
        Order order = orderRepository.save(modelMapper.map(newOrderDto, Order.class));

        // OrderAddList 의 상품 데이터 생성
        for(OrderDto orderDto : cartDtoList) {
            orderDto.setSeqOrder(order.getSeqOrder());
            for(OrderProductDto orderProductDto : orderDto.getProducts()) {
                orderProductDto.setSeqOrder(orderDto.getSeqOrder());
                OrderProduct orderProduct = orderProductRepository.save(modelMapper.map(orderProductDto, OrderProduct.class));
                orderProductDto.setSeqProductOrder(orderProduct.getSeqProductOrder());
            }
        }

        return cartDtoList;
    }

    @Transactional
    public OrderDto save(OrderDto orderDto) {
        // 주문 데이터 생성
        orderDto.setIdCustomer("test"); // todo: 고객아이디 셋팅
        orderDto.setStatOrder(OrderStat.A);
        orderDto.setDtOrder(LocalDateTime.now());
        Order order = orderRepository.save(modelMapper.map(orderDto, Order.class));
        orderDto.setSeqOrder(order.getSeqOrder());

        // 주문상품 데이터 생성
        for(OrderProductDto orderProductDto : orderDto.getProducts()) {
            orderProductDto.setSeqOrder(orderDto.getSeqOrder());
            OrderProduct orderProduct = orderProductRepository.save(modelMapper.map(orderProductDto, OrderProduct.class));
            orderProductDto.setSeqProductOrder(orderProduct.getSeqProductOrder());
        }
        
        // 엑셀데이터 생성
//        HashMap<String,String> excelPathMap = this.makeExcelPath(orderDto, "invoice");
//        order.setPathFileExcel(excelPathMap.get("excelFileFullPath"));
//        orderDto.setPathFileExcel(excelPathMap.get("excelFileFullPath"));
        // 오류 => 롤백 todo: 롤백테스트

        return orderDto;
    }

    public List<OrderDto> getList(Pageable pageble, HashMap<String,Object> searchMap) throws Exception {
//        HashMap result = new HashMap();
        // todo: 페이징 구현, 고객아이디 셋팅
        List<Order> orderList = orderRepository.findAllByIdCustomerAndStatOrderNotOrderBySeqOrderDesc("test", OrderStat.B);

        List<OrderDto> orderDtoList = new ArrayList();
        for(Order order : orderList) {
            orderDtoList.add(modelMapper.map(order, OrderDto.class));
        }

        return orderDtoList;
    }

    public String makeExcelPath(OrderDto orderDto, String type) {
        StringBuffer excelPathBuf = new StringBuffer();
        excelPathBuf.append(propertyConfig.getExcelPath());
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + type);
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + orderDto.getDtOrder().getYear());
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + orderDto.getDtOrder().getMonthValue());
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + orderDto.getDtOrder().getDayOfMonth());
        Path excelPath = Paths.get(excelPathBuf.toString());

        if (Files.notExists(excelPath)) {
            try {
                Files.createDirectories(excelPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String excelFileName = type+"_"+orderDto.getSeqOrder().toString()+".xlsx";
        String excelFileFullPath = excelPathBuf.toString() + propertyConfig.getFilePathSeperator() + excelFileName;

        return excelFileFullPath;
    }

    public void makeExcel(OrderDto orderDto){

        try {
            InputStream io = new ClassPathResource("excel-template/template_invoice.xlsx").getInputStream();
//            OutputStream os = new FileOutputStream(excelFileFullPath);
            Context context = new Context();
            // todo: session 데이터
            context.putVar("invoiceTel", "invoiceTel");
            context.putVar("invoiceMail", "invoiceMail");
            context.putVar("invoiceAddr", "invoiceAddr");
            context.putVar("invoiceCompany", "invoiceCompany");
            context.putVar("invoiceName", "invoiceName");

            context.putVar("excelDataList", orderDto.getProducts());
            context.putVar("invoiceDate", orderDto.getDtOrder());
            context.putVar("totalPriceOrder", orderDto.getTotalPriceOrder());
            context.putVar("seqOrder", orderDto.getSeqOrder());
//            JxlsHelper.getInstance().processTemplate(io, os, context);

            // todo: 엑셀경로 update

            // 관리자에게 전송
            // 주문일시
            // 주문번호
            // 상호
            // 주문이 접수되었습니다.
//            emailUtil.sendEmail("unasd@daum.net", "paralysist@naver.com"
//                    ,"주문", "냉무", excelFileFullPath, excelFileName);

            // todo: 주문자에게 전송
            // 주문일시
            // 주문번호
            // 상호
            // 주문이 완료되었습니다.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

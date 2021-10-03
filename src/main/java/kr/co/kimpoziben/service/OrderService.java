package kr.co.kimpoziben.service;

import kr.co.kimpoziben.config.PropertyConfig;
import kr.co.kimpoziben.config.auth.SessionUser;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    private ModelMapper modelMapper = null;

    private OrderService() {
        modelMapper = new ModelMapper();
    }


    @Transactional
    public OrderDto save(OrderDto orderDto, SessionUser user) throws Exception {
        // 주문 데이터 생성
        orderDto.setIdCustomer(user.getId());
        orderDto.setStatOrder(OrderStat.A);
        orderDto.setDtOrder(LocalDateTime.now());
        Order order = orderRepository.save(modelMapper.map(orderDto, Order.class));
        orderDto.setSeqOrder(order.getSeqOrder());

        // 주문상품 데이터 생성
        for(OrderProductDto orderProductDto : orderDto.getProducts()) {
            orderProductDto.setSeqOrder(orderDto.getSeqOrder());
            OrderProduct orderProduct = orderProductRepository.save(modelMapper.map(orderProductDto, OrderProduct.class));
        }

        // 주문상품 재조회: 상품명, 사이즈명 포함하여 조회
        HashMap<String,Object> searchMap = new HashMap<String,Object> ();
        searchMap.put("seqOrder", orderDto.getSeqOrder());
        orderDto.setProducts(orderProductRepository.findOrderProductByOrderId(searchMap));
        
        // 엑셀데이터 생성
        String excelPath = this.makeExcelPath("invoice");
        String excelFileName = "invoice" + "_" + orderDto.getSeqOrder().toString() +".xlsx";
        order.setPathFileExcel(excelPath + propertyConfig.getFilePathSeperator() + excelFileName);
        orderDto.setPathFileExcel(excelPath + propertyConfig.getFilePathSeperator() + excelFileName);
        this.makeExcel(orderDto, user);

        // 관리자에게 메일발송
        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
        context.setVariable("orderDto", orderDto);
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        context.setVariable("scheme", req.getScheme());
        context.setVariable("serverName", req.getServerName());
        context.setVariable("serverPort", req.getServerPort());

        context.setVariable("mailHeader", "아래와 같이 주문이 접수되었습니다.");
        String htmlTemplate = htmlTemplateEngine.process("order/mail-invoice", context);
        emailUtil.sendEmail("paralysist@naver.com", "paralysist@naver.com","김포지벤 - 주문접수",
                            orderDto.getPathFileExcel(), excelFileName, htmlTemplate);

        // 고객에게 메일발송
        context.setVariable("mailHeader", "아래와 같이 주문이 완료되었습니다.");
        htmlTemplate = htmlTemplateEngine.process("order/mail-invoice", context);
        emailUtil.sendEmail(user.getEmail(), "paralysist@naver.com","김포지벤 - 주문접수",
                            orderDto.getPathFileExcel(), excelFileName, htmlTemplate);

        return orderDto;
    }

    public List<OrderDto> getList(Pageable pageble, HashMap<String,Object> searchMap) throws Exception {
        List<Order> orderList = orderRepository.findAllByIdCustomerAndStatOrderNotOrderBySeqOrderDesc((Long)searchMap.get("idCustomer"), OrderStat.B);

        List<OrderDto> orderDtoList = new ArrayList();
        for(Order order : orderList) {
            orderDtoList.add(modelMapper.map(order, OrderDto.class));
        }

        return orderDtoList;
    }

    public String makeExcelPath(String type) {
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuffer excelPathBuf = new StringBuffer();
        excelPathBuf.append(propertyConfig.getExcelPath());
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + type);
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getYear());
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getMonthValue());
        excelPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getDayOfMonth());
        Path excelPath = Paths.get(excelPathBuf.toString());

        if (Files.notExists(excelPath)) {
            try {
                Files.createDirectories(excelPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return excelPath.toString();
    }

    public void makeExcel(OrderDto orderDto, SessionUser user) throws Exception {
        InputStream io = null;
        OutputStream os = null;

        try {
            io = new ClassPathResource("templates-excel/excel-invoice.xlsx").getInputStream();
            os = new FileOutputStream(orderDto.getPathFileExcel());
            Context context = new Context();
            context.putVar("invoiceTel", user.getMobile());
            context.putVar("invoiceMail", user.getEmail());
            context.putVar("invoiceAddr", user.getAddrCompany());
            context.putVar("invoiceCompany", user.getNmCompany());
            context.putVar("invoiceName", user.getName());

            context.putVar("excelDataList", orderDto.getProducts());
            context.putVar("invoiceDate", orderDto.getDtOrder());
            context.putVar("totalPriceOrder", orderDto.getTotalPriceOrder());
            context.putVar("seqOrder", orderDto.getSeqOrder());
            JxlsHelper.getInstance().processTemplate(io, os, context);
        } catch (Exception e) {
            if (io != null) {io.close();}
            if (os != null) {os.close();}
            throw e;
        }
    }
}

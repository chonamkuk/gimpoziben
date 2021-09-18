package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.domain.entity.OrderProduct;
import kr.co.kimpoziben.dto.*;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.service.ProductService;
import kr.co.kimpoziben.service.SizeService;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private ProductService productService;
    private SizeService sizeService;
    private AttachService attachService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            , @RequestParam(value = "seqCategory", required = false) Long seqCategory
            , @RequestParam(value = "seqUpperCategory", required = false) Long seqUpperCategory) throws Exception {
        pageable.setListSize(9);
        pageable.setDirection(Sort.Direction.DESC);
        pageable.setSortProp("seqProduct");
        searchMap.put("ynDisplay", "Y");
        searchMap.put("seqCategory", seqCategory);
        searchMap.put("seqUpper", seqUpperCategory);
        HashMap result = productService.getList(pageable.of(), searchMap);

        model.addAttribute("resultList", result.get("resultList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("pagingResult")));
        model.addAttribute("searchMap", searchMap);

        return "shop/list";
    }

    @GetMapping("/detail.do")
    public @ResponseBody Object detail(@RequestParam("seqProduct") Long seqProduct, @RequestParam("idMainImg") String idMainImg) throws  Exception {
        HashMap resultMap = new HashMap();

        List<AttachDto> attachDtoList = null;

        attachDtoList = attachService.getAttachInfoList(idMainImg);
        resultMap.put("sizeList", sizeService.findBySeqProduct(seqProduct));
        resultMap.put("attachDtoList", attachDtoList);
        return resultMap;
    }

    @GetMapping("/detail2.do")
    public @ResponseBody Object detail2(@RequestParam("seqProduct") Long seqProduct) throws  Exception {
        HashMap resultMap = new HashMap();
        resultMap.put("productDetail", productService.getProductDetail(seqProduct));
        return resultMap;
    }

    @GetMapping("/cartList.do")
    public String cartList() throws Exception {
        return "shop/cartList";
    }

    @GetMapping("/mailTest.do")
    public String mailTest(Model model) throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setSeqOrder(28L);
        orderDto.setTotalPriceOrder(404000L);
        orderDto.setDtOrder(LocalDateTime.now());
        orderDto.setIdCustomer("test");

        List<OrderProductDto> orderProducts = new ArrayList<>();
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setNmProduct("ZB-123123");
        orderProductDto.setNmSize("M");
        orderProductDto.setIdMainImg("20210627150744-product-50685");
        orderProducts.add(orderProductDto);

        OrderProductDto orderProductDto1 = new OrderProductDto();
        orderProductDto1.setNmProduct("ZB-010101");
        orderProductDto.setNmSize("M");
        orderProducts.add(orderProductDto1);

        orderDto.setProducts(orderProducts);

        model.addAttribute("orderDto", orderDto);
        model.addAttribute("mailHeader", "아래와 같이 주문이 완료되었습니다.");

        return "order/mail-invoice";
    }
}

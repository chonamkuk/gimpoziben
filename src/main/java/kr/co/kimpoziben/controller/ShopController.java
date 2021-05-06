package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.service.ProductService;
import kr.co.kimpoziben.test.domain.code.AsStat;
import kr.co.kimpoziben.test.dto.AsDto;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private ProductService productService;
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

//        pageable.pagination((Page) result.get("resultPage"));
        model.addAttribute("resultList", result.get("resultList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("pagingResult")));
        model.addAttribute("searchMap", searchMap);

        return "shop/list";
    }

    @GetMapping("/detail.do")
    public @ResponseBody Object detail(@RequestParam("seqProduct") Long seqProduct) throws  Exception {
        HashMap resultMap = new HashMap();

        ProductDto productDto = productService.getProductDetail(seqProduct);
        List<AttachDto> attachDtoList = null;

        if(productDto != null) {
            attachDtoList = attachService.getAttachInfoList(productDto.getIdMainImg()); // todo: file의 실제경로가 노출됨
            resultMap.put("productDto", productDto);
            resultMap.put("attachDtoList", attachDtoList);
            return resultMap;
        } else {
            return null;
        }
    }
}

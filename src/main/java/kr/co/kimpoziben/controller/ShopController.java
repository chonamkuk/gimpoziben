package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.service.ProductService;
import kr.co.kimpoziben.test.domain.code.AsStat;
import kr.co.kimpoziben.test.dto.AsDto;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private ProductService productService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, HashMap<String,Object> searchMap) throws Exception {
        pageable.setSortProp("nmProduct");
        searchMap.put("ynDisplay", "Y");
        HashMap result = productService.getList(pageable.of(), searchMap);

//        pageable.pagination((Page) result.get("resultPage"));
        model.addAttribute("resultList", result.get("resultList"));
        model.addAttribute("resultPage", pageable.pagination((Page) result.get("resultPage")));
        model.addAttribute("searchMap", searchMap);

        return "shop/list";
    }

    @PostMapping("/admin/write.do")
    public String write(ProductDto productDto, String[] image, String[] imageName, String[] imageSize) throws Exception {
        productDto.setRegister("admin");
        productService.save(productDto, image, imageName, imageSize);
        return "redirect:/shop/list.do";
    }

    @GetMapping("/admin/write.do")
    public String write() {
        return "/shop/write";
    }
}

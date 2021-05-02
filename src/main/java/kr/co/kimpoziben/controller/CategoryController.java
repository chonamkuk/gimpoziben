package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/childList.do")
    public @ResponseBody Object getChildList(@RequestParam("seqUpper") Long seqUpper) throws Exception {
        return categoryService.getChildList(seqUpper);
    }
}

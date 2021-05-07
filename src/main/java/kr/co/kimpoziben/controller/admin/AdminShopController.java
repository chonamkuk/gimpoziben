package kr.co.kimpoziben.controller.admin;

import kr.co.kimpoziben.dto.ImageUploadDto;
import kr.co.kimpoziben.dto.ProdSizeDto;
import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.SizeDto;
import kr.co.kimpoziben.service.CategoryService;
import kr.co.kimpoziben.service.ProductService;
import kr.co.kimpoziben.service.SizeService;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/shop")
public class AdminShopController {

    private ProductService productService;
    private SizeService sizeService;
    private CategoryService categoryService;

    @PostMapping("/write.do")
    public String write(@ModelAttribute ProductDto productDto) throws Exception {
        productDto.setRegister("admin");
        productService.save(productDto);
        return "redirect:/shop/list.do";
    }

    @GetMapping("/write.do")
    public String write(Model model) throws Exception {
        model.addAttribute("upperSizeList", sizeService.getUpperList());
        model.addAttribute("parentCategoryList", categoryService.getParentList());
        return "admin/shop/write";
    }
}

package kr.co.kimpoziben.controller.admin;

import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.service.CategoryService;
import kr.co.kimpoziben.service.ProductService;
import kr.co.kimpoziben.service.SizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/shop")
public class AdminShopController {

    private ProductService productService;
    private SizeService sizeService;
    private CategoryService categoryService;
    private AttachService attachService;

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

    @GetMapping("/update.do")
    public String update(Model model, @RequestParam("seqProduct") Long seqProduct) throws Exception {
        ProductDto productDto = productService.getProductDetail(seqProduct);

        if(productDto != null) {
            model.addAttribute("resultDto", productDto);
            model.addAttribute("attachDtoList", attachService.getAttachInfoList(productDto.getIdMainImg()));// todo: file의 실제경로가 노출됨
            model.addAttribute("upperSizeList", sizeService.getUpperList());
            model.addAttribute("parentCategoryList", categoryService.getParentList());
        }

        return "admin/shop/update";
    }
}

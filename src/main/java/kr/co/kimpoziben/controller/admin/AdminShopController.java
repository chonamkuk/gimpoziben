package kr.co.kimpoziben.controller.admin;

import kr.co.kimpoziben.dto.*;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.service.CategoryService;
import kr.co.kimpoziben.service.ProductService;
import kr.co.kimpoziben.service.SizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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
        productDto.setRegDt(LocalDateTime.now());
        productService.save(productDto);
        return "redirect:/shop/list.do";
    }

    @GetMapping("/write.do")
    public String write(Model model) throws Exception {
        model.addAttribute("upperSizeList", sizeService.getHierarchyList());
        model.addAttribute("parentCategoryList", categoryService.getParentList());
        return "admin/shop/write";
    }

    @GetMapping("/update.do")
    public String update(Model model, @RequestParam("seqProduct") Long seqProduct) throws Exception {
        ProductDto productDto = productService.getProductDetail(seqProduct);

        if(productDto != null) {
            model.addAttribute("resultDto", productDto);
            model.addAttribute("upperSizeList", sizeService.getHierarchyList());
            model.addAttribute("parentCategoryList", categoryService.getParentList());
            model.addAttribute("productCategoryList", categoryService.findBySeqProduct(seqProduct));
        }

        return "admin/shop/update";
    }

    @PostMapping("/update.do")
    public String update(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttr) throws Exception {
        productDto.setModifier("admin");
        productDto.setModDt(LocalDateTime.now());
        productService.update(productDto);
        redirectAttr.addAttribute("seqProduct", productDto.getSeqProduct());
        return "redirect:/admin/shop/update.do";
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
}

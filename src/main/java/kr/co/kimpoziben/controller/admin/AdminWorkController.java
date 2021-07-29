package kr.co.kimpoziben.controller.admin;

import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.ProductWorkDto;
import kr.co.kimpoziben.service.*;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/work")
public class AdminWorkController {

    private ProductService productService;
    private WorkProductService workProductService;
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
    public String update(Model model, @RequestParam("seqProduct") Long seqProduct,
                         HashMap<String,Object> searchMap, final PageRequest pageable) throws Exception {
        ProductDto productDto = productService.getProductDetail(seqProduct);

        if(productDto != null) {
            model.addAttribute("resultDto", productDto);
            model.addAttribute("upperSizeList", sizeService.getHierarchyList());
            model.addAttribute("parentCategoryList", categoryService.getParentList());
            model.addAttribute("productCategoryList", categoryService.findBySeqProduct(seqProduct));
            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
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
    public @ResponseBody Object detail(@RequestParam("seqWorkProduct") Long seqWorkProduct, @RequestParam("idMainImg") String idMainImg) throws  Exception {
        HashMap resultMap = new HashMap();

        List<AttachDto> attachDtoList = null;

        attachDtoList = attachService.getAttachInfoList(idMainImg);

        resultMap.put("attachDtoList", attachDtoList);
        return resultMap;
    }

    @GetMapping("/list.do")
    public String adminShopList(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            , @RequestParam(value = "seqCategory", required = false) Long seqCategory
            , @RequestParam(value = "seqUpperCategory", required = false) Long seqUpperCategory) throws Exception {
        pageable.setListSize(9);
        pageable.setDirection(Sort.Direction.DESC);
        pageable.setSortProp("seqProduct");
        searchMap.put("seqCategory", seqCategory);
        searchMap.put("seqUpper", seqUpperCategory);
        HashMap result = productService.getList(pageable.of(), searchMap);

        model.addAttribute("resultList", result.get("resultList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("pagingResult")));
        model.addAttribute("searchMap", searchMap);

        return "admin/shop/list";
    }


    @GetMapping("/jasuWrite.do")
    public String jasuWrite(Model model) throws Exception {
       // model.addAttribute("upperSizeList", sizeService.getUpperList());
       // model.addAttribute("parentCategoryList", categoryService.getParentList());
        return "admin/work/jasuWrite";
    }

    @PostMapping("/jasuWrite.do")
    public String jasuWrite(@ModelAttribute ProductWorkDto productWorkDto) throws Exception {
        productWorkDto.setRegister("admin");
        workProductService.save(productWorkDto);
        return "redirect:/admin/work/jasulist.do";
    }

    /*
    * 1.jasuSeq를 갖고 뿌린다.
    * 2.update 화면에 이미지 액션명 매핑 수정하여 뿌려준다
    * 
    */
    @GetMapping("/jasuUpdate.do")
    public String jasuUpdate(Model model, @RequestParam("seqWorkProduct") Long seqWorkProduct,
                         HashMap<String,Object> searchMap, final PageRequest pageable) throws Exception {
        ProductWorkDto productWorkDto = workProductService.getProductDetail(seqWorkProduct);

        if(productWorkDto != null) {
            model.addAttribute("resultDto", productWorkDto);
            model.addAttribute("upperSizeList", sizeService.getHierarchyList());
            model.addAttribute("parentCategoryList", categoryService.getParentList());
            model.addAttribute("productCategoryList", categoryService.findBySeqProduct(seqWorkProduct));
            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
        }

        return "admin/work/jasuUpdate";
    }

    @PostMapping("/jasuUpdate.do")
    public String jasuUpdate(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttr) throws Exception {
        productDto.setModifier("admin");
        productDto.setModDt(LocalDateTime.now());
        productService.update(productDto);
        redirectAttr.addAttribute("seqProduct", productDto.getSeqProduct());
        return "redirect:/admin/shop/update.do";
    }

    

    @GetMapping("/jasulist.do")
    public String list2(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            , @RequestParam(value = "seqCategory", required = false) Long seqCategory
            , @RequestParam(value = "seqUpperCategory", required = false) Long seqUpperCategory) throws Exception {

        HashMap result =  workProductService.findWorkList();
        model.addAttribute("workList", result.get("workList"));
        return "admin/work/jasuList";
    }

}

package kr.co.kimpoziben.controller.admin;

import kr.co.kimpoziben.dto.*;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.service.CategoryService;
import kr.co.kimpoziben.service.ProductService;
import kr.co.kimpoziben.service.WorkProductService;
import kr.co.kimpoziben.service.SizeService;
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
@RequestMapping("/admin/shop")
public class AdminShopController {

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
    public @ResponseBody Object detail(@RequestParam("seqProduct") Long seqProduct, @RequestParam("idMainImg") String idMainImg) throws  Exception {
        HashMap resultMap = new HashMap();

        List<AttachDto> attachDtoList = null;

        attachDtoList = attachService.getAttachInfoList(idMainImg);
        resultMap.put("sizeList", sizeService.findBySeqProduct(seqProduct));
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
        return "admin/shop/jasuWrite";
    }

    @PostMapping("/jasuWrite.do")
    public String jasuWrite(@ModelAttribute ProductWorkDto productWorkDto) throws Exception {
        productWorkDto.setRegister("admin");
        workProductService.save(productWorkDto);
        return "redirect:/shop/list.do";
    }

    @GetMapping("/jasulist2.do")
    public String list(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            , @RequestParam(value = "seqCategory", required = false) Long seqCategory
            , @RequestParam(value = "seqUpperCategory", required = false) Long seqUpperCategory) throws Exception {


        /*pageable.setListSize(9);
        pageable.setDirection(Sort.Direction.DESC);
        pageable.setSortProp("seqProduct");
        searchMap.put("ynDisplay", "Y");
        searchMap.put("seqCategory", seqCategory);
        searchMap.put("seqUpper", seqUpperCategory);
        HashMap result = productService.getList(pageable.of(), searchMap);

        model.addAttribute("resultList", result.get("resultList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("pagingResult")));
        model.addAttribute("searchMap", searchMap);*/

        return "admin/shop/data-table";
    }

    @GetMapping("/jasulist.do")
    public String list2(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            , @RequestParam(value = "seqCategory", required = false) Long seqCategory
            , @RequestParam(value = "seqUpperCategory", required = false) Long seqUpperCategory) throws Exception {

        /* 1. 리스트를 만들어 화면에 뿌린다. 페이징은 기본 맵에서 처리할수 있도록
        *  2. 헤더만 따로 뗄수있는지 여부
        *  3. 시퀀스 여부 확인할것
        *
        * */


        /*pageable.setListSize(9);
        pageable.setDirection(Sort.Direction.DESC);
        pageable.setSortProp("seqProduct");
        searchMap.put("ynDisplay", "Y");
        searchMap.put("seqCategory", seqCategory);
        searchMap.put("seqUpper", seqUpperCategory);
        HashMap result = productService.getList(pageable.of(), searchMap);

        model.addAttribute("resultList", result.get("resultList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("pagingResult")));
        model.addAttribute("searchMap", searchMap);*/

        return "admin/shop/table-data-table";
    }

}

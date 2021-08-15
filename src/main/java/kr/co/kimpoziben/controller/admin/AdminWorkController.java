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
    private AttachService attachService;

    @GetMapping("/detail.do")
    public @ResponseBody Object detail(@RequestParam("seqWorkProduct") Long seqWorkProduct, @RequestParam("idMainImg") String idMainImg) throws  Exception {
        HashMap resultMap = new HashMap();

        List<AttachDto> attachDtoList = null;

        attachDtoList = attachService.getAttachInfoList(idMainImg);

        resultMap.put("attachDtoList", attachDtoList);
        return resultMap;
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
        productWorkDto.setTypeWorkProduct("J");
        workProductService.save(productWorkDto);
        return "redirect:/admin/work/jasuList.do";
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

        }

        return "admin/work/jasuUpdate";
    }

    @PostMapping("/jasuUpdate.do")
    public String jasuUpdate(@ModelAttribute ProductWorkDto productWorkDto, RedirectAttributes redirectAttr) throws Exception {
        productWorkDto.setModifier("admin");
        productWorkDto.setModDt(LocalDateTime.now());

        workProductService.update(productWorkDto);
        redirectAttr.addAttribute("seqWorkProduct", productWorkDto.getSeqWorkProduct());
        return "redirect:/admin/work/jasuUpdate.do";
    }

    

    @GetMapping("/jasuList.do")
    public String list2(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            , @RequestParam(value = "seqCategory", required = false) Long seqCategory
            , @RequestParam(value = "seqUpperCategory", required = false) Long seqUpperCategory) throws Exception {

        HashMap result =  workProductService.findWorkList("J");
        model.addAttribute("workList", result.get("workList"));
        return "admin/work/jasuList";
    }



    @GetMapping("/wanjangWrite.do")
    public String wanjangWrite(Model model) throws Exception {
        // model.addAttribute("upperSizeList", sizeService.getUpperList());
        // model.addAttribute("parentCategoryList", categoryService.getParentList());
        return "admin/work/wanjangWrite";
    }

    @PostMapping("/wanjangWrite.do")
    public String wanjangWrite(@ModelAttribute ProductWorkDto productWorkDto) throws Exception {
        productWorkDto.setRegister("admin");
        productWorkDto.setTypeWorkProduct("W");
        workProductService.save(productWorkDto);
        return "redirect:/admin/work/wanjangList.do";
    }

    /*
     * 1.jasuSeq를 갖고 뿌린다.
     * 2.update 화면에 이미지 액션명 매핑 수정하여 뿌려준다
     *
     */
    @GetMapping("/wanjangUpdate.do")
    public String wanjangUpdate(Model model, @RequestParam("seqWorkProduct") Long seqWorkProduct,
                             HashMap<String,Object> searchMap, final PageRequest pageable) throws Exception {
        ProductWorkDto productWorkDto = workProductService.getProductDetail(seqWorkProduct);

        if(productWorkDto != null) {
            model.addAttribute("resultDto", productWorkDto);
            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
        }

        return "admin/work/wanjangUpdate";
    }

    @PostMapping("/wanjangUpdate.do")
    public String wanjangUpdate(@ModelAttribute ProductWorkDto productWorkDto, RedirectAttributes redirectAttr) throws Exception {
        productWorkDto.setModifier("admin");
        productWorkDto.setModDt(LocalDateTime.now());

        workProductService.update(productWorkDto);
        redirectAttr.addAttribute("seqWorkProduct", productWorkDto.getSeqWorkProduct());
        return "redirect:/admin/work/wanjangUpdate.do";
    }



    @GetMapping("/wanjangList.do")
    public String wanjanglist(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            , @RequestParam(value = "seqCategory", required = false) Long seqCategory
            , @RequestParam(value = "seqUpperCategory", required = false) Long seqUpperCategory) throws Exception {

        HashMap result =  workProductService.findWorkList("W");
        model.addAttribute("workList", result.get("workList"));
        return "admin/work/wanjangList";
    }

}

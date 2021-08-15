package kr.co.kimpoziben.controller.admin;

import kr.co.kimpoziben.dto.ProductWorkDto;
import kr.co.kimpoziben.dto.NoticeDto;
import kr.co.kimpoziben.service.*;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/customer")
public class AdminCustomerController {

    private ProductService productService;
    private NoticeService noticeService;
    private WorkProductService workProductService;

    private AttachService attachService;

    @GetMapping("/faqList.do")
    public String write(Model model) throws Exception {

        return "admin/customer/faqList";
    }




    @GetMapping("/noticeWrite.do")
    public String noticeWrite(Model model) throws Exception {
        return "admin/customer/noticeWrite";
    }

    @PostMapping("/noticeWrite.do")
    public String noticeWrite(@ModelAttribute NoticeDto noticeDto) throws Exception {
        noticeDto.setRegister("admin");

        noticeService.save(noticeDto);
        return "redirect:/customer/noticeList.do";
    }

    @GetMapping("/noticeList.do")
    public String list2(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
            ) throws Exception {

        HashMap result =  noticeService.findNoticeList();
        model.addAttribute("noticeList", result.get("noticeList"));
        return "admin/customer/noticeList";
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

            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
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

    






}

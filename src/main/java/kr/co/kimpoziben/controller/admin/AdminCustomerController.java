package kr.co.kimpoziben.controller.admin;

import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.dto.QnaDto;
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
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/customer")
public class AdminCustomerController {

    private ProductService productService;
    private NoticeService noticeService;
    private QnaService qnaService;
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

    @GetMapping("/detail.do")
    public @ResponseBody Object detail(@RequestParam("seqNotice") Long seqNotice, @RequestParam("idMainImg") String idMainImg) throws  Exception {
        HashMap resultMap = new HashMap();

        List<AttachDto> attachDtoList = null;

        attachDtoList = attachService.getAttachInfoList(idMainImg);

        resultMap.put("attachDtoList", attachDtoList);
        return resultMap;
    }
    @GetMapping("/noticeDetail.do")
    public String noticeDetail(Model model, @RequestParam("seqNotice") Long seqNotice,
                            HashMap<String,Object> searchMap, final PageRequest pageable) throws Exception {
        NoticeDto noticeDto = noticeService.getNoticeDetail(seqNotice);
        if(noticeDto != null) {
            model.addAttribute("resultDto", noticeDto);

            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
        }

        return "admin/customer/noticeDetail";
    }

    /*
    * 1.noticeSeq를 갖고 뿌린다.
    * 2.update 화면에 이미지 액션명 매핑 수정하여 뿌려준다
    * 
    */
    @GetMapping("/noticeUpdate.do")
    public String noticeUpdate(Model model, @RequestParam("seqNotice") Long seqNotice,
                         HashMap<String,Object> searchMap, final PageRequest pageable) throws Exception {
        NoticeDto noticeDto = noticeService.getNoticeDetail(seqNotice);

        if(noticeDto != null) {
            model.addAttribute("resultDto", noticeDto);

            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
        }

        return "admin/customer/noticeUpdate";
    }

    @PostMapping("/noticeUpdate.do")
    public String noticeUpdate(@ModelAttribute NoticeDto noticeDto, RedirectAttributes redirectAttr) throws Exception {
        noticeDto.setModifier("admin");
        noticeDto.setModDt(LocalDateTime.now());

        noticeService.update(noticeDto);
        redirectAttr.addAttribute("seqNotice", noticeDto.getSeqNotice());
        return "redirect:/customer/noticeDetail.do";
    }

    @GetMapping("/qnaWrite.do")
    public String qnaWrite(Model model) throws Exception {
        return "admin/customer/qnaWrite";
    }

    @PostMapping("/qnaWrite.do")
    public String qnaWrite(@ModelAttribute QnaDto qnaDto) throws Exception {
        qnaDto.setRegister("admin");

        qnaService.save(qnaDto);
        return "redirect:/customer/qnaList.do";
    }

    @GetMapping("/qnaList.do")
    public String list(Model model, final PageRequest pageable, HashMap<String,Object> searchMap
    ) throws Exception {

        HashMap result =  qnaService.findQnaList();
        model.addAttribute("qnaList", result.get("qnaList"));
        return "admin/customer/qnaList";
    }

    @GetMapping("/detail2.do")
    public @ResponseBody Object qnaDetail(@RequestParam("seqQna") Long seqQna, @RequestParam("idMainImg") String idMainImg) throws  Exception {
        HashMap resultMap = new HashMap();

        List<AttachDto> attachDtoList = null;

        attachDtoList = attachService.getAttachInfoList(idMainImg);

        resultMap.put("attachDtoList", attachDtoList);
        return resultMap;
    }

    @GetMapping("/qnaDetail.do")
    public String qnaDetail(Model model, @RequestParam("seqQna") Long seqQna,
                            HashMap<String,Object> searchMap, final PageRequest pageable) throws Exception {
        QnaDto qnaDto = qnaService.getQnaDetail(seqQna);

        if(qnaDto != null) {
            model.addAttribute("resultDto", qnaDto);

            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
        }

        return "admin/customer/qnaDetail";
    }

    @GetMapping("/qnaUpdate.do")
    public String qnaUpdate(Model model, @RequestParam("seqQna") Long seqQna,
                             HashMap<String,Object> searchMap, final PageRequest pageable) throws Exception {
        QnaDto qnaDto = qnaService.getQnaDetail(seqQna);

        if(qnaDto != null) {
            model.addAttribute("resultDto", qnaDto);

            model.addAttribute("searchDto", searchMap);
            model.addAttribute("pagingResult", pageable);
        }

        return "admin/customer/qnaUpdate";
    }

    @PostMapping("/qnaUpdate.do")
    public String qnaUpdate(@ModelAttribute QnaDto qnaDto, RedirectAttributes redirectAttr) throws Exception {
        qnaDto.setModifier("admin");
        qnaDto.setModDt(LocalDateTime.now());

        qnaService.update(qnaDto);
        redirectAttr.addAttribute("seqQna", qnaDto.getSeqQna());

         qnaDto = qnaService.getQnaDetail(qnaDto.getSeqQna());


        return "redirect:/customer/qnaDetail.do";
    }



}

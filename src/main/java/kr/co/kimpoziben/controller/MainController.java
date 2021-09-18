package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.dto.NoticeDto;
import kr.co.kimpoziben.dto.QnaDto;
import kr.co.kimpoziben.service.*;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor

public class MainController {

    private ProductService productService;
    private NoticeService noticeService;
    private QnaService qnaService;
    private WorkProductService workProductService;

    private AttachService attachService;



    @RequestMapping(value="/" )
    public String index() {
        return "redirect:/main.do";
    }


    @GetMapping("main.do")
     public String list(Model model, final PageRequest pageable, HashMap<String,Object> searchMap ) throws Exception {

            HashMap result =  noticeService.findNoticeMainList();
            model.addAttribute("popupList", result.get("noticeList"));

        HashMap result2 =  noticeService.findNoticeMainSmallList();
        model.addAttribute("smallNoticeList", result2.get("smallNoticeList"));



            return "main/main";
     }

}

package kr.co.kimpoziben.test.controller;

import kr.co.kimpoziben.test.dto.IjDto;
import kr.co.kimpoziben.test.dto.RpDto;
import kr.co.kimpoziben.test.service.RpService;
import kr.co.kimpoziben.util.AES256Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@RequestMapping("test/admin/rp")
public class AdminRpController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private RpService rpService;

    /**
     * RP작성
     * @param ijDto
       @param rpDto
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */

    @PostMapping("/write.do")
    public String write(Model model, IjDto ijDto, RpDto rpDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {

        redirectAttr.addAttribute("seqAs", ijDto.getSeqAs());
        redirectAttr.addAttribute("passwordAs", ijDto.getPasswordAs());
        rpDto.setIdRp(ijDto.getSeqAs());

        rpDto.setCommentRp(rpDto.getCommentRp());
        rpDto.setRegDtRp(LocalDateTime.now());
        rpDto.setUserRp("A");
        rpDto.setYnDelRp("N");

        rpService.saveAs(rpDto);

        return "redirect:/test/admin/ij/detail.do";
    }

    public String update(Model model, IjDto ijDto, RpDto rpDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {

        redirectAttr.addAttribute("seqAs", ijDto.getSeqAs());
        redirectAttr.addAttribute("passwordAs", ijDto.getPasswordAs());
        rpDto.setIdRp(ijDto.getSeqAs());

        rpDto.setCommentRp(rpDto.getCommentRp());
        rpDto.setRegDtRp(LocalDateTime.now());
        rpDto.setUserRp("A");

        rpService.updateRp(rpDto);

        return "redirect:/test/admin/ij/detail.do";
    }


    /**
     * RP삭제
     * @param ijDto
     * @param rpDto
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/delete.do")
    public String delete(Model model, IjDto ijDto, RpDto rpDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {
        redirectAttr.addAttribute("seqAs", ijDto.getSeqAs());
        redirectAttr.addAttribute("passwordAs", ijDto.getPasswordAs());

        rpService.updateYnDelRp(rpDto.getSeqRp());
        return "redirect:/test/admin/ij/detail.do";

    }
}

package kr.co.kimpoziben.test.controller;

import kr.co.kimpoziben.test.dto.RpDto;
import kr.co.kimpoziben.test.service.RpService;
import kr.co.kimpoziben.util.AES256Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/test/rp")
public class RpController {
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
    /**
    @PostMapping("/write.do")
    public String write(Model model, IjDto ijDto, RpDto rpDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {

        redirectAttr.addAttribute("seqAs", ijDto.getSeqAs());
        redirectAttr.addAttribute("passwordAs", ijDto.getPasswordAs());
        rpDto.setIdRp(ijDto.getSeqAs());

        rpDto.setCommentRp(rpDto.getCommentRp());
        rpDto.setRegDtRp(LocalDateTime.now());
        rpDto.setUserRp("U");
        rpDto.setYnDelRp("N");

        rpService.saveAs(rpDto);

        return "redirect:/ij/detail.do";
    }
     */
    //답글 Ajax
    /**
     * RP작성
     * @param rpDto
     * @param response
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/write.do")
    @ResponseBody
    public Object write(@RequestBody RpDto rpDto, HttpServletResponse response, RedirectAttributes redirectAttr ) throws Exception {

        rpDto.setIdRp(rpDto.getIdRp());
        rpDto.setCommentRp(rpDto.getCommentRp());
        rpDto.setRegDtRp(LocalDateTime.now());
        rpDto.setUserRp("U");
        rpDto.setYnDelRp("N");

        Long seqRp = rpService.saveAs(rpDto);
        rpDto.setSeqRp(seqRp);
        List<RpDto> rpDtoList = rpService.getRpList(rpDto.getIdRp());

        //boolean result  = true;
        //return rpDto.getCommentRp();

        return rpDtoList;

    }

    /**
     * RP 수정
     * @param rpDto
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/update.do")
    @ResponseBody
    public Object update(@RequestBody RpDto rpDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {

        rpService.updateCommentRp(rpDto.getSeqRp(), rpDto.getCommentRp());
        boolean result  = true;
        //return rpDto.getCommentRp();
        return result;
    }

    /**
     * RP삭제
     * @param rpDto
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/delete.do")
    @ResponseBody
    public Object delete(@RequestBody RpDto rpDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {
        //redirectAttr.addAttribute("seqAs", ijDto.getSeqAs());
        //redirectAttr.addAttribute("passwordAs", ijDto.getPasswordAs());

        rpService.updateYnDelRp(rpDto.getSeqRp());
        boolean result  = true;
        return result;
    }


}

package kr.co.kimpoziben.test.controller;

import kr.co.kimpoziben.test.domain.code.AsStat;
//import kr.co.dmoim.dto.AsDto;
import kr.co.kimpoziben.test.dto.IjDto;
import kr.co.kimpoziben.test.dto.RpDto;
import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.dto.FileDto;
import kr.co.kimpoziben.dto.SearchDto;
//import kr.co.dmoim.service.AsService;
import kr.co.kimpoziben.test.service.IjService;
import kr.co.kimpoziben.test.service.RpService;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.service.FileService;
import kr.co.kimpoziben.util.AES256Util;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/test/ij")
public class IjController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private IjService ijService;
    private RpService rpService;
    private AttachService attachService;
    private FileService fileService;

    /**
     * AS리스트 조회
     * @param model
     * @param pageable
     * @param searchDto
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqAs"); // 페이징 필수셋팅 값, 정렬기준
//        pageable.setListSize(20);
//        pageable.setPageSize(5);
//        pageable.setPageSize(1);
//        pageable.setDirection(Sort.Direction.DESC);
        HashMap result = ijService.getAsList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("asEntityPage"));
        model.addAttribute("asList", result.get("asDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("asEntityPage")));
        model.addAttribute("searchDto", searchDto);
        return "test/ij/list";
    }

    /**
     * AS작성페이지 이동
     * @return
     */
    @GetMapping("/write.do")
    public String write() {
        return "test/ij/write";
    }

    /**
     * AS작성
     * @param ijDto
     * @param image
     * @param imageName
     * @param imageSize
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/write.do")
    public String write(IjDto ijDto, String[] image, String[] imageName, String[] imageSize) throws Exception {

        ijDto.setStatAs(AsStat.A);
        ijDto.setYnDel("N");
        ijDto.setRegdtAs(LocalDateTime.now());
        ijDto.setPasswordAs(passwordEncoder.encode(ijDto.getPasswordAs()));
        ijDto.setNoTelAs(aes.encrypt(ijDto.getNoTelAs()));
        ijService.saveAs(ijDto, image, imageName, imageSize);
        return "redirect:/test/ij/list.do";
    }

    /**
     * 비밀번호 체크
     * @param model
     * @param ijDto
     * @return
     */
    @PostMapping("/passwordChk.do")
    @ResponseBody
    public Object passwordChk(Model model, @RequestBody IjDto ijDto) throws Exception {
        return ijService.passwordChk(ijDto);
    }

    /**
     * AS 상세보기
     * @param model
     * @param ijDto
     * @param searchDto
     * @param pageable
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/detail.do")
    public String detail(Model model, IjDto ijDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {

        List<AttachDto> attachDtoList = null;
        List<RpDto> rpDtoList = null;
        List<FileDto> fileDtoList = null;
        IjDto resultDto = ijService.getAsDetail(ijDto.getSeqAs());
        resultDto.setPasswordAs(request.getParameter("passwordAs"));

        if(!ijService.passwordChk(ijDto)) {
            resultDto = null;
        }

        rpDtoList = rpService.getRpList(resultDto.getSeqAs());

        if(resultDto != null ) {

            attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());
            //문익진 추가 20201101
            fileDtoList = fileService.getFileInfoList(ijDto.getSeqAs());
            model.addAttribute("resultDto", resultDto);
            model.addAttribute("attachDtoList", attachDtoList);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            model.addAttribute("rpDtoList", rpDtoList);
            model.addAttribute("fileDtoList", fileDtoList);

            return "test/ij/detail";

        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }


    /**
     * AS 수정화면 이동
     * @param model
     * @param ijDto
     * @param searchDto
     * @param pageable
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/update.do")
    public String update(Model model, IjDto ijDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {
        if(ijService.passwordChk(ijDto)) {
            IjDto resultDto = ijService.getAsDetail(ijDto.getSeqAs());
            List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

            resultDto.setPasswordAs(ijDto.getPasswordAs());
            model.addAttribute("resultDto", resultDto);
            model.addAttribute("attachDtoList", attachDtoList);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            return "test/ij/update";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    /**
     * AS 수정처리
     * @param ijDto
     * @param image
     * @param imageName
     * @param imageSize
     * @return
     * @throws Exception
     */
    @PostMapping("/update.do")
    public String update(Model model, IjDto ijDto, SearchDto searchDto , String[] image, String[] imageName, String[] imageSize
            , HttpServletResponse response, HttpServletRequest request , RedirectAttributes redirectAttr) throws Exception {
        if (ijService.passwordChk(ijDto)) {
            redirectAttr.addAttribute("seqAs", ijDto.getSeqAs());
            redirectAttr.addAttribute("passwordAs", ijDto.getPasswordAs());
            ijDto.setModdtAs(LocalDateTime.now());
            ijDto.setPasswordAs(passwordEncoder.encode(ijDto.getPasswordAs()));
            ijDto.setNoTelAs(aes.encrypt(ijDto.getNoTelAs()));
            ijService.updateAs(ijDto, image, imageName, imageSize);
            return "redirect:/test/ij/detail.do";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    /**
     * AS삭제
     * @param ijDto
     * @param response
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delete.do")
    public String delete(IjDto ijDto, HttpServletResponse response) throws Exception {
        System.out.println(ijDto);
        if (ijService.passwordChk(ijDto)) {
            ijService.updateYnDel(ijDto.getSeqAs());
            return "redirect:/test/ij/list.do";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }
}

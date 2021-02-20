package kr.co.kimpoziben.test.controller;

import kr.co.kimpoziben.test.dto.AsDto;
import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.test.service.AsService;
import kr.co.kimpoziben.service.AttachService;
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
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/test/admin/as")
public class AdmnAsController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private AsService asService;
    private AttachService attachService;

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
        searchDto.setYnDel(""); // 삭제여부 관계없이 전부조회
        HashMap result = asService.getAsList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("asEntityPage"));
        model.addAttribute("asList", result.get("asDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("asEntityPage")));
        model.addAttribute("searchDto", searchDto);
        return "test/as/list";
    }

    /**
     * AS 상세보기
     * @param model
     * @param asDto
     * @param searchDto
     * @param pageable
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/detail.do")
    public String detail(Model model, AsDto asDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {

        AsDto resultDto = null;
        List<AttachDto> attachDtoList = null;

        resultDto = asService.getAsDetail(asDto.getSeqAs());

        attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());
        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "test/as/detail";
    }

    /**
     * AS 수정화면 이동
     * @param model
     * @param asDto
     * @param searchDto
     * @param pageable
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/update.do")
    public String update(Model model, AsDto asDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {
        AsDto resultDto = asService.getAsDetail(asDto.getSeqAs());
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "test/as/update";
    }

    /**
     * AS 수정처리
     * @param asDto
     * @param image
     * @param imageName
     * @param imageSize
     * @return
     * @throws Exception
     */
    @PostMapping("/update.do")
    public String update(Model model, AsDto asDto, SearchDto searchDto , String[] image, String[] imageName, String[] imageSize
            , HttpServletResponse response, HttpServletRequest request , RedirectAttributes redirectAttr) throws Exception {

        redirectAttr.addAttribute("seqAs", asDto.getSeqAs());

        asDto.setModdtAs(LocalDateTime.now());
        asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
        asService.updateAs(asDto, image, imageName, imageSize);

        return "redirect:/test/admin/as/detail.do";
    }

    /**
     * AS이미지 삭제
     * @param asDto
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/asImgDelete.do")
    @ResponseBody
    public Object asImgDelete(@RequestBody AsDto asDto) throws Exception {
        HashMap<String,Object> resultMap = new HashMap<>();
        attachService.updateDelYn(asDto);
        resultMap.put("result", "success");

        return resultMap;
    }

    @DeleteMapping("/delete.do")
    public String delete(AsDto asDto, HttpServletResponse response) throws Exception {
        asService.updateYnDel(asDto.getSeqAs());

        return "redirect:/test/admin/as/list.do";
    }
}

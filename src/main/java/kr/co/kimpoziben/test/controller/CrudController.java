package kr.co.kimpoziben.test.controller;

import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.test.dto.CrudDto;
import kr.co.kimpoziben.test.service.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/test/crud")
public class CrudController {
    private CrudService crudService;
    private AttachService attachService;

    @GetMapping("/list")
    public String list(Model model){
        List<CrudDto> crudList = crudService.getCrudList();

        model.addAttribute("crudList", crudList);
        return "test/crud/list";
    }

    @GetMapping("/post")
    public String write() {
        return "test/crud/write";
    }

    @PostMapping("/post")
    public String write(CrudDto crudDto, String[] image, String[] imageName, String[] imageSize) {
        crudDto.setWriteDate(LocalDateTime.now());
        crudService.savePost(crudDto, image, imageName, imageSize);
        return "redirect:/test/crud/list";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        CrudDto crudDto = crudService.getPost(no);
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(crudDto.getIdAttach());

        model.addAttribute("crudDto", crudDto);
        model.addAttribute("attachDtoList", attachDtoList);
        return "test/crud/detail";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        CrudDto crudDto = crudService.getPost(no);

        model.addAttribute("crudDto", crudDto);
        return "test/crud/update";
    }

    @PutMapping("/post/edit/{no}")
    public String update(CrudDto crudDto) {
        crudService.savePost(crudDto, null, null, null);

        return "redirect:/test/crud/list";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        crudService.deletePost(no);

        return "redirect:/test/crud/list";
    }
}

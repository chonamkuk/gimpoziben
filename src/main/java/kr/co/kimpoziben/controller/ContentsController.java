package kr.co.kimpoziben.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    @GetMapping("/location.do")
    public String location() throws Exception {
        return "contents/location";
    }

}

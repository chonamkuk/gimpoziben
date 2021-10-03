package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.config.auth.SessionUser;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@AllArgsConstructor
public class UserController {

    @GetMapping("login.do")
    public String login(Model model, HttpServletRequest request) throws Exception {

        return "user/login";
    }
}

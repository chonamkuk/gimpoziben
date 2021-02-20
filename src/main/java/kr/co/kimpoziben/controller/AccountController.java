package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.dto.AccountDto;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.service.AccountService;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private AccountService accountService;

    @GetMapping("/list.do")
    @ResponseBody
    public Object list(final PageRequest pageable, SearchDto searchDto) throws  Exception {
        HashMap result = accountService.getList(pageable.of(), searchDto);
        return result;
    }

    /**
     * 계정생성 java에서 값을 설정한다
     * @return
     */
    @GetMapping("/save.do")
    public String save(){
        AccountDto accountDto = new AccountDto();

        accountDto.setIdAccount("petersen");
        accountDto.setPasswordAccount(passwordEncoder.encode("1111"));
        accountDto.setNameAccount("petersen");
        accountDto.setEmailAccount("petersen@bikego.co.kr");
        accountDto.setRoleAccount("USER");

        accountService.save(accountDto);

        return "/main/main";
    }
}

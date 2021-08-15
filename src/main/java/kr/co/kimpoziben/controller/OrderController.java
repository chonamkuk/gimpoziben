package kr.co.kimpoziben.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/add.do")
    public @ResponseBody Object add(@RequestBody String orderDto) throws Exception {
        System.out.println("orderDto :: " + orderDto);
        HashMap resultMap = new HashMap();
        resultMap.put("result", 0);
        resultMap.put("orderDto", orderDto);
        return resultMap;
    }
}

package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.dto.OrderDto;
import kr.co.kimpoziben.service.OrderService;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @PostMapping("/add.do")
    public @ResponseBody Object add(@RequestBody OrderDto orderDto) throws Exception {
        HashMap resultMap = new HashMap();
        resultMap.put("result", 0);
        resultMap.put("orderDto", orderService.save(orderDto));
        return resultMap;
    }

    @GetMapping("list.do")
    public String list(Model model, final PageRequest pageable, HashMap<String,Object> searchMap) throws Exception {
        model.addAttribute("resultList", orderService.getList(pageable.of(), searchMap));
        return "order/list";
    }
}

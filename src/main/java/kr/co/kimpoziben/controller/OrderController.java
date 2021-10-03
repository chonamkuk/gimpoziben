package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.config.auth.SessionUser;
import kr.co.kimpoziben.dto.OrderDto;
import kr.co.kimpoziben.service.OrderService;
import kr.co.kimpoziben.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @PostMapping("/add.do")
    public @ResponseBody Object add(@RequestBody OrderDto orderDto, HttpServletRequest request) throws Exception {
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        HashMap resultMap = new HashMap();

        if(user != null) {
            resultMap.put("result", 0);
            resultMap.put("orderDto", orderService.save(orderDto, user));
        } else {
            resultMap.put("result", -1);
            resultMap.put("message", "need login");
        }

        return resultMap;
    }

    @GetMapping("list.do")
    public String list(Model model, final PageRequest pageable, HashMap<String,Object> searchMap,  HttpServletRequest request) throws Exception {
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        pageable.setListSize(9);
        pageable.setDirection(Sort.Direction.DESC);
        pageable.setSortProp("seqOrder");

        if(user != null) {
            searchMap.put("idCustomer", user.getId());
            model.addAttribute("resultList", orderService.getList(pageable.of(), searchMap));
        }

        return "order/list";
    }
}

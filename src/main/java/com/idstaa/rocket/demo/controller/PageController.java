package com.idstaa.rocket.demo.controller;

import com.idstaa.rocket.demo.entity.IdstaaOrder;
import com.idstaa.rocket.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenjie
 * @date 2021/4/16 18:52
 */
@Controller
public class PageController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = {"/index","/"})

    public String orderPayView(IdstaaOrder order){
        Integer goods_count_init = redisTemplate.opsForValue().get("goods_count_init");
        Integer goods_count = redisTemplate.opsForValue().get("goods_count");
        if (goods_count == null) {
            redisTemplate.opsForValue().set("goods_count_init", 10);
            redisTemplate.opsForValue().set("goods_count", 10);
            goods_count = 10;
        }
        int amount_percent = goods_count*100/goods_count_init;
        request.setAttribute("amount_percent", amount_percent+"%");
        request.setAttribute("current_count", goods_count);
        return "index";
    }

    @GetMapping(value = "waitingPayView/{orderId}")
    public String waitingPayView(@PathVariable String orderId){
        request.setAttribute("orderId", orderId);
        return "waitingPayView";
    }
}

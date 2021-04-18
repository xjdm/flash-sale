package com.idstaa.rocket.demo.controller;

import com.idstaa.rocket.demo.entity.IdstaaOrder;
import com.idstaa.rocket.demo.entity.OrderStatus;
import com.idstaa.rocket.demo.result.AppResult;
import com.idstaa.rocket.demo.result.ReturnWrapper;
import com.idstaa.rocket.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author chenjie
 * @date 2021/4/16 18:52
 */
@RestController
public class OrderController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 秒杀抢购
     * @param order
     * @return
     * @throws Exception
     */
    @GetMapping("/flash")
    public AppResult<IdstaaOrder> order(IdstaaOrder order) throws Exception {
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.NEW.getStatus());
        String result = orderService.order(order);
        if(result.equals("fail")){
            return new ReturnWrapper<IdstaaOrder>(order).fail(result);
        }else {
            return new ReturnWrapper<IdstaaOrder>(order).success(result);
        }

    }

    /**
     * 初始化秒杀商品数量（测试用）
     * @param amount
     * @return
     * @throws Exception
     */
    @GetMapping("/initProductAmount/{amount}")
    public AppResult<String> initProductAmount(@PathVariable Integer amount) throws Exception {
        redisTemplate.opsForValue().set("goods_count_init", amount);
        redisTemplate.opsForValue().set("goods_count", amount);
        return new ReturnWrapper<String>().success("success");
    }

    /**
     * 支付
     * @param orderId
     * @return
     * @throws Exception
     */
    @GetMapping("/pay/{orderId}")
    public AppResult<String> pay(@PathVariable String orderId) throws Exception {
        IdstaaOrder order = new IdstaaOrder();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.PAYED.getStatus());
        int i = orderService.updateByPrimaryKeySelective(order);
        if(i>0){
            return new ReturnWrapper<String>().success("支付成功");
        }else {
            return new ReturnWrapper<String>().fail("fail");
        }
    }
}

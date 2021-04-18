package com.idstaa.rocket.demo.listener;

import com.idstaa.rocket.demo.entity.IdstaaOrder;
import com.idstaa.rocket.demo.entity.OrderStatus;
import com.idstaa.rocket.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author chenjie
 * @date 2021/4/16 19:02
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "to_DelayPay2", consumerGroup = "consumer_grp_02")
public class RocketMQPayListener implements RocketMQListener<String> {
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(String message) {
        IdstaaOrder order = new IdstaaOrder();
        order.setOrderId(message);
        // 若超时，则取消订单
        order.setOrderStatus(OrderStatus.CANCEL.getStatus());
        int i = orderService.updateByPrimaryKeySelective(order);
        if (i >= 0) {
            redisTemplate.opsForValue().increment("goods_count");
            System.out.println(message+"订单已取消。。。");
        }

        log.info("{}订单支付状态更改", order.getOrderId());
    }
}

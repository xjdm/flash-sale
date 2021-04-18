package com.idstaa.rocket.demo.service.impl;

import com.idstaa.rocket.demo.entity.IdstaaOrder;
import com.idstaa.rocket.demo.entity.OrderStatus;
import com.idstaa.rocket.demo.mapper.OrderMapper;
import com.idstaa.rocket.demo.service.OrderService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author chenjie
 * @date 2021/4/16 18:46
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public String order(IdstaaOrder order) {
        Integer goods_count = redisTemplate.opsForValue().get("goods_count");
        if (goods_count <= 0) return "fail";
        rocketMQTemplate.asyncSend("tp_flashSale2", order, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                redisTemplate.opsForValue().set("goods_count", goods_count - 1);
                System.out.println("发送消息到秒杀队列"+order);
                order.setOrderStatus(OrderStatus.NEW.getStatus());
            }

            @Override
            public void onException(Throwable e) {

            }
        });
        return order.getOrderStatus() != null ? "success" : "fail";
    }

    @Override
    public int updateByPrimaryKeySelective(IdstaaOrder order) {
        IdstaaOrder queryOrder = new IdstaaOrder();
        queryOrder.setOrderId(order.getOrderId());
        IdstaaOrder select = orderMapper.selectOne(queryOrder);
        if (select != null && OrderStatus.PAYED.getStatus()!=select.getOrderStatus()) {
            return orderMapper.updateByPrimaryKeySelective(order);
        }else {
            return 0;
        }
    }

    @Override
    public int insertSelective(IdstaaOrder order) {

        return orderMapper.insertSelective(order);
    }

}

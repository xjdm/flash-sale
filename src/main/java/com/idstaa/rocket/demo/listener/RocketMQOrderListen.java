package com.idstaa.rocket.demo.listener;

import com.idstaa.rocket.demo.entity.IdstaaOrder;
import com.idstaa.rocket.demo.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenjie
 * @date 2021/4/16 19:02
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "tp_flashSale2", consumerGroup = "consumer_grp_03")
public class RocketMQOrderListen implements RocketMQListener<IdstaaOrder> {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void onMessage(IdstaaOrder order) {
        System.out.println("订单状态："+ order.getOrderStatus());
        System.out.println("向数据库插入记录-service-"+order);
        orderMapper.insertSelective(order);
        // 延迟支付消息
        Message message = new Message("to_DelayPay2", order.getOrderId().getBytes());
        org.springframework.messaging.Message springMessage = RocketMQUtil.convertToSpringMessage(message);
        rocketMQTemplate.asyncSend(message.getTopic(), springMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送消息到延迟支付队列");
            }

            @Override
            public void onException(Throwable e) {

            }
        },3000,3);
    }
}

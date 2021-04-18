package com.idstaa.rocket.demo.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenjie
 * @date 2021/4/16 18:24
 */

@Data
@Table(name="tb_order")
public class IdstaaOrder {

    @Id
    private String orderId;
    private String userId;
    private Integer orderStatus;
    private String content;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"OrderId\":\"")
                .append(orderId).append('\"');
        sb.append(",\"userId\":\"")
                .append(userId).append('\"');
        sb.append(",\"orderStatus\":")
                .append(orderStatus);
        sb.append(",\"content\":\"")
                .append(content).append('\"');
        sb.append('}');
        return sb.toString();
    }
}

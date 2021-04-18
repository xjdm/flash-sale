package com.idstaa.rocket.demo.service;

import com.idstaa.rocket.demo.entity.IdstaaOrder;

/**
 * @author chenjie
 * @date 2021/4/16 18:44
 */
public interface OrderService {
    public String order(IdstaaOrder order);

    public int updateByPrimaryKeySelective(IdstaaOrder order);

    public int insertSelective(IdstaaOrder order);

}

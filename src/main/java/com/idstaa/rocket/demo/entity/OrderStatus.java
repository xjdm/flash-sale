package com.idstaa.rocket.demo.entity;

/**
 * @author chenjie
 * @date 2021/4/16 18:43
 */
public enum OrderStatus {
    NEW(0),PAYED(1),CANCEL(2);
    private int status;

    OrderStatus(int status){
        this.status = status;
    }
    public  int getStatus(){
        return this.status;
    }
}

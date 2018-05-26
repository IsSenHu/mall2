package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 订单的状态
 * @author husen
 */
public enum OrderStatu implements Serializable {

    NO_PAY(0, "未付款"),
    PAYED_BUT_NO_DELIVER(1, "已付款未发货"),
    PAYED_AND_DELIVERED(2, "已付款已发货"),
    RECEIVED(3, "确认收货未评价"),
    REVIEWED(4, "已评价"),
    CANCELED(5, "订单已取消"),
    SURED(6, "订单已被确认"),
    REFUND_ALL(7, "订单中所有的条目已经退款");

    private Integer Value;
    private String description;

    OrderStatu(Integer value, String description) {
        Value = value;
        this.description = description;
    }

    public Integer getValue() {
        return Value;
    }

    public String getDescription() {
        return description;
    }
}

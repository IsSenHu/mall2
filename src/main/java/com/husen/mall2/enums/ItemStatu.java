package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 退款的状态
 * @author husen
 */
public enum ItemStatu implements Serializable {
    SHOPCART(1, "在购物车中"),
    ORDER(2, "在订单中"),
    REFUND(3, "在退款中");

    private Integer value;
    private String des;

    ItemStatu(Integer value, String des) {
        this.value = value;
        this.des = des;
    }

    public Integer getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }
}

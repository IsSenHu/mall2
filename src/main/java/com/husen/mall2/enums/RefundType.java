package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 退款类型
 * @author husen
 */
public enum RefundType implements Serializable{
    NO_POSTAGE(1, "仅退款"),
    HAVE_POSTAGE(2, "退款/退货");

    private Integer value;
    private String des;

    RefundType(Integer value, String des) {
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

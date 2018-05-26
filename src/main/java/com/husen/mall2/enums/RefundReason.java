package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 退款原因
 * @author husen
 */
public enum RefundReason implements Serializable {
    NO_WANT(1, "不想要了"),
    BUY_ERROR(2, "买错了"),
    NO_RECEIVE(3, "没收到货"),
    NO_MATCH_DES(4, "与说明不符");

    private Integer value;
    private String des;

    RefundReason(Integer value, String des) {
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

package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 退货的状态
 * @author husen
 */
public enum  RefundStatu implements Serializable{

    START(0, "买家开始申请"),
    WAIT(1, "等待商家处理退款申请"),
    SUCCESS(2, "款项成功退回"),
    FAILE(4, "商家不同意退款");

    private Integer value;
    private String description;

    RefundStatu(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

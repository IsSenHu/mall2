package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 排序方式
 * @author husen
 */
public enum SortStyle implements Serializable {
    COMPREHENSIVE(1, "综合排序"),
    SALE(2, "销量排序"),
    PRICE(3, "价格优先"),
    REVIEW(4, "评价为主");

    private Integer value;
    private String description;

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    SortStyle(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}

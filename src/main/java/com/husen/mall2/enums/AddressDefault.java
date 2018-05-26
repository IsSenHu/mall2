package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 是否为默认地址
 * @author husen
 */
public enum AddressDefault implements Serializable{
    YES(1, "默认地址"),
    NO(2, "不是默认地址");

    private Integer value;
    private String description;

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    AddressDefault(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}

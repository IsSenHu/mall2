package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 用户状态
 * @author husen
 */
public enum UserStatu implements Serializable {
    ACCESS("access"),
    DISABLED("disabled"),
    REALNAME_AUTHENTICATE("realNameAccess"),
    REALNAME_NO_AUTHENTICATE("realNameFaile");
    private String value;

    UserStatu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

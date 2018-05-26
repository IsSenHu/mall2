package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * 银行的
 * @author husen
 */
public enum Bank implements Serializable{
    JIAN("建设银行"),
    ZHAO("招商银行"),
    GONG("工商银行"),
    NONG("农业银行");

    private String bankName;

    Bank(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
}

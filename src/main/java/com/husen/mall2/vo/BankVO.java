package com.husen.mall2.vo;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 银行卡输入vo
 * @author husen
 */
public class BankVO implements Serializable {
    private Integer bankId;
    @NotEmpty(message = "真实姓名不能为空")
    private String realName;
    @NotEmpty(message = "身份证号不能为空")
    private String cardId;
    @NotEmpty(message = "银行卡号不能为空")
    private String bankCardId;
    private String phone;
    @NotEmpty(message = "验证码不能为空")
    private String code;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BankVO{" +
                "bankId=" + bankId +
                ", realName='" + realName + '\'' +
                ", cardId='" + cardId + '\'' +
                ", bankCardId='" + bankCardId + '\'' +
                ", phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

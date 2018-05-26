package com.husen.mall2.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 创建订单的vo
 * @author husen
 */
public class CreateOrder implements Serializable {
    private Integer userId;
    @NotNull(message = "地址id不能为空")
    private Integer addressId;
    @NotEmpty(message = "条目id不能为空")
    private String itemIdStr;
    @NotEmpty(message = "买家留言其实不为空")
    @Length(max = 500, message = "最多只能输入500个字符")
    private String userNote;
    @NotNull(message = "总价不能为空")
    private Double totalMoney;
    @NotNull(message = "支付状态必须提供")
    private Integer statu;
    private String password;
    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getItemIdStr() {
        return itemIdStr;
    }

    public void setItemIdStr(String itemIdStr) {
        this.itemIdStr = itemIdStr;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CreateOrder{" +
                "userId=" + userId +
                ", addressId=" + addressId +
                ", itemIdStr='" + itemIdStr + '\'' +
                ", userNote='" + userNote + '\'' +
                ", totalMoney=" + totalMoney +
                ", statu=" + statu +
                ", password='" + password + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}

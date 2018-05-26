package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 账单明细的vo
 * @author husen
 */
public class BillDetailVO implements Serializable {
    private Integer billDetaiId;
    private String pic;
    private Date payTime;
    private String goodName;
    private Double money;
    private String bankName;

    public BillDetailVO(Integer billDetaiId, String pic, Date payTime, String goodName, Double money, String bankName) {
        super();
        this.billDetaiId = billDetaiId;
        this.pic = pic;
        this.payTime = payTime;
        this.goodName = goodName;
        this.money = money;
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BillDetailVO() {
        super();
    }

    public Integer getBillDetaiId() {
        return billDetaiId;
    }

    public void setBillDetaiId(Integer billDetaiId) {
        this.billDetaiId = billDetaiId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "BillDetailVO{" +
                "billDetaiId=" + billDetaiId +
                ", pic='" + pic + '\'' +
                ", payTime=" + payTime +
                ", goodName='" + goodName + '\'' +
                ", money=" + money +
                '}';
    }
}

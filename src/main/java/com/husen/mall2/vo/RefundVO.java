package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * @author husen
 */
public class RefundVO implements Serializable {
    private Integer refundId;
    private Integer orderId;
    private Integer itemId;
    private String id;
    private String goodName;
    private String pic;
    private String money;
    private Integer number;
    private String isPostage;
    private String postage;
    private String totalMoney;
    private String smallAmount;
    private String dealTime;
    private Integer refundStatu;
    private Integer refundType;
    private Integer refundReason;
    private String refundMoney;
    private String description;

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Integer getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(Integer refundReason) {
        this.refundReason = refundReason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmallAmount() {
        return smallAmount;
    }

    public void setSmallAmount(String smallAmount) {
        this.smallAmount = smallAmount;
    }

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIsPostage() {
        return isPostage;
    }

    public void setIsPostage(String isPostage) {
        this.isPostage = isPostage;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public Integer getRefundStatu() {
        return refundStatu;
    }

    public void setRefundStatu(Integer refundStatu) {
        this.refundStatu = refundStatu;
    }

    public String getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(String refundMoney) {
        this.refundMoney = refundMoney;
    }

    @Override
    public String toString() {
        return "RefundVO{" +
                "refundId=" + refundId +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", id='" + id + '\'' +
                ", goodName='" + goodName + '\'' +
                ", pic='" + pic + '\'' +
                ", money='" + money + '\'' +
                ", number=" + number +
                ", isPostage='" + isPostage + '\'' +
                ", postage='" + postage + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", smallAmount='" + smallAmount + '\'' +
                ", dealTime='" + dealTime + '\'' +
                ", refundStatu=" + refundStatu +
                ", refundMoney='" + refundMoney + '\'' +
                '}';
    }
}

package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 退款说明
 * @author husen
 */
@Entity(name = "t_refund")
public class Refund implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refundId;

    /**
     * 订单的id
     */
    @Column
    private Integer orderId;
    /**
     * 订单条目的id
     */
    @Column
    private Integer itemId;
    /**
     * 退款类型
     */
    @Column
    private Integer refundType;

    /**
     * 退款原因
     */
    @Column
    private Integer refundReason;

    /**
     * 如果已发货，则不退运费，未发货则退包括运费
     */
    @Column
    private Double refundMoney;

    /**
     * 申请状态
     */
    @Column
    private Integer statu;

    /**
     * 退款说明
     */
    @Column
    private String description;

    /**
     * 订单所属店铺id
     */
    @Column
    private Integer storesId;

    /**
     * 订单所属用户id
     */
    @Column
    private Integer userId;

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

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStoresId() {
        return storesId;
    }

    public void setStoresId(Integer storesId) {
        this.storesId = storesId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "Refund{" +
                "refundId=" + refundId +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", refundType=" + refundType +
                ", refundReason=" + refundReason +
                ", refundMoney=" + refundMoney +
                ", statu=" + statu +
                ", description='" + description + '\'' +
                ", storesId=" + storesId +
                ", userId=" + userId +
                '}';
    }
}

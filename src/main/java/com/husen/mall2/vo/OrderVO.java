package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单的vo
 * @author husen
 */
public class OrderVO implements Serializable {
    private Integer orderId;
    private String id;
    private Date payTime;
    private Integer statu;
    private List<ItemVO> items;
    private String ifPostage;
    private Double postage;
    private Double totalMoney;
    private String storesName;
    private Integer refundStatu;
    private String defaultPic;
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }

    public Integer getRefundStatu() {
        return refundStatu;
    }

    public void setRefundStatu(Integer refundStatu) {
        this.refundStatu = refundStatu;
    }

    public String getStoresName() {
        return storesName;
    }

    public void setStoresName(String storesName) {
        this.storesName = storesName;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public List<ItemVO> getItems() {
        return items;
    }

    public void setItems(List<ItemVO> items) {
        this.items = items;
    }

    public String getIfPostage() {
        return ifPostage;
    }

    public void setIfPostage(String ifPostage) {
        this.ifPostage = ifPostage;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public OrderVO() {
        super();
    }

    public OrderVO(Integer orderId, String id, Date payTime, Integer statu, String ifPostage, Double postage, Double totalMoney) {
        this.orderId = orderId;
        this.id = id;
        this.payTime = payTime;
        this.statu = statu;
        this.ifPostage = ifPostage;
        this.postage = postage;
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderId=" + orderId +
                ", id='" + id + '\'' +
                ", payTime=" + payTime +
                ", statu=" + statu +
                ", items=" + items +
                ", ifPostage='" + ifPostage + '\'' +
                ", postage=" + postage +
                ", totalMoney=" + totalMoney +
                ", storesName='" + storesName + '\'' +
                '}';
    }
}

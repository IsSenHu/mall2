package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 订单条目的vo
 * @author husen
 */
public class ItemVO implements Serializable {
    private Integer itemId;
    private String pic;
    private String goodName;
    private Double unitPrice;
    private Integer number;

    public ItemVO(Integer itemId, String pic, String goodName, Double unitPrice, Integer number) {
        super();
        this.itemId = itemId;
        this.pic = pic;
        this.goodName = goodName;
        this.unitPrice = unitPrice;
        this.number = number;
    }

    public ItemVO() {
        super();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ItemVO{" +
                "itemId=" + itemId +
                ", pic='" + pic + '\'' +
                ", goodName='" + goodName + '\'' +
                ", unitPrice=" + unitPrice +
                ", number=" + number +
                '}';
    }
}

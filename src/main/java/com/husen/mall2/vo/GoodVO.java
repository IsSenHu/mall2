package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 商品的vo
 * @author husen
 */
public class GoodVO implements Serializable {
    private Integer goodId;
    private String goodName;
    private String firstPic;
    /**
     * 价格，有促销价显示促销价，为则显示原价
     */
    private Double price;

    /**
     * 销量
     */
    private Integer accumulatedSales;

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAccumulatedSales() {
        return accumulatedSales;
    }

    public void setAccumulatedSales(Integer accumulatedSales) {
        this.accumulatedSales = accumulatedSales;
    }

    @Override
    public String toString() {
        return "GoodVO{" +
                "goodId=" + goodId +
                ", goodName='" + goodName + '\'' +
                ", firstPic='" + firstPic + '\'' +
                ", price=" + price +
                ", accumulatedSales=" + accumulatedSales +
                '}';
    }
}

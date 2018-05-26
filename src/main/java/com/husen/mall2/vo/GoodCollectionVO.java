package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 收藏商品的vo
 * @author husen
 */
public class GoodCollectionVO extends GoodVO implements Serializable {
    private Double originalPrice;
    private Double salePrice;

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "GoodCollectionVO{" +
                "originalPrice=" + originalPrice +
                ", salePrice=" + salePrice +
                '}';
    }
}

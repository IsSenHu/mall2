package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 品牌的id和名称
 * @author husen
 */
public class FindBrand implements Serializable {
    private Integer brandId;
    private String brandName;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "FindBrand{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}

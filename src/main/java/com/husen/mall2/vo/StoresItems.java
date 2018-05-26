package com.husen.mall2.vo;


import com.husen.mall2.model.Item;

import java.io.Serializable;
import java.util.List;

/**
 * @author husen
 */
public class StoresItems implements Serializable {
    private List<Item> items;
    private String storesName;
    private List<String> coupons;
    private Integer storesId;

    public Integer getStoresId() {
        return storesId;
    }

    public void setStoresId(Integer storesId) {
        this.storesId = storesId;
    }

    public List<String> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<String> coupons) {
        this.coupons = coupons;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getStoresName() {
        return storesName;
    }

    public void setStoresName(String storesName) {
        this.storesName = storesName;
    }

    @Override
    public String toString() {
        return "StoresItems{" +
                "items=" + items +
                ", storesName='" + storesName + '\'' +
                ", coupons=" + coupons +
                ", storesId=" + storesId +
                '}';
    }
}

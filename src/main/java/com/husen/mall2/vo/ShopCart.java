package com.husen.mall2.vo;

import com.husen.mall2.model.Item;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车的vo
 * @author husen
 */
public class ShopCart implements Serializable {
    private Integer userId;

    private List<StoresItems> lists;

    private Double totalMoney;

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<StoresItems> getLists() {
        return lists;
    }

    public void setLists(List<StoresItems> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "userId=" + userId +
                ", lists=" + lists +
                '}';
    }
}

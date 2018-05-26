package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 * 店铺
 */
@Entity(name = "t_stores")
public class Stores implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storesId;

    /**
     * 该店铺拥有的商品
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Good> goods = new ArrayList<>();

    /**
     * 该店铺的所有订单
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();

    /**
     * 该店铺的名字
     */
    private String name;

    /**
     * 该店铺的老板
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bossId", referencedColumnName = "bossId")
    private Boss boss;

    /**
     * 该店的位置
     */
    @Column
    private String address;

    /**
     * 该店的主营业务
     */
    @Column
    private String sportStr;
    /**
     * 该店铺的营业许可
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "blId", referencedColumnName = "blId")
    private BusinessLicense businessLicense;

    public String getSportStr() {
        return sportStr;
    }

    public void setSportStr(String sportStr) {
        this.sportStr = sportStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BusinessLicense getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(BusinessLicense businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStoresId() {
        return storesId;
    }

    public void setStoresId(Integer storesId) {
        this.storesId = storesId;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    @Override
    public String toString() {
        return "Stores{" +
                "storesId=" + storesId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", sportStr='" + sportStr + '\'' +
                '}';
    }
}

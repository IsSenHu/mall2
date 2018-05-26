package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 优惠券
 * @author 11785
 */
@Entity(name = "t_coupon")
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 需要达到的金额
     */
    @Column
    private Double need;

    /**
     * 免多少钱
     */
    @Column
    private Double free;

    /**
     * 所属的商品
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "goodId", referencedColumnName = "goodId")
    private Good good;

    /**
     * 所属用户，应该是多对多的关系
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserCoupon> userCoupons = new ArrayList<>();

    /**
     * 过期时间
     */
    @Column
    @Temporal(TemporalType.DATE)
    private Date expiryTime;

    @Column
    private String expiryTimeStr;

    public String getExpiryTimeStr() {
        return expiryTimeStr;
    }

    public void setExpiryTimeStr(String expiryTimeStr) {
        this.expiryTimeStr = expiryTimeStr;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public List<UserCoupon> getUserCoupons() {
        return userCoupons;
    }

    public void setUserCoupons(List<UserCoupon> userCoupons) {
        this.userCoupons = userCoupons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNeed() {
        return need;
    }

    public void setNeed(Double need) {
        this.need = need;
    }

    public Double getFree() {
        return free;
    }

    public void setFree(Double free) {
        this.free = free;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", need=" + need +
                ", free=" + free +
                '}';
    }
}

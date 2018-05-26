package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 店铺老板
 * @author 11785
 */
@Entity(name = "t_boss")
public class Boss implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bossId;

    /**
     * 该老板的店铺
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "storesId", referencedColumnName = "storesId")
    private Stores stores;
    
    /**
     * 用户名
     */
    @Column(nullable = false)
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 手机号
     */
    @Column(nullable = false)
    private String phone;

    /**
     * 老板的姓名
     */
    @Column
    private String name;

    /**
     * 身份证号
     */
    @Column
    private String cardId;

    /**
     * 身份证件照正面
     */
    @Column
    private String cardBefore;

    /**
     * 身份证件照背面
     */
    @Column
    private String cardAfter;

    /**
     * 是否被认证
     */
    @Column
    private String isAuthenticate;

    /**
     * 状态
     */
    @Column
    private String statu;

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardBefore() {
        return cardBefore;
    }

    public void setCardBefore(String cardBefore) {
        this.cardBefore = cardBefore;
    }

    public String getCardAfter() {
        return cardAfter;
    }

    public void setCardAfter(String cardAfter) {
        this.cardAfter = cardAfter;
    }

    public String getIsAuthenticate() {
        return isAuthenticate;
    }

    public void setIsAuthenticate(String isAuthenticate) {
        this.isAuthenticate = isAuthenticate;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "bossId=" + bossId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", cardId='" + cardId + '\'' +
                ", cardBefore='" + cardBefore + '\'' +
                ", cardAfter='" + cardAfter + '\'' +
                ", isAuthenticate='" + isAuthenticate + '\'' +
                ", statu='" + statu + '\'' +
                '}';
    }
}

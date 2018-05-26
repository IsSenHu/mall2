package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品咨询
 * @author 11785
 */
@Entity(name = "t_commodity_consultation")
public class CommodityConsultation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ccId;

    /**
     * 问题类型
     */
    @Column
    private String type;

    /**
     * 问题描述
     */
    @Column
    private String description;

    /**
     * 所属用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    /**
     * 咨询反馈的结果
     */
    @Column
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommodityConsultation{" +
                "ccId=" + ccId +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}

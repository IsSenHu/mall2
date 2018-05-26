package com.husen.mall2.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "t_foot")
public class Foot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer footId;
    private Integer userId;
    private Integer goodId;
    @Temporal(TemporalType.DATE)
    private Date footDate;

    public Integer getFootId() {
        return footId;
    }

    public void setFootId(Integer footId) {
        this.footId = footId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Date getFootDate() {
        return footDate;
    }

    public void setFootDate(Date footDate) {
        this.footDate = footDate;
    }

    @Override
    public String toString() {
        return "Foot{" +
                "footId=" + footId +
                ", userId=" + userId +
                ", goodId=" + goodId +
                ", footDate=" + footDate +
                '}';
    }
}

package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 物流公司
 * @author 11785
 */
@Entity(name = "t_logistics_company")
public class LogisticsCompany implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lcId;

    /**
     * 公司名字
     */
    @Column
    private String name;

    /**
     * 官方电话
     */
    @Column
    private String phone;

    public Integer getLcId() {
        return lcId;
    }

    public void setLcId(Integer lcId) {
        this.lcId = lcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "LogisticsCompany{" +
                "lcId=" + lcId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

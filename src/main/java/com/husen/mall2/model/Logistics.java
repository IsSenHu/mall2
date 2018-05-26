package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 物流信息
 * @author 11785
 */
@Entity(name = "t_logistics")
public class Logistics implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 该物流信息的地址
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;

    /**
     * 物流公司
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lcId", referencedColumnName = "lcId")
    private LogisticsCompany company;

    /**
     * 快递单号
     */
    @Column
    private String expressNumber;

    /**
     * 物流状态
     */
    @Column
    private String statu;

    /**
     * 物流记录
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<LogisticsRecord> records = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LogisticsCompany getCompany() {
        return company;
    }

    public void setCompany(LogisticsCompany company) {
        this.company = company;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public List<LogisticsRecord> getRecords() {
        return records;
    }

    public void setRecords(List<LogisticsRecord> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Logistics{" +
                "id=" + id +
                ", expressNumber='" + expressNumber + '\'' +
                ", statu='" + statu + '\'' +
                '}';
    }
}

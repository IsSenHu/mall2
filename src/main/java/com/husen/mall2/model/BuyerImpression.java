package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 买家印象
 * @author 11785
 */
@Entity(name = "t_buyer_impression")
public class BuyerImpression implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评价标签
     */
    @Column
    private String label;

    /**
     * 同个标签的数量
     */
    @Column
    private Integer number;

    /**
     * 该标签所属商品
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "goodId", referencedColumnName = "goodId")
    private Good good;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "BuyerImpression{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", number=" + number +
                '}';
    }
}

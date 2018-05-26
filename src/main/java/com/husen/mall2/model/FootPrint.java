package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 11785 足迹
 */
@Entity(name = "t_foot_print")
public class FootPrint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer footPrintId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goodId", referencedColumnName = "goodId")
    private Good good;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public Integer getFootPrintId() {
        return footPrintId;
    }

    public void setFootPrintId(Integer footPrintId) {
        this.footPrintId = footPrintId;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FootPrint{" +
                "footPrintId=" + footPrintId +
                ", good=" + good +
                ", user=" + user +
                '}';
    }

    public FootPrint(Good good, User user) {
        super();
        this.good = good;
        this.user = user;
    }

    public FootPrint() {
        super();
        this.user = new User();
        this.good = new Good();
    }
}

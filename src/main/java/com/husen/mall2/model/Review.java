package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评价
 */
@Entity(name = "t_review")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 开不开匿名评价
     */
    @Column
    private String showWho;

    /**
     * 评价的用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    /**
     * 评价的商品
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "goodId", referencedColumnName = "goodId")
    private Good good;

    /**
     * 评价的时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    /**
     * 评价的内容
     */
    @Column
    private String content;

    private Integer reviewLevel;

    public Integer getReviewLevel() {
        return reviewLevel;
    }

    public void setReviewLevel(Integer reviewLevel) {
        this.reviewLevel = reviewLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShowWho() {
        return showWho;
    }

    public void setShowWho(String showWho) {
        this.showWho = showWho;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", showWho='" + showWho + '\'' +
                ", data=" + data +
                ", content='" + content + '\'' +
                '}';
    }
}

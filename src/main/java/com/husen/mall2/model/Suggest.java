package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 意见
 * @author 11785
 */
@Entity(name = "t_suggest")
public class Suggest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer suggestId;

    /**
     * 意见类型
     */
    @Column
    private String type;

    /**
     * 一件描述
     */
    @Column
    private String description;

    /**
     * 该意见来自于哪个用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public Integer getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Integer suggestId) {
        this.suggestId = suggestId;
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
        return "Suggest{" +
                "suggestId=" + suggestId +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}

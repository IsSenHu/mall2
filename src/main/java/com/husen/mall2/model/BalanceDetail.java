package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 余额明细
 * @author 11785
 */
@Entity(name="t_balance_detail")
public class BalanceDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bdId;

    /**
     * 创建时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 详情
     */
    @Column
    private String content;

    /**
     * 变化
     */
    @Column
    private Double changeMoney;

    /**
     * 余额
     */
    @Column
    private Double balanceMoney;

    /**
     * 所属用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public Integer getBdId() {
        return bdId;
    }

    public void setBdId(Integer bdId) {
        this.bdId = bdId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(Double changeMoney) {
        this.changeMoney = changeMoney;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BalanceDetail{" +
                "bdId=" + bdId +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", changeMoney=" + changeMoney +
                ", balanceMoney=" + balanceMoney +
                '}';
    }
}

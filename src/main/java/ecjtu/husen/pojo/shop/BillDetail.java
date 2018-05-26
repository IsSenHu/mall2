package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 账单明细
 * @author 11785
 */
@Entity
public class BillDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bdId;

    /**
     * 支出
     */
    @Column
    private Double expenditure;

    /**
     * 交易时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dealTime;

    /**
     * 所属的订单
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    /**
     * 所属的用户
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

    public Double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "bdId=" + bdId +
                ", expenditure=" + expenditure +
                ", dealTime=" + dealTime +
                '}';
    }
}

package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发货单
 * @author 11785
 */
@Entity(name = "t_deliverorder")
public class DeliverOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliverOrderId;

    /**
     * 收货的地址id，包括（收货人，地址，电话）
     */
    @Column
    private Integer addressId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Row> rows = new ArrayList<>();

    @Column
    private Double totalMoney;

    @Column
    private Integer userId;

    @Column
    private Integer storesId;

    /**
     * 发货时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliverTime;

    @Column
    private String orderId;
    /**
     * 发货单的状态
     */
    @Column
    private String statu;

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getDeliverOrderId() {
        return deliverOrderId;
    }

    public void setDeliverOrderId(Integer deliverOrderId) {
        this.deliverOrderId = deliverOrderId;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoresId() {
        return storesId;
    }

    public void setStoresId(Integer storesId) {
        this.storesId = storesId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "DeliverOrder{" +
                "deliverOrderId=" + deliverOrderId +
                ", totalMoney=" + totalMoney +
                ", userId=" + userId +
                ", storesId=" + storesId +
                ", orderId=" + orderId +
                ", statu='" + statu + '\'' +
                '}';
    }
}

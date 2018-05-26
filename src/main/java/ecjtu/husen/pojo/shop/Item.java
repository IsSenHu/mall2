package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品订单条目
 * @author 11785
 */
@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    /**
     * 该订单条目的商品
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "goodId", referencedColumnName = "goodId")
    private Good good;

    /**
     * 该条目的商品数量
     */
    @Column
    private Integer number;

    /**
     * 优惠过后折算的实际单价
     */
    @Column
    private Double unitPrice;

    /**
     * 该条目商品的总价
     */
    @Column
    private Double totalMoney;

    /**
     * 有无运费
     */
    @Column
    private String isFreight;

    /**
     * 运费
     */
    @Column
    private Double freight;

    /**
     * 该条目属于哪一个订单
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getIsFreight() {
        return isFreight;
    }

    public void setIsFreight(String isFreight) {
        this.isFreight = isFreight;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", number=" + number +
                ", unitPrice=" + unitPrice +
                ", totalMoney=" + totalMoney +
                ", isFreight='" + isFreight + '\'' +
                ", freight=" + freight +
                '}';
    }
}

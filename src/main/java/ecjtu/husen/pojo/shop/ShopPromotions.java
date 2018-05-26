package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 店铺优惠
 * @author 11785
 */
@Entity
public class ShopPromotions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spId;
    /**
     * 需要达到的数量
     */
    @Column
    private Integer number;

    /**
     * 折扣
     */
    @Column
    private Double discount;

    /**
     * 所属的商品
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "goodId", referencedColumnName = "goodId")
    private Good good;

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "ShopPromotions{" +
                "spId=" + spId +
                ", number=" + number +
                ", discount=" + discount +
                '}';
    }
}

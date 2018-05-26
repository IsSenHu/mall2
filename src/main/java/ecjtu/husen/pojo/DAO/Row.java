package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 出库单的条目
 * @author 11785
 */
@Entity(name = "t_row")
public class Row implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rowId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "outOrderId", referencedColumnName = "outOrderId")
    private OutOrder outOrder;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "deliverOrderId", referencedColumnName = "deliverOrderId")
    private DeliverOrder deliverOrder;

    @Column
    private Integer number;

    /**
     * 单价
     */
    @Column
    private Double money;

    /**
     * 这一条的总价
     */
    @Column
    private Double rowMoney;

    public DeliverOrder getDeliverOrder() {
        return deliverOrder;
    }

    public void setDeliverOrder(DeliverOrder deliverOrder) {
        this.deliverOrder = deliverOrder;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getRowMoney() {
        return rowMoney;
    }

    public void setRowMoney(Double rowMoney) {
        this.rowMoney = rowMoney;
    }

    public OutOrder getOutOrder() {
        return outOrder;
    }

    public void setOutOrder(OutOrder outOrder) {
        this.outOrder = outOrder;
    }

    @Override
    public String toString() {
        return "Row{" +
                "rowId=" + rowId +
                ", number=" + number +
                ", money=" + money +
                ", rowMoney=" + rowMoney +
                '}';
    }
}

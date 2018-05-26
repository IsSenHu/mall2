package ecjtu.husen.pojo.DAO;

import ecjtu.husen.pojo.DTO.InStatu;
import ecjtu.husen.pojo.converter.InStatuConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 入库单
 * @author 11785
 */
@Entity(name = "t_inorder")
public class InOrder implements Serializable {

    /**
     * 编号，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inOrderId;
    /**
     * 商品
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private Item item;
    /**
     * 数量
     */
    @Column
    private Integer itmeNumber;
    /**
     * 入库单总金额
     */
    @Column
    private Double totalMoney;
    /**
     * 入库单状态
     */
    @Column(name = "inStatu", columnDefinition = "int not null COMMENT '" + InStatu.Doc + "'")
    @Convert(converter = InStatuConverter.class)
    private InStatu inStatu;
    /**
     * 入库单生成时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date inTime;
    /**
     * 填写入库单的人
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "inUserId", referencedColumnName = "userId")
    private UserPO userIn;
    /**
     * 审核入库单的人
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "verfyUserId", referencedColumnName = "userId")
    private UserPO userVerfy;

    public Integer getInOrderId() {
        return inOrderId;
    }

    public void setInOrderId(Integer inOrderId) {
        this.inOrderId = inOrderId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getItmeNumber() {
        return itmeNumber;
    }

    public void setItmeNumber(Integer itmeNumber) {
        this.itmeNumber = itmeNumber;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public InStatu getInStatu() {
        return inStatu;
    }

    public void setInStatu(InStatu inStatu) {
        this.inStatu = inStatu;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    @Override
    public String toString() {
        return "InOrder{" +
                "inOrderId=" + inOrderId +
                ", itmeNumber=" + itmeNumber +
                ", totalMoney=" + totalMoney +
                ", inStatu=" + inStatu +
                ", inTime=" + inTime +
                '}';
    }

    public UserPO getUserIn() {
        return userIn;
    }

    public void setUserIn(UserPO userIn) {
        this.userIn = userIn;
    }

    public UserPO getUserVerfy() {
        return userVerfy;
    }

    public void setUserVerfy(UserPO userVerfy) {
        this.userVerfy = userVerfy;
    }
}

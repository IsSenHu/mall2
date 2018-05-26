package ecjtu.husen.pojo.DAO;

import ecjtu.husen.pojo.DTO.InStatu;
import ecjtu.husen.pojo.converter.InStatuConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 出库单
 * @author 11785
 */
@Entity(name = "t_outorder")
public class OutOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer outOrderId;

    /**
     * 出库的条目
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Row> rows = new ArrayList<>();

    /**
     * 出库单生成时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date outTime;

    /**
     * 出库的总金额
     */
    @Column
    private Double totalMoney;

    /**
     * 出库单状态
     */
    @Column(name = "inStatu", columnDefinition = "int not null COMMENT '" + InStatu.Doc + "'")
    @Convert(converter = InStatuConverter.class)
    private InStatu inStatu;

    /**
     * 审核的人
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserPO verfyUser;

    /**
     * 由哪个订单生成
     */
    @Column
    private String orderId;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Integer getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(Integer outOrderId) {
        this.outOrderId = outOrderId;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
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

    public UserPO getVerfyUser() {
        return verfyUser;
    }

    public void setVerfyUser(UserPO verfyUser) {
        this.verfyUser = verfyUser;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OutOrder{" +
                "outOrderId='" + outOrderId + '\'' +
                ", outTime=" + outTime +
                ", totalMoney=" + totalMoney +
                ", inStatu=" + inStatu +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}

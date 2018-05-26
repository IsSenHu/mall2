package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品订单
 * @author 11785
 */
@Entity(name = "t_orderId")
public class Order implements Serializable {
    /**
     * 订单物理主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    /**
     * 订单业务主键,订单编号
     */
    @Column(unique = true)
    private String id;

    /**
     * 交易时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime;

    /**
     * 该订单所包含的订单条目
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Item> items = new ArrayList<>();

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
     * 合计
     */
    @Column(nullable = false)
    private Double totalMoney;

    /**
     * 该订单的物流信息
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "lId", referencedColumnName = "id")
    private Logistics logistics;

    /**
     * 订单状态
     */
    @Column
    private Integer statu;

    /**
     * 该订单所属的用户
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @Column
    private Integer uid;
    /**
     * 该订单所属的店铺
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "storesId", referencedColumnName = "storesId")
    private Stores stores;
    @Column
    private Integer storeId;

    /**
     * 删除标识
     */
    @Column
    private String del;

    /**
     * 发货地址
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;


    /**
     * 买家留言
     */
    @Column(length = 500)
    private String userNote;

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", id='" + id + '\'' +
                ", payTime=" + payTime +
                ", isFreight='" + isFreight + '\'' +
                ", freight=" + freight +
                ", totalMoney=" + totalMoney +
                ", statu=" + statu +
                ", uid=" + uid +
                ", storeId=" + storeId +
                ", del='" + del + '\'' +
                '}';
    }
}

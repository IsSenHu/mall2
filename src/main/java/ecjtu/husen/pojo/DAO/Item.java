package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 11785
 * 商品
 */
@Entity(name = "t_item")
public class Item implements Serializable {
    /**
     * 商品编号，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    /**
     * 商品名称
     */
    @Column
    private String itemName;
    /**
     * 商品品牌
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    private Brand brand;
    /**
     * 当前库存
     */
    @Column
    private Integer currentInventory;
    /**
     * 商品所属规格
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "specificationId", referencedColumnName = "specificationId")
    private Specification specification;
    /**
     * 建议零售价
     */
    @Column
    private Double suggestRetailPrice;
    /**
     * 实际单价
     */
    @Column
    private Double unitPrice;
    /**
     * 适用对象
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "applyerId", referencedColumnName = "applyerId")
    private Applyer applyer;
    /**
     * 上市时间
     */
    @Column
    private Date timeToMarket;
    /**
     * 材质
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "materialId", referencedColumnName = "materialId")
    private Material material;
    /**
     * 图片地址
     */
    @Column
    private String picPath;
    /**
     * 物品是属于哪个运动物品
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sportItemId", referencedColumnName = "sportItemId")
    private SportItem sportItem;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Integer getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(Integer currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public Double getSuggestRetailPrice() {
        return suggestRetailPrice;
    }

    public void setSuggestRetailPrice(Double suggestRetailPrice) {
        this.suggestRetailPrice = suggestRetailPrice;
    }

    public Applyer getApplyer() {
        return applyer;
    }

    public void setApplyer(Applyer applyer) {
        this.applyer = applyer;
    }

    public Date getTimeToMarket() {
        return timeToMarket;
    }

    public void setTimeToMarket(Date timeToMarket) {
        this.timeToMarket = timeToMarket;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", currentInventory=" + currentInventory +
                ", suggestRetailPrice=" + suggestRetailPrice +
                ", timeToMarket=" + timeToMarket +
                ", picPath='" + picPath + '\'' +
                '}';
    }

    public SportItem getSportItem() {
        return sportItem;
    }

    public void setSportItem(SportItem sportItem) {
        this.sportItem = sportItem;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

package com.husen.mall2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 * @author 11785
 */
@Entity(name = "t_good")
public class Good implements Serializable {
    /**
     * 商品id，编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodId;

    /**
     * 物品的id
     */
    @Column
    private Integer itemId;
    /**
     * 商品名字
     */
    @Column
    private String goodName;

    /**
     * 拥有的折扣
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ShopPromotions> promotions = new ArrayList<>();

    /**
     * 拥有的优惠券
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Coupon> coupons = new ArrayList<>();

    /**
     * 促销价
     */
    @Column
    private Double salePrice;

    /**
     * 原价
     */
    @Column
    private Double originalPrice;
    /**
     * 月销量
     */
    @Column
    private Integer monthlySales;

    /**
     * 累计销量
     */
    @Column
    private Integer accumulatedSales;

    /**
     * 拥有的超过一定数量的印象
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<BuyerImpression> impressions = new ArrayList<>();

    /**
     * 该商品的所有评论
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<FootPrint> footPrints = new ArrayList<>();
    /**
     * 评价的分数
     */
    @Column
    private Double reviewsScore;

    /**
     * 最终价格
     */
    @Column
    private Double lastPrice;
    /**
     * 该商品所有的图片地址连接字符串
     */
    @Column(length = 1000, columnDefinition = "")
    private String pisc;

    /**
     * 商品细节图片地址
     */
    @Column
    private String detailpic;

    /**
     * 该商品所在的店铺
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "storesId", referencedColumnName = "storesId")
    private Stores stores;

    /**
     * 是否上架
     */
    @Column
    private String isShelves;

    /**
     * 综合能力
     */
    @Column
    private Long comprehensive;

    /**
     * 有无邮费
     */
    @Column
    private String isPostage;

    /**
     * 邮费多少
     */
    @Column
    private Double postageMoney;

    public Double getReviewsScore() {
        return reviewsScore;
    }

    public void setReviewsScore(Double reviewsScore) {
        this.reviewsScore = reviewsScore;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Long getComprehensive() {
        return comprehensive;
    }

    public void setComprehensive(Long comprehensive) {
        this.comprehensive = comprehensive;
    }

    public String getIsPostage() {
        return isPostage;
    }

    public void setIsPostage(String isPostage) {
        this.isPostage = isPostage;
    }

    public Double getPostageMoney() {
        return postageMoney;
    }

    public void setPostageMoney(Double postageMoney) {
        this.postageMoney = postageMoney;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getIsShelves() {
        return isShelves;
    }

    public void setIsShelves(String isShelves) {
        this.isShelves = isShelves;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public List<FootPrint> getFootPrints() {
        return footPrints;
    }

    public void setFootPrints(List<FootPrint> footPrints) {
        this.footPrints = footPrints;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public List<ShopPromotions> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<ShopPromotions> promotions) {
        this.promotions = promotions;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public Integer getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Integer monthlySales) {
        this.monthlySales = monthlySales;
    }

    public Integer getAccumulatedSales() {
        return accumulatedSales;
    }

    public void setAccumulatedSales(Integer accumulatedSales) {
        this.accumulatedSales = accumulatedSales;
    }

    public List<BuyerImpression> getImpressions() {
        return impressions;
    }

    public void setImpressions(List<BuyerImpression> impressions) {
        this.impressions = impressions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getPisc() {
        return pisc;
    }

    public void setPisc(String pisc) {
        this.pisc = pisc;
    }

    public String getDetailpic() {
        return detailpic;
    }

    public void setDetailpic(String detailpic) {
        this.detailpic = detailpic;
    }

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodId=" + goodId +
                ", itemId=" + itemId +
                ", goodName='" + goodName + '\'' +
                ", salePrice=" + salePrice +
                ", originalPrice=" + originalPrice +
                ", monthlySales=" + monthlySales +
                ", accumulatedSales=" + accumulatedSales +
                ", reviewsScore=" + reviewsScore +
                ", lastPrice=" + lastPrice +
                ", pisc='" + pisc + '\'' +
                ", detailpic='" + detailpic + '\'' +
                ", isShelves='" + isShelves + '\'' +
                ", comprehensive=" + comprehensive +
                ", isPostage='" + isPostage + '\'' +
                ", postageMoney=" + postageMoney +
                '}';
    }
}

package com.husen.mall2.vo;

import ecjtu.husen.pojo.DAO.Item;

import java.io.Serializable;
import java.util.List;

/**
 * 商品介绍页面的vo
 * @author husen
 */
public class GoodIntroductionVO implements Serializable {

    /**
     * 商品ID
     */
    private Integer goodId;
    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 店铺优惠
     */
    private String shopPromotions;

    /**
     * 优惠券
     */
    private List<String> coupons;

    /**
     * 邮费
     */
    private Double postage;

    /**
     * 月销量
     */
    private Integer monthSaleNumber;

    /**
     * 累积销量
     */
    private Integer saleNumber;

    /**
     * 累计评论
     */
    private Integer reviewNumber;

    /**
     * 图片集合
     */
    private List<String> pics;

    /**
     * 手机端的图片集合
     */
    private List<String> picsPhone;
    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 促销价
     */
    private Double salePrice;

    /**
     * 商品所属物品以及其参数
     */
    private Item item;

    /**
     * 商品详情图片
     */
    private String detailPic;

    private String firstPic;

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }
    private List<GoodVO> lookAndLook;

    public List<GoodVO> getLookAndLook() {
        return lookAndLook;
    }

    public void setLookAndLook(List<GoodVO> lookAndLook) {
        this.lookAndLook = lookAndLook;
    }

    private List<GoodVO> guestYouLike;


//    private List<Review> reviews;全部评价用ajax

    public List<String> getPicsPhone() {
        return picsPhone;
    }

    public void setPicsPhone(List<String> picsPhone) {
        this.picsPhone = picsPhone;
    }

    public Integer getGoodId() {
        return goodId;
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

    public String getShopPromotions() {
        return shopPromotions;
    }

    public void setShopPromotions(String shopPromotions) {
        this.shopPromotions = shopPromotions;
    }

    public List<String> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<String> coupons) {
        this.coupons = coupons;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public Integer getMonthSaleNumber() {
        return monthSaleNumber;
    }

    public void setMonthSaleNumber(Integer monthSaleNumber) {
        this.monthSaleNumber = monthSaleNumber;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Integer getReviewNumber() {
        return reviewNumber;
    }

    public void setReviewNumber(Integer reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getDetailPic() {
        return detailPic;
    }

    public void setDetailPic(String detailPic) {
        this.detailPic = detailPic;
    }

    public List<GoodVO> getGuestYouLike() {
        return guestYouLike;
    }

    public void setGuestYouLike(List<GoodVO> guestYouLike) {
        this.guestYouLike = guestYouLike;
    }

    @Override
    public String toString() {
        return "GoodIntroductionVO{" +
                "goodId=" + goodId +
                ", goodName='" + goodName + '\'' +
                ", shopPromotions='" + shopPromotions + '\'' +
                ", coupons=" + coupons +
                ", postage=" + postage +
                ", monthSaleNumber=" + monthSaleNumber +
                ", saleNumber=" + saleNumber +
                ", reviewNumber=" + reviewNumber +
                ", pics=" + pics +
                ", originalPrice=" + originalPrice +
                ", salePrice=" + salePrice +
                ", item=" + item +
                ", detailPic='" + detailPic + '\'' +
                ", firstPic='" + firstPic + '\'' +
                '}';
    }
}

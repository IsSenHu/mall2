package com.husen.mall2.model;

import ecjtu.husen.pojo.DTO.Gender;
import ecjtu.husen.pojo.converter.GenderConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商城顾客
 * @author 11785
 */
@Entity(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /**
     * 用户名
     */
    @Column
    private String username;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 昵称
     */
    @Column
    private String nickName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    @Column(name = "gender", columnDefinition = "int not null COMMENT '" + Gender.Doc + "'")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    /**
     * 生日
     */
    @Column
    @Temporal(TemporalType.DATE)
    private Date birthday;

    /**
     * 电话
     */
    @Column
    private String phone;

    /**
     * 电子邮件
     */
    @Column
    private String email;

    /**
     * 头像
     */
    @Column
    private String pic;

    /**
     * 是否实名认证
     */
    @Column
    private String isVerified;

    /**
     * 身份证号
     */
    @Column
    private String idCard;

    /**
     * 身份证正面照片地址
     */
    @Column
    private String idCardBefore;

    /**
     * 身份证背面照片地址
     */
    @Column
    private String idCardAfter;

    /**
     * 支付密码
     */
    @Column
    private String payPassword;

    /**
     * 安全分数
     */
    @Column
    private Integer safeScore;

    /**
     * 可用余额
     */
    @Column
    private Double availableBalance;

    /**
     * 账户状态
     */
    @Column
    private String statu;

    /**
     * 默认地址
     * */
    @Column
    private Integer addressId;
    /**
     * 该用户所有的地址
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Address> addresses = new ArrayList<>();
    /**
     * 该用户的所有评论
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Review> reviews = new ArrayList<>();

    /**
     * 该用户的所有订单
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Order> orders = new ArrayList<>();

    /**
     * 该用户的安全问题
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SafeQuestion> safeQuestions = new ArrayList<>();

    /**
     * 该用户的银行卡
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<BankCard> bankCards = new ArrayList<>();

    /**
     * 我的优惠券
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserCoupon> userCoupons = new ArrayList<>();

    /**
     * 该用户的余额明细
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<BalanceDetail> balanceDetails = new ArrayList<>();

    /**
     * 该用户的账单明细
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<BillDetail> billDetails = new ArrayList<>();

    /**
     * 我的收藏
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumns({@JoinColumn(name = "userCollectionId", referencedColumnName = "userId")})
    private List<Good> collection = new ArrayList<>();

    /**
     * 我的足迹
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<FootPrint> footPrints = new ArrayList<>();

    /**
     * 该用户的意见
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Suggest> suggests = new ArrayList<>();

    /**
     * 该用户的咨询
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CommodityConsultation> commodityConsultations = new ArrayList<>();

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardBefore() {
        return idCardBefore;
    }

    public void setIdCardBefore(String idCardBefore) {
        this.idCardBefore = idCardBefore;
    }

    public String getIdCardAfter() {
        return idCardAfter;
    }

    public void setIdCardAfter(String idCardAfter) {
        this.idCardAfter = idCardAfter;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getSafeScore() {
        return safeScore;
    }

    public void setSafeScore(Integer safeScore) {
        this.safeScore = safeScore;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<SafeQuestion> getSafeQuestions() {
        return safeQuestions;
    }

    public void setSafeQuestions(List<SafeQuestion> safeQuestions) {
        this.safeQuestions = safeQuestions;
    }

    public List<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<BankCard> bankCards) {
        this.bankCards = bankCards;
    }

    public List<UserCoupon> getUserCoupons() {
        return userCoupons;
    }

    public void setUserCoupons(List<UserCoupon> userCoupons) {
        this.userCoupons = userCoupons;
    }

    public List<BalanceDetail> getBalanceDetails() {
        return balanceDetails;
    }

    public void setBalanceDetails(List<BalanceDetail> balanceDetails) {
        this.balanceDetails = balanceDetails;
    }

    public List<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    public List<Good> getCollection() {
        return collection;
    }

    public void setCollection(List<Good> collection) {
        this.collection = collection;
    }

    public List<FootPrint> getFootPrints() {
        return footPrints;
    }

    public void setFootPrints(List<FootPrint> footPrints) {
        this.footPrints = footPrints;
    }

    public List<Suggest> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<Suggest> suggests) {
        this.suggests = suggests;
    }

    public List<CommodityConsultation> getCommodityConsultations() {
        return commodityConsultations;
    }

    public void setCommodityConsultations(List<CommodityConsultation> commodityConsultations) {
        this.commodityConsultations = commodityConsultations;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", pic='" + pic + '\'' +
                ", isVerified='" + isVerified + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idCardBefore='" + idCardBefore + '\'' +
                ", idCardAfter='" + idCardAfter + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", safeScore=" + safeScore +
                ", availableBalance=" + availableBalance +
                ", statu='" + statu + '\'' +
                '}';
    }
}

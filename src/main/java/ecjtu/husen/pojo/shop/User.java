package ecjtu.husen.pojo.shop;

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
@Entity
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
    private List<Coupon> coupons = new ArrayList<>();

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
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumns({@JoinColumn(name = "userFootprintId", referencedColumnName = "userId")})
    private List<Good> footprint = new ArrayList<>();

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

}

package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 银行卡
 * @author 11785
 */
@Entity
public class BankCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bcId;

    /**
     * 真实姓名
     */
    @Column
    private String realName;

    /**
     * 身份证号
     */
    @Column
    private String idCardId;

    /**
     * 银行卡号
     */
    @Column
    private String bankCardId;

    /**
     * 手机号码
     */
    @Column
    private String phone;

    /**
     * 该银行卡所属的用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBcId() {
        return bcId;
    }

    public void setBcId(Integer bcId) {
        this.bcId = bcId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardId() {
        return idCardId;
    }

    public void setIdCardId(String idCardId) {
        this.idCardId = idCardId;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "bcId=" + bcId +
                ", realName='" + realName + '\'' +
                ", idCardId='" + idCardId + '\'' +
                ", bankCardId='" + bankCardId + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

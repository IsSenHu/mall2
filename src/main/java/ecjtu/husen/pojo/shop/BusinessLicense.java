package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 营业执照
 * @author 11785
 */
@Entity
public class BusinessLicense implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blId;

    /**
     * 注册号
     */
    @Column
    private String registNumber;

    /**
     * 图片地址
     */
    @Column
    private String picPath;

    /**
     * 属于哪个店铺
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "storesId", referencedColumnName = "storesId")
    private Stores stores;

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }

    public Integer getBlId() {
        return blId;
    }

    public void setBlId(Integer blId) {
        this.blId = blId;
    }

    public String getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(String registNumber) {
        this.registNumber = registNumber;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "BusinessLicense{" +
                "blId=" + blId +
                ", registNumber='" + registNumber + '\'' +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}

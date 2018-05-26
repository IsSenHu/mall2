package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 11785
 * 品牌的实体类
 */
@Entity(name = "t_brand")
public class Brand implements Serializable {
    /**
     * 品牌编号，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;
    /**
     * 品牌名称
     */
    @Column
    private String brandName;
    /**
     * 品牌公司名字
     */
    @Column
    private String brandCompanyName;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandCompanyName() {
        return brandCompanyName;
    }

    public void setBrandCompanyName(String brandCompanyName) {
        this.brandCompanyName = brandCompanyName;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", brandCompanyName='" + brandCompanyName + '\'' +
                '}';
    }
}

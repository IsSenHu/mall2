package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 11785
 * 商品规格
 */
@Entity(name = "t_specification")
public class Specification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specificationId;
    /**
     * 商品规格名字
     */
    @Column
    private String specificationName;

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "specificationId=" + specificationId +
                ", specificationName='" + specificationName + '\'' +
                '}';
    }
}

package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 区
 * @author 11785
 */
@Entity
public class District implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtId;

    /**
     * 名字
     */
    @Column
    private String name;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "District{" +
                "districtId=" + districtId +
                ", name='" + name + '\'' +
                '}';
    }
}

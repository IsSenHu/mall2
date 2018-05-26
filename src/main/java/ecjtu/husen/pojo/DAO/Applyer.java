package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 11785
 * 使用对象
 */
@Entity(name = "t_applyer")
public class Applyer implements Serializable {
    /**
     * 使用对象id，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applyerId;
    /**
     * 使用对象名称
     */
    @Column(nullable = false, length = 50)
    private String applyerName;

    /**
     * 所属运动
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "sportId", referencedColumnName = "sportId")
    private Sport sport;

    public Integer getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(Integer applyerId) {
        this.applyerId = applyerId;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "Applyer{" +
                "applyerId=" + applyerId +
                ", applyerName='" + applyerName + '\'' +
                '}';
    }
}

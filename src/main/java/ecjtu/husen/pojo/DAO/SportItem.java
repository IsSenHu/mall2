package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 运动物品
 * @author 11785
 */
@Entity(name = "t_sport_item")
public class SportItem implements Serializable{
    /**
     * Id主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sportItemId;
    /**
     * 运动物品名
     */
    @Column(length = 50, nullable = false)
    private String sportItemName;

    public Integer getSportItemId() {
        return sportItemId;
    }

    public void setSportItemId(Integer sportItemId) {
        this.sportItemId = sportItemId;
    }

    public String getSportItemName() {
        return sportItemName;
    }

    public void setSportItemName(String sportItemName) {
        this.sportItemName = sportItemName;
    }

    @Override
    public String toString() {
        return "SportItem{" +
                "sportItemId=" + sportItemId +
                ", sportItemName='" + sportItemName + '\'' +
                '}';
    }
}

package ecjtu.husen.pojo.shop;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 物流记录
 * @author 11785
 */
@Entity
public class LogisticsRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lrId;

    /**
     * 内容
     */
    @Column
    private String content;

    /**
     * 时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    /**
     * 所属的物流信息
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Logistics logistics;

    public Integer getLrId() {
        return lrId;
    }

    public void setLrId(Integer lrId) {
        this.lrId = lrId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    @Override
    public String toString() {
        return "LogisticsRecord{" +
                "lrId=" + lrId +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}

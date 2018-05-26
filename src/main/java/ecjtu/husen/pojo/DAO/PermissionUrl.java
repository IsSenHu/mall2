package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限和URL的关系
 * @author 11785
 */
@Entity(name = "t_permission_url")
public class PermissionUrl implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer puId;

    /**
     * 关系中的权限
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "permissionId", referencedColumnName = "permissionId")
    private PermissionPO permission;

    /**
     * 关系中的地址
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "urlId", referencedColumnName = "urlId")
    private Url url;

    public Integer getPuId() {
        return puId;
    }

    public void setPuId(Integer puId) {
        this.puId = puId;
    }

    public PermissionPO getPermission() {
        return permission;
    }

    public void setPermission(PermissionPO permission) {
        this.permission = permission;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PermissionUrl{" +
                "puId=" + puId +
                '}';
    }
}

package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限的可访问的url
 * @author 11785
 */
@Entity
public class Url implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer urlId;
    /**
     * url地址
     */
    @Column
    private String url;
    /**
     * url描述
     */
    private String description;
    /**
     * 所拥有的权限和url的关系
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PermissionUrl> permissionUrls = new ArrayList<>();

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PermissionUrl> getPermissionUrls() {
        return permissionUrls;
    }

    public void setPermissionUrls(List<PermissionUrl> permissionUrls) {
        this.permissionUrls = permissionUrls;
    }

    @Override
    public String toString() {
        return "Url{" +
                "urlId=" + urlId +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

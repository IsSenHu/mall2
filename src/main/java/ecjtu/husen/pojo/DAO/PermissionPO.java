package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 */
@Entity(name = "t_permisson")
public class PermissionPO implements Serializable {
    /*
    * 权限ID
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionId;
    /**
     * 权限名字
     */
    @Column
    private String permissionName;
    /*
    * 拥有的url和权限的关系
    * */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PermissionUrl> permissionUrls = new ArrayList<>();
    /**
     * 所拥有的角色和权限的关系
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<RolePermission> rolePermissions = new ArrayList<>();
    /*
    * 权限说明
    * */
    @Column
    private String description;
    /*=======set,get=======*/
    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
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

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    /*======toString=======*/
    @Override
    public String toString() {
        return "PermissionVO{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    /*======hash,equals=======*/
}

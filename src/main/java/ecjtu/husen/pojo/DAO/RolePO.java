package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 */
@Entity(name = "t_role")
public class RolePO implements Serializable{
    /*
    * 角色ID
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    /*
    * 角色名
    * */
    @Column
    private String roleName;
    /*
    * 角色说明
    * */
    @Column
    private String description;
    /*
    * 所拥有的角色和权限的关系
    * fetch=FetchType.EAGER 立即加载，默认是延迟加载
    * */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<RolePermission> rolePermissions = new ArrayList<>();
    /**
     * 该角色拥有的角色用户关系
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserRolePO> userRolePOS = new ArrayList<>();
    /*=========get,set==========*/
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    @Override
    public String toString() {
        return "RolePO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public List<UserRolePO> getUserRolePOS() {
        return userRolePOS;
    }

    public void setUserRolePOS(List<UserRolePO> userRolePOS) {
        this.userRolePOS = userRolePOS;
    }
}

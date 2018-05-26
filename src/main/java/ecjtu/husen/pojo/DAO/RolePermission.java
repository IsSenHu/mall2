package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色和权限的关系
 * @author 11785
 */
@Entity(name = "t_role_permission")
public class RolePermission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rpId;

    /**
     * 关系里的角色
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private RolePO role;

    /**
     * 关系里的权限
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "permissionId", referencedColumnName = "permissionId")
    private PermissionPO permission;

    public Integer getRpId() {
        return rpId;
    }

    public void setRpId(Integer rpId) {
        this.rpId = rpId;
    }

    public RolePO getRole() {
        return role;
    }

    public void setRole(RolePO role) {
        this.role = role;
    }

    public PermissionPO getPermission() {
        return permission;
    }

    public void setPermission(PermissionPO permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "rpId=" + rpId +
                '}';
    }
}

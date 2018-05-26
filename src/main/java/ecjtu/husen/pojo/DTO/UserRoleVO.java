package ecjtu.husen.pojo.DTO;

import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.UserPO;

import java.io.Serializable;

public class UserRoleVO implements Serializable {
    private Integer id;
    private UserPO userPO;
    private RolePO rolePO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public RolePO getRolePO() {
        return rolePO;
    }

    public void setRolePO(RolePO rolePO) {
        this.rolePO = rolePO;
    }

    @Override
    public String toString() {
        return "UserRoleVO{" +
                "id=" + id +
                ", userPO=" + userPO +
                ", rolePO=" + rolePO +
                '}';
    }
}

package ecjtu.husen.pojo.DAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 11785
 */
@Entity(name = "t_user_role")
public class UserRolePO implements Serializable {
    /*
    * ID
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*
    * 一个关系里的用户
    * */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserPO userPO;
    /*
    * 一个关系里的角色
    * */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private RolePO rolePO;
    /*=====set,get=====*/
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
    /*======toString======*/
    @Override
    public String toString() {
        return "UserRolePO{" +
                "id=" + id +
                ", userPO=" + userPO +
                ", rolePO=" + rolePO +
                '}';
    }
    /*======hash,equals======*/
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRolePO that = (UserRolePO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userPO, that.userPO) &&
                Objects.equals(rolePO, that.rolePO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userPO, rolePO);
    }
}

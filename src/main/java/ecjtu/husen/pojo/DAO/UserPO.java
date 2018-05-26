package ecjtu.husen.pojo.DAO;

import ecjtu.husen.pojo.DTO.Gender;
import ecjtu.husen.pojo.DTO.UserStatu;
import ecjtu.husen.pojo.converter.GenderConverter;
import ecjtu.husen.pojo.converter.UserStatuConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 11785
 * User的数据访问对象
 */
@Entity(name = "t_user")
public class UserPO implements Serializable{
    /*
    * 用户ID
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    /*
    * 用户名
    * */
    @Column
    private String username;
    /*
    * 密码
    * */
    @Column
    private String password;
    /*
    * 真实姓名
    * */
    @Column
    private String realName;
    /*
    * 性别
    * 使用枚举类
    * */
    @Column(name = "gender", columnDefinition = "int not null COMMENT '" + Gender.Doc + "'")
    @Convert(converter = GenderConverter.class)
    private Gender gender;
    /*
    * 邮箱
    * */
    @Column
    private String email;
    /*
    * 联系电话
    * */
    @Column
    private String phone;
    /*
    * 手机
    * */
    @Column
    private String mobilePhone;
    /*
    * 状态
    * 使用枚举类
    * */
    @Column(name = "statu", columnDefinition = "int not null COMMENT'" + UserStatu.Doc + "'")
    @Convert(converter = UserStatuConverter.class)
    private UserStatu userStatu;
    /*
    * 用户创建时间
    * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    /*
    * 该用户拥有的用户角色关系
    * */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserRolePO> userRolePOS = new ArrayList<>();

    /*======set,get======*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmali() {
        return email;
    }

    public void setEmali(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public UserStatu getUserStatu() {
        return userStatu;
    }

    public void setUserStatu(UserStatu userStatu) {
        this.userStatu = userStatu;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /*=====toString======*/
    @Override
    public String toString() {
        return "UserPO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", gender=" + gender +
                ", emali='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", userStatu=" + userStatu +
                ", createDate=" + createDate +
                '}';
    }
    /*======hash,equals======*/
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        UserPO userPO = (UserPO) o;
        return Objects.equals(userId, userPO.userId) &&
                Objects.equals(username, userPO.username) &&
                Objects.equals(realName, userPO.realName) &&
                Objects.equals(email, userPO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, realName, email);
    }

    public List<UserRolePO> getUserRolePOS() {
        return userRolePOS;
    }

    public void setUserRolePOS(List<UserRolePO> userRolePOS) {
        this.userRolePOS = userRolePOS;
    }
}

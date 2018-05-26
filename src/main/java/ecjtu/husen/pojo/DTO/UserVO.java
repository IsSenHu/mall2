package ecjtu.husen.pojo.DTO;

import ecjtu.husen.pojo.DAO.UserRolePO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 11785
 * User的数据传输对象
 */
public class UserVO implements Serializable{
    private Integer userId;
    private String username;
    private String password;
    private String realName;
    private Gender gender;
    private String email;
    private String phone;
    private String mobilePhone;
    private UserStatu userStatu;
    private Date createDate;
    private List<UserRolePO> userRolePOS = new ArrayList<>();

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

    public String getEmail() {
        return email;
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

    public List<UserRolePO> getUserRolePOS() {
        return userRolePOS;
    }

    public void setUserRolePOS(List<UserRolePO> userRolePOS) {
        this.userRolePOS = userRolePOS;
    }

    @Override
    public String toString() {
        return "UserVO{" +
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
}

package com.husen.mall2.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 手机注册的vo
 * @author husen
 */
public class RegisterForPhone implements Serializable {
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    private String code;
    @Length(min = 6, max = 16, message = "密码长度必须在6到16之间")
    private String password;
    @Length(min = 6, max = 16, message = "重复密码长度必须在6到16之间")
    private String password2;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public String toString() {
        return "RegisterForPhone{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                '}';
    }
}

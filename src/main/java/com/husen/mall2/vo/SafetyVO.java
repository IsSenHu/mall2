package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 账户安全的vo
 * @author husen
 */
public class SafetyVO implements Serializable {
    private String username;
    private Integer score;
    private String phone;
    private String email;
    private String header;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

package com.husen.mall2.model;

import ecjtu.husen.pojo.shop.City;
import ecjtu.husen.pojo.shop.District;
import ecjtu.husen.pojo.shop.Province;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地址
 * @author 11785
 */
@Entity(name = "t_address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    /**
     * 收货人
     */
    @Column
    private String receivePersonName;

    /**
     * 手机号码
     */
    @Column
    private String phone;

    /**
     * 省
     */
    @Column
    private String province;

    /**
     * 市
     */
    @Column
    private String city;

    /**
     * 区
     */
    @Column
    private String area;

    /**
     * 详细地址
     */
    @Column
    private String address;

    /**
     * 所属用户
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    /**
     * 是否默认地址
     * */
    private Integer isDefault;

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getReceivePersonName() {
        return receivePersonName;
    }

    public void setReceivePersonName(String receivePersonName) {
        this.receivePersonName = receivePersonName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", receivePersonName='" + receivePersonName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

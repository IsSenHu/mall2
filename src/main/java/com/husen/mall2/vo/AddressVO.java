package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 地址的vo
 * @author husen
 */
public class AddressVO implements Serializable {
    private Integer addressId;
    private String receivePersonName;
    private String phone;
    private String provinceid;
    private String cityid;
    private String areaid;
    private String detailAddress;

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

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    @Override
    public String toString() {
        return "AddressVO{" +
                "addressId=" + addressId +
                ", receivePersonName='" + receivePersonName + '\'' +
                ", phone='" + phone + '\'' +
                ", provinceid='" + provinceid + '\'' +
                ", cityid='" + cityid + '\'' +
                ", areaid='" + areaid + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }
}

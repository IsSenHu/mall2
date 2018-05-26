package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 物流信息的vo
 * @author husen
 */
public class LogisticsVO implements Serializable {
    private Integer id;
    private String company;
    private String expressNumber;
    private String phone;
    private String statu;
    private List<String> notes;

    public LogisticsVO(Integer id, String company, String expressNumber, List<String> notes, String phone, String statu) {
        super();
        this.id = id;
        this.company = company;
        this.expressNumber = expressNumber;
        this.notes = notes;
        this.phone = phone;
        this.statu = statu;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LogisticsVO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "LogisticsVO{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", expressNumber='" + expressNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", notes=" + notes +
                '}';
    }
}

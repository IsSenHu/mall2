package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * @Author: HuSen
 * @Description: 适用者的vo
 * @Date: Created in 20:38 2018/4/22
 * @Modified By:
 */
public class Applyer implements Serializable {
    private Integer applyerId;
    private String applyerName;

    public Integer getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(Integer applyerId) {
        this.applyerId = applyerId;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    @Override
    public String toString() {
        return "Applyer{" +
                "applyerId=" + applyerId +
                ", applyerName='" + applyerName + '\'' +
                '}';
    }
}

package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 适用者的id和名称
 * @author husen
 */
public class FindApplyer implements Serializable {
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
        return "FindApplyer{" +
                "applyerId=" + applyerId +
                ", applyerName='" + applyerName + '\'' +
                '}';
    }
}

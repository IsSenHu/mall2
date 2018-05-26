package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: HuSen
 * @Description: 运动物品的vo
 * @Date: Created in 20:36 2018/4/22
 * @Modified By:
 */
public class SportItem implements Serializable{
    private Integer sportItemId;
    private String sportItemName;
    private List<Applyer> applyers;

    public Integer getSportItemId() {
        return sportItemId;
    }

    public void setSportItemId(Integer sportItemId) {
        this.sportItemId = sportItemId;
    }

    public String getSportItemName() {
        return sportItemName;
    }

    public void setSportItemName(String sportItemName) {
        this.sportItemName = sportItemName;
    }

    public List<Applyer> getApplyers() {
        return applyers;
    }

    public void setApplyers(List<Applyer> applyers) {
        this.applyers = applyers;
    }

    @Override
    public String toString() {
        return "SportItem{" +
                "sportItemId=" + sportItemId +
                ", sportItemName='" + sportItemName + '\'' +
                ", applyers=" + applyers +
                '}';
    }
}

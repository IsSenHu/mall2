package com.husen.mall2.vo;

import java.io.Serializable;

public class GoodVONew extends GoodVO implements Serializable {
    /**
     * 上架还是下架
     * */
    private String upOrDown;

    private Integer footId;

    public Integer getFootId() {
        return footId;
    }

    public void setFootId(Integer footId) {
        this.footId = footId;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }

    @Override
    public String toString() {
        return "GoodVONew{" +
                "upOrDown='" + upOrDown + '\'' +
                ", footId=" + footId +
                '}';
    }
}

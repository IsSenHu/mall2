package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 材质的id和名称
 * @author husen
 */
public class FindMaterial implements Serializable {
    private Integer materialId;
    private String materialName;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public String toString() {
        return "FindMaterial{" +
                "materialId=" + materialId +
                ", materialName='" + materialName + '\'' +
                '}';
    }
}

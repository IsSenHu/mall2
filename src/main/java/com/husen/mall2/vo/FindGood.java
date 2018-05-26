package com.husen.mall2.vo;

import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Brand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回的条件集合
 * @author husen
 */
public class FindGood implements Serializable {
    private List<FindBrand> brands = new ArrayList<>();
    private List<FindApplyer> applyers = new ArrayList<>();
    private List<FindMaterial> materials = new ArrayList<>();;

    public List<FindBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<FindBrand> brands) {
        this.brands = brands;
    }

    public List<FindApplyer> getApplyers() {
        return applyers;
    }

    public void setApplyers(List<FindApplyer> applyers) {
        this.applyers = applyers;
    }

    public List<FindMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<FindMaterial> materials) {
        this.materials = materials;
    }

    @Override
    public String toString() {
        return "FindGood{" +
                "brands=" + brands +
                ", applyers=" + applyers +
                ", materials=" + materials +
                '}';
    }
}

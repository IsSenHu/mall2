package com.husen.mall2.vo;

import com.husen.mall2.model.Areas;
import com.husen.mall2.model.Cities;

import java.io.Serializable;
import java.util.List;

/**
 * 市和区的vo
 * @author husen
 */
public class CityAndArea implements Serializable {
    private List<Cities> cities;
    private List<Areas> areas;

    public List<Cities> getCities() {
        return cities;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }

    public List<Areas> getAreas() {
        return areas;
    }

    public void setAreas(List<Areas> areas) {
        this.areas = areas;
    }
}

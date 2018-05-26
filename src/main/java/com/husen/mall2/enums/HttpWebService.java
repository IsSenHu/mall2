package com.husen.mall2.enums;

import java.io.Serializable;

/**
 * http接口地址枚举类
 * @author husen
 */
public enum HttpWebService implements Serializable {
    ITEMS("http://localhost:8080/ws/item/itemsId", "根据条件查询item的接口"),
    COUNT_ITEMS("http://localhost:8080/ws/item/countItems", "根据查询条件查询出总结果数"),
    FIND_GOODS_CONDITION("http://localhost:8080/ws/item/findGoods", "查询条件"),
    FIND_BRANDS("http://localhost:8080/ws/item/findBrands", "查询品牌"),
    FIND_APPLYERS("http://localhost:8080/ws/item/findApplyers", "查询适用者"),
    FIND_MATERIALS("http://localhost:8080/ws/item/findMaterials", "查询材质"),
    RECOMMEND("http://localhost:8080/ws/item/recommend", "推荐商品"),
    ITEM("http://localhost:8080/ws/item/itemById", "根据id查询商品"),
    ALL_SPORT("http://localhost:8080/ws/rest/sports", "获取所有的运动"),
    FIND_ITEM_BY_SPORT("http://localhost:8080/ws/rest/sports/sportItems", "根据运动名称查询所有的运动物品"),
    FIND_APPLYER_BY_ITEM("http://localhost:8080/ws/rest/sports/applyers", "根据运动物品查询出所有的适用者");

    private String value;
    private String description;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    HttpWebService(String value, String description) {
        this.value = value;
        this.description = description;
    }
}

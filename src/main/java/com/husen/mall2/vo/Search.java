package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * 查询条件
 * @author husen
 */
public class Search implements Serializable {
    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 运动物品
     */
    private Integer sportItemId;

    /**
     * 品牌
     */
    private Integer brandId;

    /**
     * 适用者
     */
    private Integer applyerId;

    /**
     * 材质
     */
    private Integer materialId;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 排序方式
     */
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getSportItemId() {
        return sportItemId;
    }

    public void setSportItemId(Integer sportItemId) {
        this.sportItemId = sportItemId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(Integer applyerId) {
        this.applyerId = applyerId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    @Override
    public String toString() {
        return "Search{" +
                "goodName='" + goodName + '\'' +
                ", sportItemId=" + sportItemId +
                ", brandId=" + brandId +
                ", applyerId=" + applyerId +
                ", materialId=" + materialId +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", sort=" + sort +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return Objects.equals(goodName, search.goodName) &&
                Objects.equals(sportItemId, search.sportItemId) &&
                Objects.equals(brandId, search.brandId) &&
                Objects.equals(applyerId, search.applyerId) &&
                Objects.equals(materialId, search.materialId) &&
                Objects.equals(currentPage, search.currentPage) &&
                Objects.equals(pageSize, search.pageSize) &&
                Objects.equals(sort, search.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodName, sportItemId, brandId, applyerId, materialId, currentPage, pageSize, sort);
    }
}

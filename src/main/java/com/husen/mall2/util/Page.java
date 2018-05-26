package com.husen.mall2.util;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    //当前页码
    private int currentPage;
    //总页数
    private int totalPage;
    //总条数
    private long rowsTotal;
    //每页显示条数
    private int pageSize;
    //返回数据的集合
    private List<T> content = new ArrayList<>();

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getRowsTotal() {
        return rowsTotal;
    }

    public void setRowsTotal(long rowsTotal) {
        this.rowsTotal = rowsTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", rowsTotal=" + rowsTotal +
                ", pageSize=" + pageSize +
                '}';
    }
}

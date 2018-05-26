package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 我的足迹的vo
 * @author husen
 */
public class FootVO implements Serializable {
    private List<GoodVONew> today;

    private List<GoodVONew> week;

    private GoodVONew firstToday;
    private GoodVONew firstWeek;

    public GoodVONew getFirstToday() {
        return firstToday;
    }

    public void setFirstToday(GoodVONew firstToday) {
        this.firstToday = firstToday;
    }

    public GoodVONew getFirstWeek() {
        return firstWeek;
    }

    public void setFirstWeek(GoodVONew firstWeek) {
        this.firstWeek = firstWeek;
    }

    public List<GoodVONew> getToday() {
        return today;
    }

    public void setToday(List<GoodVONew> today) {
        this.today = today;
    }

    public List<GoodVONew> getWeek() {
        return week;
    }

    public void setWeek(List<GoodVONew> week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "FootVO{" +
                "today=" + today +
                ", week=" + week +
                ", firstToday=" + firstToday +
                ", firstWeek=" + firstWeek +
                '}';
    }
}

package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: HuSen
 * @Description: 频道
 * @Date: Created in 20:31 2018/4/22
 * @Modified By:
 */
public class Channel implements Serializable {
    private String channelName;
    private String icon;
    private List<SportItem> sportItems;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<SportItem> getSportItems() {
        return sportItems;
    }

    public void setSportItems(List<SportItem> sportItems) {
        this.sportItems = sportItems;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelName='" + channelName + '\'' +
                ", sportItems=" + sportItems +
                '}';
    }
}

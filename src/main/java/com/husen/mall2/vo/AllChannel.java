package com.husen.mall2.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: HuSen
 * @Description: 商城频道展示vo
 * @Date: Created in 20:26 2018/4/22
 * @Modified By:
 */
public class AllChannel implements Serializable {
    private List<Channel> channels;

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return "AllChannel{" +
                "channels=" + channels +
                '}';
    }
}

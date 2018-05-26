package com.husen.mall2.transform;

import com.alibaba.fastjson.JSONArray;
import com.husen.mall2.enums.HttpWebService;
import com.husen.mall2.util.HttpClientUtil;
import com.husen.mall2.vo.Channel;
import com.husen.mall2.vo.Icon;
import com.husen.mall2.vo.SportItem;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.Sport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 21:05 2018/4/22
 * @Modified By:
 */
@Component
public class TransformChannel implements Function<String, List<Channel>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransformChannel.class);
    @Override
    public List<Channel> apply(String data) {
        List<Channel> channels = new ArrayList<>();
        if(StringUtils.isNotBlank(data)){
            try {
                List<Sport> sports = com.alibaba.fastjson.JSONArray.parseArray(data, Sport.class);
                for(Sport sport : sports){
                    Channel channel = new Channel();
                    channel.setIcon(getIcon(sport.getSportName()));
                    channel.setChannelName(sport.getSportName() + "频道");
                    String itemsData = HttpClientUtil.getData(HttpWebService.FIND_ITEM_BY_SPORT.getValue() + "/" + sport.getSportId());
                    List<ecjtu.husen.pojo.DAO.SportItem> sportItems = JSONArray.parseArray(itemsData, ecjtu.husen.pojo.DAO.SportItem.class);
                    List<SportItem> sportItemList = new ArrayList<>();
                    sportItems.stream().forEach(x -> {
                        SportItem sportItem = new SportItem();
                        sportItem.setSportItemId(x.getSportItemId());
                        sportItem.setSportItemName(x.getSportItemName());
                        try {
                            String applyersData = HttpClientUtil.getData(HttpWebService.FIND_APPLYER_BY_ITEM.getValue() + "/" + sport.getSportId());
                            List<Applyer> applyers = JSONArray.parseArray(applyersData, Applyer.class);
                            List<com.husen.mall2.vo.Applyer> applyerList = new ArrayList<>();
                            applyers.stream().forEach(y -> {
                                com.husen.mall2.vo.Applyer applyer = new com.husen.mall2.vo.Applyer();
                                BeanUtils.copyProperties(y, applyer);
                                applyerList.add(applyer);
                            });
                            sportItem.setApplyers(applyerList);
                        } catch (IOException e) {
                            LOGGER.error("调用" + HttpWebService.FIND_APPLYER_BY_ITEM + "接口失败:{}", e.getMessage());
                            e.printStackTrace();
                        }
                        sportItemList.add(sportItem);
                    });
                    channel.setSportItems(sportItemList);
                    channels.add(channel);
                }
            }catch (Exception e){
                LOGGER.error("调用" + HttpWebService.FIND_ITEM_BY_SPORT + "接口失败:{}", e.getMessage());
                e.printStackTrace();
            }
            return channels;
        }else {
            return channels;
        }
    }

    /**
     * 得到图标
     * @param sportName
     * @return
     */
    private String getIcon(String sportName){
        String icon = "";
        switch (sportName){
            case "羽毛球" : icon = Icon.BADMINTON;
                            break;
            case "兵乓球" : icon = Icon.PBBALL;
                            break;
            case "篮球" : icon = Icon.BASKETBALL;
                            break;
            case "足球" : icon = Icon.FOOTBALL;
                            break;
            case "排球" : icon = Icon.VOLLEYBALL;
                            break;
            case "跑步" : icon = Icon.RUN;
                            break;
            case "滑雪" : icon = Icon.SLID;
                            break;
            case "户外" : icon = Icon.OUTSIDE;
                            break;
            case "网球" : icon = Icon.TENNIS;
                            break;
            default : icon = Icon.OTHER;
                            break;
        }
        return icon;
    }
}

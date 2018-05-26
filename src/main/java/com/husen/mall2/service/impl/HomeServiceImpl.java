package com.husen.mall2.service.impl;

import com.husen.mall2.enums.HttpWebService;
import com.husen.mall2.service.HomeService;
import com.husen.mall2.transform.TransformChannel;
import com.husen.mall2.util.HttpClientUtil;
import com.husen.mall2.vo.AllChannel;
import com.husen.mall2.vo.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:45 2018/4/22
 * @Modified By:
 */
@Service
@Transactional
public class HomeServiceImpl implements HomeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeServiceImpl.class);
    @Autowired
    private TransformChannel transformChannel;

    @Override
    public AllChannel allChannel() {
        try {
            //先获取所有的运动
            String sportsData = HttpClientUtil.getData(HttpWebService.ALL_SPORT.getValue());
            LOGGER.info("调用接口查询的所有的运动为:{}", sportsData);
            //用这些运动去构造频道
            List<Channel> channels = transformChannel.apply(sportsData);
            LOGGER.info("产生的频道为:{}", channels);
            AllChannel allChannel = new AllChannel();
            allChannel.setChannels(channels);
            return allChannel;
        } catch (IOException e) {
            LOGGER.error("生成频道失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

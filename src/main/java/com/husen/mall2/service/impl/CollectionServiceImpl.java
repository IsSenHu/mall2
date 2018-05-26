package com.husen.mall2.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.husen.mall2.model.Good;
import com.husen.mall2.repository.GoodRepository;
import com.husen.mall2.service.CollectionService;
import com.husen.mall2.vo.GoodCollectionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author husen
 */
@Service
@Transactional
public class CollectionServiceImpl implements CollectionService{
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public List<GoodCollectionVO> findAllCollectionByUserId(String username) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value = operations.get(username + "collects");
        if(value != null){
            List<Integer> values = JSONArray.parseArray(value, Integer.class);
            List<Good> goods = goodRepository.findAllByGoodIdIn(values);
            List<GoodCollectionVO> goodCollectionVOS = new ArrayList<>();
            goods.stream().forEach(x -> {
                GoodCollectionVO goodCollectionVO = new GoodCollectionVO();
                goodCollectionVO.setOriginalPrice(x.getOriginalPrice());
                goodCollectionVO.setSalePrice(x.getSalePrice());
                goodCollectionVO.setAccumulatedSales(x.getAccumulatedSales());
                goodCollectionVO.setGoodId(x.getGoodId());
                goodCollectionVO.setGoodName(x.getGoodName());
                goodCollectionVO.setFirstPic(StringUtils.isNotBlank(x.getPisc()) ? x.getPisc().split("connectionRegex")[0] : "");
                goodCollectionVOS.add(goodCollectionVO);
            });
            return goodCollectionVOS;
        }else {
            return new ArrayList<>();
        }
    }
}

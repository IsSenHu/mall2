package com.husen.mall2.service.impl;

import com.husen.mall2.model.Foot;
import com.husen.mall2.model.FootPrint;
import com.husen.mall2.model.Good;
import com.husen.mall2.repository.FootPrintRepository;
import com.husen.mall2.repository.FootRepository;
import com.husen.mall2.repository.GoodRepository;
import com.husen.mall2.service.FootPrintService;
import com.husen.mall2.vo.FootVO;
import com.husen.mall2.vo.GoodVO;
import com.husen.mall2.vo.GoodVONew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author husen
 */
@Service
@Transactional
public class FootPrintServiceImpl implements FootPrintService {
    private final static Logger LOGGER = LoggerFactory.getLogger(FootPrintServiceImpl.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private FootPrintRepository footPrintRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private FootRepository footRepository;
    @Override
    public void save(FootPrint footPrint) {
        //记录该商品的浏览次数
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Integer userId = footPrint.getUser().getUserId();
        Integer goodId = footPrint.getGood().getGoodId();
        //key=usrId_goodId
        String key = userId + "_" + goodId;
        //如果没有，则设为0
        String lookTimes = operations.get(key);
        if(lookTimes == null){
            operations.set(key, "0_" + goodId);
        }else {
            String newLookTimes = (Integer.valueOf(lookTimes.split("_")[0]) + 1) + "_" + goodId;
            operations.set(key, newLookTimes);
        }
        Foot foot = new Foot();
        foot.setFootDate(new Date());
        foot.setUserId(userId);
        foot.setGoodId(goodId);
        footRepository.save(foot);
    }

    @Override
    public List<GoodVO> guestYouLike(Integer userId) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Set<String> keys = operations.getOperations().keys(userId + "_*");
        LOGGER.info("查到到的足迹数量为：{}", keys.size());
        List<String> countGoodList = new ArrayList<>();
        keys.stream().forEach(x -> countGoodList.add(operations.get(x)));
        LOGGER.info("查询到的足迹数量为：{}", countGoodList.size());
        String[] cgl = countGoodList.toArray(new String[countGoodList.size()]);
        Arrays.sort(cgl, Comparator.comparingInt(x -> Integer.MAX_VALUE - Integer.valueOf(x.split("_")[1])));
        LOGGER.info("查询到的足迹数量为：{}", cgl.length);
        List<String> lastCgl = new ArrayList<>();
        //最多取12条
        for(int i = 0; i < cgl.length; i++){
            if(i > 12){
                break;
            }
            lastCgl.add(cgl[i]);
        }
        LOGGER.info("查询到的足迹数量为：{}", lastCgl.size());
        List<Good> goods = goodRepository.findAllByGoodIdIn(lastCgl.stream().map(x -> Integer.valueOf(x.split("_")[1])).collect(Collectors.toList()));
        List<GoodVO> goodVOS = new ArrayList<>();
        goods.stream().forEach(x -> {
            GoodVO goodVO = new GoodVO();
            goodVO.setGoodId(x.getGoodId());
            goodVO.setGoodName(x.getGoodName());
            goodVO.setPrice(x.getLastPrice());
            goodVO.setAccumulatedSales(x.getAccumulatedSales());
            if(x.getPisc() != null){
                goodVO.setFirstPic(x.getPisc().split("connectionRegex")[0]);
            }
            goodVOS.add(goodVO);
        });
        return goodVOS;
    }

    @Override
    public List<GoodVO> guestYouLike() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Set<String> keys = operations.getOperations().keys("*_*");
        LOGGER.info("查到到的足迹数量为：{}", keys.size());
        List<String> countGoodList = new ArrayList<>();
        keys.stream().forEach(x -> countGoodList.add(operations.get(x)));
        LOGGER.info("查询到的足迹数量为：{}", countGoodList.size());
        String[] cgl = countGoodList.toArray(new String[countGoodList.size()]);
        if(cgl.length >= 1){
            Arrays.sort(cgl, Comparator.comparingInt(x -> Integer.MAX_VALUE - Integer.valueOf(x.split("_")[1])));
            LOGGER.info("查询到的足迹数量为：{}", cgl.length);
            List<String> lastCgl = new ArrayList<>();
            //最多取12条
            for(int i = 0; i < cgl.length; i++){
                if(i > 12){
                    break;
                }
                lastCgl.add(cgl[i]);
            }
            LOGGER.info("查询到的足迹数量为：{}", lastCgl.size());
            List<Good> goods = goodRepository.findAllByGoodIdIn(lastCgl.stream().map(x -> Integer.valueOf(x.split("_")[1])).collect(Collectors.toList()));
            List<GoodVO> goodVOS = new ArrayList<>();
            goods.stream().forEach(y -> {
                GoodVO goodVO = new GoodVO();
                goodVO.setGoodId(y.getGoodId());
                goodVO.setGoodName(y.getGoodName());
                goodVO.setPrice(y.getLastPrice());
                goodVO.setAccumulatedSales(y.getAccumulatedSales());
                if(y.getPisc() != null){
                    goodVO.setFirstPic(y.getPisc().split("connectionRegex")[0]);
                }
                goodVOS.add(goodVO);
            });
            return goodVOS;
        }else {
            return Collections.emptyList();
        }
    }

    @Override
    public void add(FootPrint footPrint) {
        footPrintRepository.save(footPrint);
    }

    @Override
    public FootVO findAllByUserId(Integer userId) {
        List<Foot> todayFoot = footRepository.findAllByUserIdAndFootDateEquals(userId, new Date());
        List<Integer> goodIdList = new ArrayList<>();
        List<Integer> footIdList = new ArrayList<>();
        todayFoot.stream().forEach(x -> {
            if(goodIdList.contains(x.getGoodId())){
                footIdList.add(x.getFootId());
            }else {
                goodIdList.add(x.getGoodId());
            }
        });
        List<Foot> distinctToday = todayFoot.stream().filter(x -> !footIdList.contains(x.getFootId())).collect(Collectors.toList());
        List<GoodVONew> today = new ArrayList<>();
        distinctToday.stream().forEach(x -> {
            Good good = goodRepository.findById(x.getGoodId()).get();
            GoodVONew goodVONew = new GoodVONew();
            goodVONew.setGoodId(x.getGoodId());
            goodVONew.setFootId(x.getFootId());
            goodVONew.setGoodName(good.getGoodName());
            goodVONew.setPrice(good.getLastPrice());
            goodVONew.setAccumulatedSales(good.getAccumulatedSales());
            goodVONew.setUpOrDown(good.getIsShelves());
            if(good.getPisc() != null && good.getPisc().trim().length() > 0){
                goodVONew.setFirstPic(good.getPisc().split("connectionRegex")[0]);
            }
            today.add(goodVONew);
        });
        int dayLength = LocalDate.now().getDayOfWeek().getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        LocalDateTime start = LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH) - (dayLength - 1), 0, 0);
        LocalDateTime end = LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH) + (7 - dayLength), 0, 0);
        List<Foot> weekFoot = footRepository.findAllByUserIdAndFootDateGreaterThanEqualAndFootDateLessThanEqual(userId, Date.from(start.atZone(ZoneId.systemDefault()).toInstant()), Date.from(end.atZone(ZoneId.systemDefault()).toInstant()));
        //去重
        List<Integer> goodIdList2 = new ArrayList<>();
        List<Integer> footIdList2 = new ArrayList<>();
        todayFoot.stream().forEach(x -> {
            if(goodIdList2.contains(x.getGoodId())){
                footIdList2.add(x.getFootId());
            }else {
                goodIdList2.add(x.getGoodId());
            }
        });
        List<Foot> distinctWeek= weekFoot.stream().filter(x -> !footIdList2.contains(x.getFootId())).collect(Collectors.toList());
        List<GoodVONew> week = new ArrayList<>();
        distinctWeek.stream().forEach(y -> {
            Good good = goodRepository.findById(y.getGoodId()).get();
            GoodVONew goodVONew = new GoodVONew();
            goodVONew.setGoodId(y.getGoodId());
            goodVONew.setFootId(y.getFootId());
            goodVONew.setGoodName(good.getGoodName());
            goodVONew.setPrice(good.getLastPrice());
            goodVONew.setAccumulatedSales(good.getAccumulatedSales());
            goodVONew.setUpOrDown(good.getIsShelves());
            if(good.getPisc() != null && good.getPisc().trim().length() > 0){
                goodVONew.setFirstPic(good.getPisc().split("connectionRegex")[0]);
            }
            week.add(goodVONew);
        });
        FootVO footVO = new FootVO();
        if(today.size() > 0){
            footVO.setFirstToday(today.get(0));
            today.remove(0);
        }
        footVO.setToday(today);
        if(week.size() > 0){
            footVO.setFirstWeek(week.get(0));
            week.remove(0);
        }
        footVO.setWeek(week);
        return footVO;
    }

    @Override
    public void delete(Integer footId, Integer userId) {
        //删除数据库的记录
        Foot foot = footRepository.findById(footId).get();
        Integer goodId = foot.getGoodId();
        footRepository.delete(foot);
        //删除redis的记录
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.getOperations().delete(userId + "_" + goodId);
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayLength = LocalDate.now().getDayOfWeek().getValue();
        System.out.println(dayLength);
    }
}

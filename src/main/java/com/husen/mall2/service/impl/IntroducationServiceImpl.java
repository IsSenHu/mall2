package com.husen.mall2.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.husen.mall2.controller.IntroductionController;
import com.husen.mall2.enums.HttpWebService;
import com.husen.mall2.model.Coupon;
import com.husen.mall2.model.Good;
import com.husen.mall2.model.ShopPromotions;
import com.husen.mall2.repository.CouponRepository;
import com.husen.mall2.repository.GoodRepository;
import com.husen.mall2.repository.ReviewRepository;
import com.husen.mall2.repository.ShopPromotionsRepository;
import com.husen.mall2.service.IntroductionService;
import com.husen.mall2.util.HttpClientUtil;
import com.husen.mall2.vo.GoodIntroductionVO;
import com.husen.mall2.vo.GoodVO;
import com.husen.mall2.vo.Search;
import ecjtu.husen.pojo.DAO.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author husen
 */
@Service
@Transactional
public class IntroducationServiceImpl implements IntroductionService {
    private final static Logger LOGGER = LoggerFactory.getLogger(IntroductionController.class);

    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ShopPromotionsRepository shopPromotionsRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<Integer> coupons(Integer goodId) {
        List<Coupon> coupons = couponRepository.findAllByGood_GoodId(goodId);
        return coupons.stream().map(x -> x.getId()).collect(Collectors.toList());
    }

    /**
     * 返回商品介绍页所需信息
     * @param goodId
     * @return
     */
    @Override
    public GoodIntroductionVO introduction(Integer goodId) {
        Good good = goodRepository.findById(goodId).get();
        GoodIntroductionVO goodIntroduction = new GoodIntroductionVO();
        goodIntroduction.setGoodId(goodId);
        goodIntroduction.setGoodName(good.getGoodName());
        if(good.getMonthlySales() == null){
            goodIntroduction.setMonthSaleNumber(0);
        }else {
            goodIntroduction.setMonthSaleNumber(good.getMonthlySales());
        }
        if(good.getAccumulatedSales() == null){
            goodIntroduction.setSaleNumber(0);
        }else {
            goodIntroduction.setSaleNumber(good.getAccumulatedSales());
        }
        goodIntroduction.setOriginalPrice(good.getOriginalPrice());
        goodIntroduction.setSalePrice(good.getSalePrice());
        String detailPic = "<img src=\"" + good.getDetailpic() +"\"/>";
        goodIntroduction.setDetailPic(detailPic);
        if(good.getPostageMoney() == null || good.getPostageMoney().equals(0.00)){
            goodIntroduction.setPostage(0.00);
        }else {
            goodIntroduction.setPostage(good.getPostageMoney());
        }
        Item item = null;
        try {
            String itemStr = HttpClientUtil.getData(HttpWebService.ITEM.getValue() + "/" + good.getItemId());
            LOGGER.info("调用接口成功，查询到的item是：{}", itemStr);
            item = JSON.parseObject(itemStr, Item.class);
            goodIntroduction.setItem(item);
        } catch (IOException e) {
            LOGGER.error("调用接口失败：{}", e.getMessage());
        }
        goodIntroduction.setPics(pics(good.getPisc()));
        goodIntroduction.setPicsPhone(picsPhone(good.getPisc()));
        if(goodIntroduction.getPics().size() > 0){
            String pic = good.getPisc().split("connectionRegex")[0];
            String firstPic = "<a href=\"" + pic + "\"><img src=\"" + pic + "\" alt=\"细节展示放大镜特效\" rel=\"" + pic + "\" class=\"jqzoom\" /></a>";
            LOGGER.info("firstPic：{}", firstPic);
            goodIntroduction.setFirstPic(firstPic);
        }
        goodIntroduction.setShopPromotions(shopPromotions(shopPromotionsRepository.findAllByGood_GoodId(goodId), item.getSpecification().getSpecificationName()));
        goodIntroduction.setCoupons(coupons(couponRepository.findAllByGood_GoodId(goodId)));
        //累计评论
        goodIntroduction.setReviewNumber(reviewRepository.countByGood_GoodId(goodId).intValue());
        //看了又看
        Search search = new Search();
        search.setBrandId(item.getBrand().getBrandId());
        search.setSportItemId(item.getSportItem().getSportItemId());
        search.setMaterialId(item.getMaterial().getMaterialId());
        search.setApplyerId(item.getApplyer().getApplyerId());
        String itemIdsStr = null;
        try {
            itemIdsStr = HttpClientUtil.getData(HttpWebService.ITEMS.getValue() + SearchServiceImpl.paramsStr(search));
            LOGGER.info("调用接口查询结果为：{}", itemIdsStr);
            List<Integer> itemIds = JSONObject.parseArray(itemIdsStr, Integer.class);
            List<Good> goods = goodRepository.findAllByItemIdIn(itemIds);
            List<Good> lookAndLook = goods.stream().filter(x -> !x.getGoodId().equals(goodId)).collect(Collectors.toList());
            //随机取3个，没有3个就全部显示
            if(lookAndLook.size() <= 3){
                goodIntroduction.setLookAndLook(goodVOS(lookAndLook));
            }else {
                Random random = new Random();
                List<Good> newGoods = new ArrayList<>();
                List<Integer> indexs = new ArrayList<>();
                while (true){
                    if(indexs.size() >= 3){
                        break;
                    }
                    Integer index = random.nextInt(lookAndLook.size());
                    if(indexs.contains(index)){
                        continue;
                    }
                    indexs.add(index);
                    newGoods.add(lookAndLook.get(index));
                }
                goodIntroduction.setLookAndLook(goodVOS(newGoods));
            }
        } catch (IOException e) {
            LOGGER.error("调用接口失败：{}", e.getMessage());
        }

        return goodIntroduction;
    }

    /**
     * 转化连接字符串为其他图片集合
     * @param pics
     * @return
     */
    private List<String> pics(String pics){
        String[] others = pics.split("connectionRegex");
        List<String> imgs = new ArrayList<>();
        Arrays.stream(others).forEach(x -> {
            String img = "<img src=\"" + x + "\" mid=\"" + x + "\" big=\"" + x + "\">";
            LOGGER.info("img：{}", img);
            imgs.add(img);
        });
        return imgs;
    }

    /**
     * 转换商品折扣集合为显示字符串
     * @param promotions
     * @param specification
     * @return
     */
    private String shopPromotions(List<ShopPromotions> promotions, String specification){

        if(promotions != null && promotions.size() > 0){
            LOGGER.info("promotions：{}", promotions.size());
            StringBuilder shopPromotions = new StringBuilder("购物");
            promotions.stream().forEach(x -> {
                if(promotions.size() > 0){
                    shopPromotions.append("满").append(x.getNumber()).append(specification).append("打").append(x.getDiscount()).append("折, ");
                }
            });
            String result = shopPromotions.toString();
            return result.substring(0, result.length() - 2) + "<span onclick=\"coupons()\">点击领券<i class=\"am-icon-sort-down\"></i></span>";
        }else {
            LOGGER.info("promotions：{}", promotions.size());
            return "暂无优惠<span onclick=\"coupons()\">点击领券<i class=\"am-icon-sort-down\"></i></span>";
        }
    }

    /**
     * 将优惠券集合转换为优惠券字符串集合
     * @param coupons
     * @return
     */
    private List<String> coupons(List<Coupon> coupons){
        if(coupons != null && coupons.size() > 0){
            LOGGER.info("查询到的优惠券数量为：{}", coupons.size());
            List<String> result = new ArrayList<>();
            coupons.stream().forEach(x -> {
                StringBuilder builder = new StringBuilder("");
                builder.append(x.getNeed()).append("减").append(x.getFree());
                result.add(builder.toString());
            });
            return result;
        }else {
            LOGGER.info("查询到的优惠券数量为：{}", coupons.size());
            List<String> result = new ArrayList<>();
            result.add("没有优惠券");
            return result;
        }
    }
    private List<GoodVO> goodVOS(List<Good> goods){
        List<GoodVO> goodVOList = new ArrayList<>();
        goods.stream().forEach(x -> {
            GoodVO goodVO = new GoodVO();
            goodVO.setGoodId(x.getGoodId());
            goodVO.setGoodName(x.getGoodName());
            goodVO.setPrice(x.getLastPrice());
            goodVO.setAccumulatedSales(x.getAccumulatedSales());
            if(x.getPisc() != null && x.getPisc().trim().length() > 0){
                goodVO.setFirstPic(x.getPisc().split("connectionRegex")[0]);
            }
            goodVOList.add(goodVO);
        });
        return goodVOList;
    }
    private List<String> picsPhone(String pics){
        String[] others = pics.split("connectionRegex");
        List<String> imgs = new ArrayList<>();
        Arrays.stream(others).forEach(x -> {
            String img = "<li><img src=\"" + x + "\" title=\"pic\" /></li>";
            LOGGER.info("img：{}", img);
            imgs.add(img);
        });
        return imgs;
    }
}

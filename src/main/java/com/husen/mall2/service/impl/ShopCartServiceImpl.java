package com.husen.mall2.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.husen.mall2.controller.ShopCartController;
import com.husen.mall2.enums.ItemStatu;
import com.husen.mall2.global.GlobalVar;
import com.husen.mall2.model.*;
import com.husen.mall2.repository.*;
import com.husen.mall2.service.ShopCartService;
import com.husen.mall2.vo.ItemMoney;
import com.husen.mall2.vo.ShopCart;
import com.husen.mall2.vo.StoresItems;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author husen
 */
@Service
@Transactional
public class ShopCartServiceImpl implements ShopCartService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ShopCartController.class);
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private ShopPromotionsRepository shopPromotionsRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoresRepository storesRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveItem(Integer userId, Integer goodId, Integer number) {
        try {
            //先判断同商品的条目是否已经存在
            Item old = itemRepository.findByUserIdAndGood_GoodIdAndStatu(userId, goodId, ItemStatu.SHOPCART.getValue());
            if(old != null && old.getOrder() == null){
                LOGGER.info("同种商品的条目存在");
                //先恢复优惠券
                Integer itemId = old.getItemId();
                rollBackCoupon(itemId, userRepository.findById(userId).get().getUsername());
                //生成新的Item信息
                Item newItem = creatItem(userId, goodId, number + old.getNumber());
                BeanUtils.copyProperties(newItem, old);
                old.setItemId(itemId);
                old.setStatu(ItemStatu.SHOPCART.getValue());
                itemRepository.save(old);
            }else if(old == null || old.getOrder() != null) {
                LOGGER.info("同种商品的条目不存在");
                Item item = creatItem(userId, goodId, number);
                item.setStatu(ItemStatu.SHOPCART.getValue());
                LOGGER.info("条目生成成功:{}", item.toString());
                itemRepository.save(item);
            }
        }catch (Exception e){
            LOGGER.error("条目生成失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteItem(Integer itemId, String username) {
        /**
         * 首先恢复优惠券,然后删除该条目
         * */
        rollBackCoupon(itemId, username);
        itemRepository.deleteById(itemId);
    }

    @Override
    public ShopCart shopCart(Integer userId) {
        List<StoresItems> lists = new ArrayList<>();
        List<Item> itemList = itemRepository.findAllByStatuAndUserId(ItemStatu.SHOPCART.getValue(), userId);
        List<Item> items = itemList == null ? new ArrayList<>() : itemList;
        Set<Integer> storesIds = new HashSet<>();
        items.stream().forEach(x -> storesIds.add(x.getGood().getStores().getStoresId()));
        LOGGER.info("店铺id有：{}", storesIds);
        storesIds.stream().forEach(x -> {
            List<Item> list = new ArrayList<>();
            items.stream().forEach(y -> {
                if(x.equals(y.getGood().getStores().getStoresId())){
                    String pics = y.getGood().getPisc();
                    if(StringUtils.isNotBlank(pics)){
                        y.getGood().setPisc(pics.split("connectionRegex")[0]);
                    }else {
                        y.getGood().setPisc("");
                    }
                    list.add(y);
                }
            });
            StoresItems storesItems = new StoresItems();
            storesItems.setItems(list);
            storesItems.setStoresName(storesRepository.findById(x).get().getName());
            lists.add(storesItems);
        });
        ShopCart shopCart = new ShopCart();
        shopCart.setLists(lists);
        shopCart.setUserId(userId);
        LOGGER.info("购物车信息为：{}", shopCart);
        return shopCart;
    }

    @Override
    public ItemMoney changeNumber(Integer userId, Integer goodId, String number) {
        try {
            //先判断同商品的条目是否已经存在
            Item old = itemRepository.findByUserIdAndGood_GoodIdAndStatu(userId, goodId, ItemStatu.SHOPCART.getValue());
            if(old !=null && old.getOrder() == null){
                LOGGER.info("同种商品的条目存在");
                //先恢复优惠券
                Integer itemId = old.getItemId();
                rollBackCoupon(itemId, userRepository.findById(userId).get().getUsername());
                //生成新的Item信息
                Item newItem;
                if(number.contains("a")){
                    newItem = creatItem(userId, goodId, Integer.valueOf(number.substring(0, number.length() - 1)));
                }else {
                    newItem = creatItem(userId, goodId, old.getNumber() + Integer.valueOf(number));
                }
                BeanUtils.copyProperties(newItem, old);
                old.setItemId(itemId);
                old.setStatu(ItemStatu.SHOPCART.getValue());
                itemRepository.save(old);
                ItemMoney itemMoney = new ItemMoney();
                itemMoney.setUnitPrice(old.getUnitPrice());
                itemMoney.setTotalMoney(old.getTotalMoney());
                itemMoney.setNumber(old.getNumber());
                return itemMoney;
            }else {
                return null;
            }
        }catch (Exception e){
            LOGGER.error("条目生成失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Item creatItem(Integer userId, Integer goodId, Integer number){
        if(number != 0){
            /**
             * 计算条目总价和单价
             * 1，先判断有无优惠券，和折扣
             * 2，有优惠券优先使用优惠券，否则使用折扣
             * 3，优惠券根据number来使用，如果number大于所有的条件，则从大到小来使用，使用完后应该删除
             * 4，判断是否需要邮费，需要的话加上邮费
             * 5，计算实际单价，等于计算后的总价除以数量
             * 6，用了优惠券以后，应该将该优惠券删除，并记录删除的优惠券
             * */
            String uuid = UUID.randomUUID().toString();
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            List<Coupon> coupons = couponRepository.findAllByGood_GoodId(goodId);
            LOGGER.info("查询到的优惠券是：{}", coupons);
            BigDecimal finalTotalMoney = new BigDecimal(0);
            BigDecimal totalMoney = new BigDecimal(0);
            Good good = goodRepository.findById(goodId).get();
            //没有折扣
            totalMoney = totalMoney.add(new BigDecimal(good.getLastPrice()).multiply(new BigDecimal(number))).setScale(2, BigDecimal.ROUND_HALF_UP);
            if(coupons == null || coupons.size() == 0){
                //没有优惠券，判断是否有折扣
                List<ShopPromotions> shopPromotions = shopPromotionsRepository.findAllByGood_GoodId(goodId);
                LOGGER.info("查询到的折扣是：{}", shopPromotions);
                if(shopPromotions == null || shopPromotions.size() == 0){
                    finalTotalMoney = new BigDecimal(totalMoney.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    LOGGER.info("总计（不计邮费）：{}", finalTotalMoney.doubleValue());
                }else {
                    //有折扣，过滤掉需要的数量大于number的折扣
                    shopPromotions = shopPromotions.stream().filter(x -> x.getNumber() <= number).collect(Collectors.toList());
                    LOGGER.info("过滤后的折扣是：{}", shopPromotions);
                    if(shopPromotions == null || shopPromotions.size() == 0){
                        finalTotalMoney = new BigDecimal(totalMoney.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        LOGGER.info("总计（不计邮费）：{}", finalTotalMoney.doubleValue());
                    }else if(shopPromotions != null && shopPromotions.size() > 0){
                        int shopPromotionsId = shopPromotions.get(0).getSpId();
                        int max = 0;
                        for(ShopPromotions promotions : shopPromotions){
                            shopPromotionsId = max < promotions.getNumber() ? promotions.getSpId() : shopPromotionsId;
                            max = max < promotions.getNumber() ? promotions.getNumber() : max;
                        }
                        //得到折扣最大的id
                        ShopPromotions promotions = shopPromotionsRepository.findById(shopPromotionsId).get();
                        finalTotalMoney = totalMoney.multiply(new BigDecimal(promotions.getDiscount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                        LOGGER.info("总计（不计邮费，打折后）：{}", finalTotalMoney.doubleValue());
                    }
                }
            }else {
                //判断用户是否领券
                String value = operations.get(userRepository.findById(userId).get().getUsername() + "coupons");
                LOGGER.info("查询到的用户优惠券情况为：{}", value);
                if(StringUtils.isBlank(value)){
                    //用户没有领取优惠券
                    if(coupons == null || coupons.size() == 0){
                        //没有优惠券，判断是否有折扣
                        List<ShopPromotions> shopPromotions = shopPromotionsRepository.findAllByGood_GoodId(goodId);
                        LOGGER.info("查询到的折扣是：{}", shopPromotions);
                        if(shopPromotions == null || shopPromotions.size() == 0){
                            finalTotalMoney = new BigDecimal(totalMoney.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            LOGGER.info("总计（不计邮费）：{}", finalTotalMoney.doubleValue());
                        }else {
                            //有折扣，过滤掉需要的数量大于number的折扣
                            shopPromotions = shopPromotions.stream().filter(x -> x.getNumber() <= number).collect(Collectors.toList());
                            LOGGER.info("过滤后的折扣是：{}", shopPromotions);
                            if(shopPromotions == null || shopPromotions.size() == 0){
                                finalTotalMoney = new BigDecimal(totalMoney.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                                LOGGER.info("总计（不计邮费）：{}", finalTotalMoney.doubleValue());
                            }else if(shopPromotions != null && shopPromotions.size() > 0){
                                int shopPromotionsId = shopPromotions.get(0).getSpId();
                                int max = 0;
                                for(ShopPromotions promotions : shopPromotions){
                                    shopPromotionsId = max < promotions.getNumber() ? promotions.getSpId() : shopPromotionsId;
                                    max = max < promotions.getNumber() ? promotions.getNumber() : max;
                                }
                                //得到折扣最大的id
                                ShopPromotions promotions = shopPromotionsRepository.findById(shopPromotionsId).get();
                                finalTotalMoney = totalMoney.multiply(new BigDecimal(promotions.getDiscount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                LOGGER.info("总计（不计邮费，打折后）：{}", finalTotalMoney.doubleValue());
                            }
                        }
                    }
                }else {
                    //用户领取了优惠券
                    List<Integer> values = JSONArray.parseArray(value, Integer.class);
                    LOGGER.info("优惠券id集合为：{}", values);
                    List<Coupon> couponList = couponRepository.findAllByIdIn(values);
                    LOGGER.info("查询到的优惠券是：{}", couponList);
                    //过滤掉不满足条件的优惠券
                    BigDecimal compareMoney = new BigDecimal(totalMoney.doubleValue());
                    List<Coupon> filteredCoupons = couponList.stream().filter(x -> compareMoney.compareTo(new BigDecimal(x.getNeed())) > 0).collect(Collectors.toList());
                    LOGGER.info("过滤后的优惠券为：{}", filteredCoupons);
                    if(filteredCoupons != null && filteredCoupons.size() > 0){
                        //说明有满足的优惠券可以用
                        int couponId = filteredCoupons.get(0).getId();
                        BigDecimal max = new BigDecimal(0);
                        for(Coupon coupon : filteredCoupons){
                            couponId = max.compareTo(new BigDecimal(coupon.getNeed())) < 0 ? coupon.getId() : couponId;
                            max = max.compareTo(new BigDecimal(coupon.getNeed())) < 0 ? new BigDecimal(coupon.getNeed()) : max;
                        }
                        LOGGER.info("couponId:{}", couponId);
                        LOGGER.info("max:{}", max.doubleValue());
                        Coupon coupon = couponRepository.findById(couponId).get();
                        LOGGER.info("free:{}", coupon.getFree());
                        finalTotalMoney = totalMoney.subtract(new BigDecimal(coupon.getFree().doubleValue())).setScale(2, BigDecimal.ROUND_HALF_UP);
                        LOGGER.info("总计（不计邮费，使用优惠券后）：{}", finalTotalMoney.doubleValue());
                        //删除已使用的优惠券，并记录，删除条目退还优惠券的时候使用
                        int filterId = couponId;
                        values = values.stream().filter(x -> x != filterId).collect(Collectors.toList());
                        String username = userRepository.findById(userId).get().getUsername();
                        operations.set(username + "coupons", JSONArray.toJSONString(values));
                        operations.set(username + uuid, String.valueOf(couponId));
                    }else {
                        //有优惠券，但用户没有达到要求，判断折扣是否可用
                        //没有优惠券，判断是否有折扣
                        List<ShopPromotions> shopPromotions = shopPromotionsRepository.findAllByGood_GoodId(goodId);
                        LOGGER.info("查询到的折扣是：{}", shopPromotions);
                        if(shopPromotions == null || shopPromotions.size() == 0){
                            finalTotalMoney = new BigDecimal(totalMoney.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            LOGGER.info("总计（不计邮费）：{}", finalTotalMoney.doubleValue());
                        }else {
                            //有折扣，过滤掉需要的数量大于number的折扣
                            shopPromotions = shopPromotions.stream().filter(x -> x.getNumber() <= number).collect(Collectors.toList());
                            LOGGER.info("过滤后的折扣是：{}", shopPromotions);
                            if(shopPromotions == null || shopPromotions.size() == 0){
                                finalTotalMoney = new BigDecimal(totalMoney.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                                LOGGER.info("总计（不计邮费）：{}", finalTotalMoney.doubleValue());
                            }else if(shopPromotions != null && shopPromotions.size() > 0){
                                int shopPromotionsId = shopPromotions.get(0).getSpId();
                                int max = 0;
                                for(ShopPromotions promotions : shopPromotions){
                                    shopPromotionsId = max < promotions.getNumber() ? promotions.getSpId() : shopPromotionsId;
                                    max = max < promotions.getNumber() ? promotions.getNumber() : max;
                                }
                                //得到折扣最大的id
                                ShopPromotions promotions = shopPromotionsRepository.findById(shopPromotionsId).get();
                                finalTotalMoney = totalMoney.multiply(new BigDecimal(promotions.getDiscount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                LOGGER.info("总计（不计邮费，打折后）：{}", finalTotalMoney.doubleValue());
                            }
                        }
                    }
                }
            }
            Item item = new Item();
            item.setUuid(uuid);
            item.setUserId(userId);
            item.setGood(good);
            item.setNumber(number);
            item.setTotalMoney(finalTotalMoney.doubleValue());
            //计算实际单价
            finalTotalMoney = finalTotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal unitPrice = finalTotalMoney.divide(new BigDecimal(number), 2, BigDecimal.ROUND_HALF_UP);
            LOGGER.info("实际单价为：{}", unitPrice.doubleValue());
            item.setUnitPrice(unitPrice.doubleValue());
            item.setIsFreight(good.getIsPostage());
            item.setFreight(good.getPostageMoney());
            return item;
        }else {
            Item item = new Item();
            item.setUserId(userId);
            Good good = goodRepository.findById(goodId).get();
            item.setGood(good);
            item.setNumber(number);
            item.setTotalMoney(0.00);
            item.setUnitPrice(0.00);
            item.setIsFreight(good.getIsPostage());
            item.setFreight(good.getPostageMoney());
            return item;
        }
    }

    /**
     * 恢复使用的优惠券
     * @param itemId
     * @param username
     */
    private void rollBackCoupon(Integer itemId, String username){
        Item item = itemRepository.findById(itemId).get();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value = operations.get(username + "coupons");
        if(StringUtils.isNotBlank(value)){
            List<Integer> values = JSONArray.parseArray(value, Integer.class);
            String couponId = operations.get(username + item.getUuid());
            if(StringUtils.isNotBlank(couponId)){
                values.add(Integer.valueOf(couponId));
                operations.getOperations().delete(username + item.getUuid());
                operations.set(username + "coupons", JSONArray.toJSONString(values));
            }
            LOGGER.info("恢复优惠券成功");
        }else {
            //用户没有优惠券
            LOGGER.info("用户没有领取优惠券，所以也没有使用优惠券");
        }
    }

    @Override
    public Integer saveItem2(Integer userId, Integer goodId, Integer number) {
        Item item = creatItem(userId, goodId, number);
        return itemRepository.save(item).getItemId();
    }

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(100);
        b1 = b1.setScale(2);
        System.out.println(b1);
    }
}

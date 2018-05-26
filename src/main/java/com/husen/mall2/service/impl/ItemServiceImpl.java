package com.husen.mall2.service.impl;
import com.husen.mall2.global.GlobalVar;
import com.husen.mall2.model.Coupon;
import com.husen.mall2.model.Item;
import com.husen.mall2.repository.CouponRepository;
import com.husen.mall2.repository.ItemRepository;
import com.husen.mall2.repository.StoresRepository;
import com.husen.mall2.repository.UserRepository;
import com.husen.mall2.service.ItemService;
import com.husen.mall2.vo.ShopCart;
import com.husen.mall2.vo.StoresItems;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author husen
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StoresRepository storesRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ShopCart shopCart(Integer userId, List<Integer> itemIdList) {
        String username = userRepository.findById(userId).get().getUsername();
        List<StoresItems> lists = new ArrayList<>();
        List<Item> items = itemRepository.findAllByItemIdIn(itemIdList);
        Set<Integer> storesIds = new HashSet<>();
        items.stream().forEach(x -> storesIds.add(x.getGood().getStores().getStoresId()));
        LOGGER.info("店铺id有：{}", storesIds);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        List<BigDecimal> bigDecimals = new ArrayList<>();
        storesIds.stream().forEach(x -> {
            List<Item> list = new ArrayList<>();
            List<String> coupons = new ArrayList<>();
            items.stream().forEach(y -> {
                if(y.getIsFreight().equals(GlobalVar.NO_POSTAGE)){
                    BigDecimal money = new BigDecimal(y.getTotalMoney());
                    bigDecimals.add(money);
                }else {
                    BigDecimal money = new BigDecimal(y.getTotalMoney()).add(new BigDecimal(y.getFreight()));
                    bigDecimals.add(money);
                }
                LOGGER.info("uuid:{}", y.getUuid());
                String couponId = operations.get(username + y.getUuid());
                if(StringUtils.isNotBlank(couponId)){
                    Coupon coupon = couponRepository.findById(Integer.valueOf(couponId)).get();
                    coupons.add("￥" + coupon.getFree() + " 【消费满" + coupon.getNeed() + "元可用】");
                }
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
            LOGGER.info("coupons:{}", coupons);
            StoresItems storesItems = new StoresItems();
            storesItems.setItems(list);
            storesItems.setStoresName(storesRepository.findById(x).get().getName());
            storesItems.setCoupons(coupons);
            storesItems.setStoresId(x);
            lists.add(storesItems);
        });
        BigDecimal total = new BigDecimal(0);
        for(BigDecimal b : bigDecimals){
            total = total.add(b);
        }
        total.setScale(2, BigDecimal.ROUND_HALF_UP);
        ShopCart shopCart = new ShopCart();
        shopCart.setLists(lists);
        shopCart.setUserId(userId);
        shopCart.setTotalMoney(total.doubleValue());
        LOGGER.info("确认订单信息为：{}", shopCart);
        return shopCart;
    }
}

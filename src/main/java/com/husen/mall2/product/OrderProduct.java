package com.husen.mall2.product;

import com.husen.mall2.enums.OrderStatu;
import com.husen.mall2.global.GlobalVar;
import com.husen.mall2.model.Item;
import com.husen.mall2.model.Order;
import com.husen.mall2.repository.AddressRepository;
import com.husen.mall2.repository.ItemRepository;
import com.husen.mall2.repository.UserRepository;
import com.husen.mall2.vo.CreateOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单生成器
 * @author husen
 */
@Component
public class OrderProduct {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProduct.class);
    @Autowired
    private OrderIdProduct orderIdProduct;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    /**
     * 生成订单
     * @param createOrder
     * @param itemIdList
     * @return
     */
    public Order createOrder(CreateOrder createOrder, List<Integer> itemIdList, String userNote){
        Order order = new Order();
        String id = orderIdProduct.productOrderId();
        LOGGER.info("生成的订单号为:{}", id);
        order.setId(id);
        order.setPayTime(new Date());
        LOGGER.info("交易时间为:{}", order.getPayTime());
        List<Item> items = itemRepository.findAllByItemIdIn(itemIdList);
        LOGGER.info("该订单的条目是:{}", items);
        order.setItems(items);
        List<Double> postages = new ArrayList<>();
        items.stream().forEach(x -> {
            if(GlobalVar.HAVE_POSTAGE.equals(x.getIsFreight())){
                //不包邮
                postages.add(x.getFreight());
            }
        });
        if(postages.size() > 0){
            //说明有邮费
            order.setIsFreight(GlobalVar.HAVE_POSTAGE);
            //计算邮费有多少
            BigDecimal totalPostAge = new BigDecimal(0);
            for (Double postAge : postages){
                totalPostAge = totalPostAge.add(new BigDecimal(postAge));
            }
            totalPostAge.setScale(2);
            order.setFreight(totalPostAge.doubleValue());
            LOGGER.info("有邮费:{}", order.getFreight());
        }else {
            //没有邮费
            order.setIsFreight(GlobalVar.NO_POSTAGE);
            order.setFreight(0.00);
            LOGGER.info("没有邮费:{}", order.getFreight());
        }
        //计算总价 = 每个条目的总价和 + 总的邮费
        BigDecimal totalMoney = new BigDecimal(0);
        for (Item item : items){
            totalMoney = totalMoney.add(new BigDecimal(item.getTotalMoney()));
        }
        totalMoney = totalMoney.add(new BigDecimal(order.getFreight())).setScale(2, BigDecimal.ROUND_HALF_UP);
        LOGGER.info("订单的总价为:{}", totalMoney.doubleValue());
        order.setTotalMoney(totalMoney.doubleValue());
        //0表示未付款，1表示已付款
        if(createOrder.getStatu().equals(0)){
            order.setStatu(OrderStatu.NO_PAY.getValue());
            LOGGER.info("订单的状态为:{}", OrderStatu.NO_PAY.getDescription());
        }else {
            order.setStatu(OrderStatu.PAYED_BUT_NO_DELIVER.getValue());
            LOGGER.info("订单的状态为:{}", OrderStatu.PAYED_BUT_NO_DELIVER.getDescription());
        }
        order.setUser(userRepository.findById(createOrder.getUserId()).get());
        LOGGER.info("该订单的用户:{}", order.getUser());
        order.setUid(createOrder.getUserId());
        if(items != null && items.size() > 0){
            order.setStores(items.get(0).getGood().getStores());
            LOGGER.info("该订单所属的店铺是:{}", order.getStores());
            order.setStoreId(order.getStores().getStoresId());
        }
        LOGGER.info("订单的删除标识是:{}", GlobalVar.ORDER_NODELETE);
        order.setDel(GlobalVar.ORDER_NODELETE);
        order.setAddress(addressRepository.findById(createOrder.getAddressId()).get());
        LOGGER.info("发货地址为:{}", order.getAddress());
        //设置买家留言
        order.setUserNote(userNote);
        LOGGER.info("买家留言是:{}", userNote);
        return order;
    }
}

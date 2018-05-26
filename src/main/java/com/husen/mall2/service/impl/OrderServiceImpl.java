package com.husen.mall2.service.impl;

import com.alibaba.fastjson.JSON;
import com.husen.mall2.amqp.Sender;
import com.husen.mall2.config.AmqpConfig;
import com.husen.mall2.enums.ItemStatu;
import com.husen.mall2.enums.OrderStatu;
import com.husen.mall2.global.GlobalVar;
import com.husen.mall2.model.*;
import com.husen.mall2.product.ItemIdListAndUserNote;
import com.husen.mall2.product.ItemIdListProduct;
import com.husen.mall2.product.OrderProduct;
import com.husen.mall2.repository.*;
import com.husen.mall2.service.OrderService;
import com.husen.mall2.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author husen
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private Sender sender;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private LogisticsRecordRepository logisticsRecordRepository;
    @Autowired
    private ItemIdListProduct itemIdListProduct;
    @Autowired
    private OrderProduct orderProduct;

    @Override
    public JsonResult cancelOrder(Integer orderId) {
        //将订单状态设为取消
        Order order = orderRepository.findById(orderId).get();
        //只有未付款的订单才能取消
        if(OrderStatu.NO_PAY.getValue().equals(order.getStatu())){
            order.setStatu(OrderStatu.CANCELED.getValue());
            return new JsonResult(200, "success");
        }else {
            return new JsonResult(400, "forbidden");
        }
    }

    @Override
    public JsonResult deleteOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).get();
        if(order.getStatu().equals(OrderStatu.CANCELED.getValue())){
            order.setDel(GlobalVar.ORDER_ISDELETE);
            orderRepository.save(order);
            return new JsonResult(200, "success");
        }else {
            LOGGER.info("订单状态不是预期的状态，删除失败");
            return new JsonResult(400, "forbidden");
        }
    }

    @Override
    public void create(CreateOrder createOrder) {
        List<ItemIdListAndUserNote> lists = itemIdListProduct.itemIdStrToListAndUserNoteByStoresId(createOrder.getItemIdStr(), createOrder.getUserNote());
        LOGGER.info("itemIdLists:{}", lists);
        lists.stream().forEach(x -> {
            Order order = orderProduct.createOrder(createOrder, x.getItemIdList(), x.getUserNote());
            order = orderRepository.save(order);
            //记录账单明细
            if(order.getStatu().equals(OrderStatu.PAYED_BUT_NO_DELIVER.getValue())){
                Date payTime =  order.getPayTime();
                Order orderBill = order;
                User user = order.getUser();
                order.getItems().stream().forEach(item -> {
                    BillDetail billDetail = new BillDetail();
                    billDetail.setBankName(createOrder.getBankName());
                    billDetail.setDealTime(payTime);
                    billDetail.setOrder(orderBill);
                    billDetail.setUser(user);
                    billDetail.setGoodName(item.getGood().getGoodName());
                    Double totalMoney = item.getTotalMoney();
                    Double freight = item.getFreight();
                    billDetail.setExpenditure(freight == null ? new BigDecimal(totalMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() : new BigDecimal(totalMoney).add(new BigDecimal(freight)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    String pisc = item.getGood().getPisc();
                    String pic = StringUtils.isNotBlank(pisc) ? pisc.split("connectionRegex")[0] : "";
                    billDetail.setPic(pic);
                    billDetailRepository.save(billDetail);
                    LOGGER.info("生成了账单明细:{}", billDetail);
                });
            }
            LOGGER.info("生成的订单为:{}", order);
            for (Item item : order.getItems()){
                item.setOrder(order);
                item.setStatu(ItemStatu.ORDER.getValue());
                item = itemRepository.save(item);
                LOGGER.info("订单的条目为:{}", item);
            }
        });
    }

    @Override
    public List<OrderVO> findAllByStatu(Integer statu, Integer userId) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "payTime"));
        List<OrderVO> orderVOS = new ArrayList<>();
        if(statu == null){
            List<Order> orders = orderRepository.findAllByDelAndUser_UserId(GlobalVar.ORDER_NODELETE, userId, sort);
            orders.stream().forEach(x -> {
                OrderVO orderVO = new OrderVO(x.getOrderId(), x.getId(), x.getPayTime(), x.getStatu(), x.getIsFreight(), x.getFreight(), x.getTotalMoney());
                List<ItemVO> itemVOS = new ArrayList<>();
                List<Item> items = itemRepository.findAllByOrder_OrderIdAndStatu(x.getOrderId(), ItemStatu.ORDER.getValue());
                if(items == null || items.size() > 0){
                    items.stream().forEach(y -> {
                        String pisc = y.getGood().getPisc();
                        String pic = StringUtils.isNotBlank(pisc) ? pisc.split("connectionRegex")[0] : "";
                        ItemVO itemVO = new ItemVO(y.getItemId(), pic, y.getGood().getGoodName(), y.getUnitPrice(), y.getNumber() );
                        itemVOS.add(itemVO);
                        orderVO.setItems(itemVOS);
                        orderVO.setStoresName(x.getStores().getName());
                        orderVOS.add(orderVO);
                    });
                }
            });
            return orderVOS;
        }else {
            List<Order> orders = orderRepository.findAllByStatuAndDelAndUser_UserId(statu, GlobalVar.ORDER_NODELETE, userId, sort);
            orders.stream().forEach(x -> {
                OrderVO orderVO = new OrderVO(x.getOrderId(), x.getId(), x.getPayTime(), x.getStatu(), x.getIsFreight(), x.getFreight(), x.getTotalMoney());
                List<ItemVO> itemVOS = new ArrayList<>();
                List<Item> items = itemRepository.findAllByOrder_OrderIdAndStatu(x.getOrderId(), ItemStatu.ORDER.getValue());
                if(items == null || items.size() > 0){
                    items.stream().forEach(y -> {
                        String pisc = y.getGood().getPisc();
                        String pic = StringUtils.isNotBlank(pisc) ? pisc.split("connectionRegex")[0] : "";
                        ItemVO itemVO = new ItemVO(y.getItemId(), pic, y.getGood().getGoodName(), y.getUnitPrice(), y.getNumber() );
                        itemVOS.add(itemVO);
                        orderVO.setItems(itemVOS);
                        orderVO.setStoresName(x.getStores().getName());
                        orderVOS.add(orderVO);
                    });
                }
            });
            return orderVOS;
        }
    }

    @Override
    public void createOrder(CreateOrder order) {
        LOGGER.info("发送的创建订单数据为:{}", order);
        if(order != null){
            try {
                String jsonData = JSON.toJSONString(order);
                sender.send(jsonData, AmqpConfig.TOPIC_EXCHANGE_NAME, AmqpConfig.CREATE_ORDER_QUEUE, AmqpConfig.CREATE_ORDER_QUEUE);
            }catch (Exception e){
                LOGGER.error("发生数据发生错误:{}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public OrderInfo findOrderInfoByOrderId(Integer orderIdInteger) {
        Order order = orderRepository.findById(orderIdInteger).get();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(order.getOrderId());
        orderInfo.setId(order.getId());
        orderInfo.setIfPostage(order.getIsFreight());
        orderInfo.setPostage(order.getFreight());
        orderInfo.setPayTime(order.getPayTime());
        orderInfo.setStatu(order.getStatu());
        orderInfo.setTotalMoney(order.getTotalMoney());
        orderInfo.setAddress(order.getAddress());
        List<ItemVO> itemVOS = new ArrayList<>();
        List<Item> items = itemRepository.findAllByOrder_OrderIdAndStatu(order.getOrderId(), ItemStatu.ORDER.getValue());
        items.stream().forEach(x -> {
            String pisc = x.getGood().getPisc();
            String pic = StringUtils.isNotBlank(pisc) ? pisc.split("connectionRegex")[0] : "";
            ItemVO itemVO = new ItemVO(x.getItemId(), pic, x.getGood().getGoodName(), x.getUnitPrice(), x.getNumber());
            itemVOS.add(itemVO);
        });
        orderInfo.setStoresName(order.getStores().getName());
        orderInfo.setItems(itemVOS);

        Logistics logistics = order.getLogistics();
        if(logistics == null){
            orderInfo.setLogisticsVO(null);
        }else {
            List<String> list = new ArrayList<>();
            Sort.Order sortOrder = new Sort.Order(Sort.Direction.DESC, "time");
            Sort sort = new Sort(sortOrder);
            Pageable pageable = new PageRequest(0, 3, sort);
            List<LogisticsRecord> records = logisticsRecordRepository.findAllByLogistics_Id(logistics.getId(), pageable);
            records.stream().forEach(x -> {
                list.add(x.getContent() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getTime()));
            });
            LogisticsVO logisticsVO = new LogisticsVO(logistics.getId(), logistics.getCompany().getName(), logistics.getExpressNumber(), list, logistics.getCompany().getPhone(), logistics.getStatu());
            orderInfo.setLogisticsVO(logisticsVO);
        }
        return orderInfo;
    }

    @Override
    public JsonResult deliveredOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).get();
        if(OrderStatu.PAYED_AND_DELIVERED.getValue().equals(order.getStatu())){
            order.setStatu(OrderStatu.RECEIVED.getValue());
            orderRepository.save(order);
            return new JsonResult(200, "确认成功");
        }else {
            return new JsonResult(400, "错误的订单状态");
        }
    }

    @Override
    public List<OrderVO> getTwoMyOrder(Integer userId) {
        List<OrderVO> list = findAllByStatu(null, userId);
        List<OrderVO> realList = new ArrayList<>();
        for(OrderVO x : list){
            if(realList.size() >= 4){
                break;
            }
            if(x.getItems().size() >= 1){
                realList.add(x);
                x.setDefaultPic(x.getItems().get(0).getPic());
            }
            Integer total = 0;
            for(ItemVO vo : x.getItems()){
                total += vo.getNumber();
            }
            x.setNumber(total);
        }
        return realList;
    }
}

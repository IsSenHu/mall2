package com.husen.mall2.service.impl;

import com.alibaba.fastjson.JSON;
import com.husen.mall2.amqp.Sender;
import com.husen.mall2.config.AmqpConfig;
import com.husen.mall2.enums.ItemStatu;
import com.husen.mall2.enums.OrderStatu;
import com.husen.mall2.enums.RefundStatu;
import com.husen.mall2.enums.RefundType;
import com.husen.mall2.global.GlobalVar;
import com.husen.mall2.model.*;
import com.husen.mall2.repository.BillDetailRepository;
import com.husen.mall2.repository.ItemRepository;
import com.husen.mall2.repository.OrderRepository;
import com.husen.mall2.repository.RefundRepository;
import com.husen.mall2.service.RefundService;
import com.husen.mall2.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author husen
 */
@Service
@Transactional
public class RefundServiceImpl implements RefundService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RefundServiceImpl.class);
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private Sender sender;
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public List<OrderVO> findAllByStatu(Integer statu) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "refundId");
        Sort sort = new Sort(order);
        List<OrderVO> vos = new ArrayList<>();
        List<Refund> refunds;
        if(statu == null){
            refunds = refundRepository.findAll(sort);
        }else {
            refunds = refundRepository.findAllByStatu(statu, sort);
        }
        for(Refund refund : refunds){
            List<ItemVO> itemVOS = new ArrayList<>();
            Order userOrder = orderRepository.findById(refund.getOrderId()).get();
            Item item = itemRepository.findById(refund.getItemId()).get();
            BigDecimal totalMoney = new BigDecimal(item.getTotalMoney()).add(new BigDecimal(item.getFreight())).setScale(2);
            OrderVO orderVO = new OrderVO(userOrder.getOrderId(), userOrder.getId(), userOrder.getPayTime(), userOrder.getStatu(), item.getIsFreight(), item.getFreight(), totalMoney.doubleValue());
            String pisc = item.getGood().getPisc();
            String pic = StringUtils.isNotBlank(pisc) ? pisc.split("connectionRegex")[0] : "";
            ItemVO itemVO = new ItemVO(item.getItemId(), pic, item.getGood().getGoodName(), item.getUnitPrice(), item.getNumber() );
            itemVOS.add(itemVO);
            orderVO.setItems(itemVOS);
            orderVO.setStoresName(userOrder.getStores().getName());
            orderVO.setRefundStatu(refund.getStatu());
            vos.add(orderVO);
        }
        return vos;
    }

    @Override
    public JsonResult cancelRefund(Integer refundId) {
        /**
         * 首先要判断退款单的状态，只有为第二种状态才能取消
         * */
        Refund refund = refundRepository.findById(refundId).get();
        if(RefundStatu.WAIT.getValue().equals(refund.getStatu())){
            /**
             * 恢复条目到订单中去
             * */
            Order order = orderRepository.findById(refund.getOrderId()).get();
            Item item = itemRepository.findById(refund.getItemId()).get();
            if(OrderStatu.PAYED_BUT_NO_DELIVER.getValue().equals(order.getStatu())){
                //这个时候是可以恢复原订单的，将order保存进Item中，并且重新计算价格
                item.setOrder(order);
                item.setStatu(ItemStatu.ORDER.getValue());
                itemRepository.save(item);
                /**
                 * 重新计算订单的价格
                 * */
                BigDecimal totalMoney = new BigDecimal(0.00);
                BigDecimal postage = new BigDecimal(0.00);
                for(Item x : itemRepository.findAllByOrder_OrderIdAndStatu(order.getOrderId(), ItemStatu.ORDER.getValue())){
                    LOGGER.info("item:{}", item);
                    totalMoney = totalMoney.add(new BigDecimal(x.getTotalMoney())).add(new BigDecimal(x.getFreight()));
                    postage = postage.add(new BigDecimal(x.getFreight()));
                }
                order.setTotalMoney(totalMoney.add(postage).setScale(2).doubleValue());
                order.setFreight(postage.setScale(2).doubleValue());
                orderRepository.save(order);
            }else {
                //否则的话新建一个订单，使用之前已经写了的方法
                CreateOrder createOrder = new CreateOrder();
                createOrder.setUserId(refund.getUserId());
                createOrder.setUserNote(order.getUserNote());
                createOrder.setStatu(OrderStatu.PAYED_BUT_NO_DELIVER.getValue());
                createOrder.setItemIdStr(item.getItemId() + ",");
                createOrder.setAddressId(order.getAddress().getAddressId());
                List<BillDetail> details = billDetailRepository.findAllByOrder_OrderId(order.getOrderId());
                createOrder.setBankName(details != null && details.size() > 0  ? details.get(0).getBankName() : "");
                createOrder(createOrder);
            }
            refundRepository.deleteById(refundId);
            return new JsonResult(200, "success");
        }else {
            LOGGER.info("该退款单已不能取消");
            return new JsonResult(400, "forbidden");
        }
    }

    private void createOrder(CreateOrder order){
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
    public RefundVO createRefund(Integer orderId, Integer itemId, Integer userId) {
        Item item = itemRepository.findById(itemId).get();
        Order order = orderRepository.findById(orderId).get();
        Good good = item.getGood();
        RefundVO vo = new RefundVO();
        vo.setDealTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getPayTime()));
        vo.setGoodName(good.getGoodName());
        vo.setId(order.getId());
        vo.setIsPostage(item.getIsFreight());
        vo.setItemId(itemId);
        vo.setMoney(new BigDecimal(item.getUnitPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        vo.setNumber(item.getNumber());
        vo.setOrderId(orderId);
        String pisc = good.getPisc();
        String pic = StringUtils.isNotBlank(pisc) ? pisc.split("connectionRegex")[0] : "";
        vo.setPic(pic);
        vo.setSmallAmount(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
        if(OrderStatu.PAYED_BUT_NO_DELIVER.getValue().equals(order.getStatu()) || OrderStatu.SURED.getValue().equals(order.getStatu())){
            //已付款未发货，退的金额包括邮费，（如果有邮费的话）
            if(GlobalVar.HAVE_POSTAGE.equals(item.getIsFreight())){
                vo.setPostage(new BigDecimal(item.getFreight()).setScale(2).toString());
                vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).add(new BigDecimal(item.getFreight())).setScale(2).toString());
            }else {
                //没有邮费
                vo.setPostage("0.00");
                vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
            }
            vo.setRefundMoney(vo.getTotalMoney());
        }else if(OrderStatu.PAYED_AND_DELIVERED.getValue().equals(order.getStatu())
                || OrderStatu.RECEIVED.getValue().equals(order.getStatu())
                || OrderStatu.REVIEWED.getValue().equals(order.getStatu())) {
            //已发货的就不包括邮费了，涉及到一个问题，什么时候能退款。只要商家觉得可以，什么时候都能退款
            if(GlobalVar.HAVE_POSTAGE.equals(item.getIsFreight())){
                vo.setPostage(new BigDecimal(item.getFreight()).setScale(2).toString());
                vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).add(new BigDecimal(item.getFreight())).setScale(2).toString());
            }else {
                //没有邮费
                vo.setPostage("0.00");
                vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
            }
            vo.setRefundMoney(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
        }else if(OrderStatu.REFUND_ALL.getValue().equals(order.getStatu())){
            //如果订单为第7状态，则根据数据库已保存的来判断就可以了
            Refund refund = refundRepository.findByItemId(itemId);
            if(refund.getRefundType().equals(RefundType.NO_POSTAGE.getValue())){
                //不包括邮费
                if(GlobalVar.HAVE_POSTAGE.equals(item.getIsFreight())){
                    vo.setPostage(new BigDecimal(item.getFreight()).setScale(2).toString());
                    vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).add(new BigDecimal(item.getFreight())).setScale(2).toString());
                }else {
                    //没有邮费
                    vo.setPostage("0.00");
                    vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
                }
                vo.setRefundMoney(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
            }else {
                //包括邮费
                //已付款未发货，退的金额包括邮费，（如果有邮费的话）
                if(GlobalVar.HAVE_POSTAGE.equals(item.getIsFreight())){
                    vo.setPostage(new BigDecimal(item.getFreight()).setScale(2).toString());
                    vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).add(new BigDecimal(item.getFreight())).setScale(2).toString());
                }else {
                    //没有邮费
                    vo.setPostage("0.00");
                    vo.setTotalMoney(new BigDecimal(item.getTotalMoney()).setScale(2).toString());
                }
                vo.setRefundMoney(vo.getTotalMoney());
            }
        }
        Refund refund = refundRepository.findByItemId(itemId);
        vo.setRefundStatu(refund == null ? RefundStatu.START.getValue() : refund.getStatu());
        vo.setDescription(refund == null ? "" : refund.getDescription());
        vo.setRefundType(refund == null ? 0 : refund.getRefundType());
        vo.setRefundReason(refund == null ? 0 : refund.getRefundReason());
        vo.setRefundId(refund == null ? 0 : refund.getRefundId());
        return vo;
    }

    @Override
    public JsonResult<Refund> saveRefund(Integer userId, Refund refund) {
        if(refundRepository.countByItemId(refund.getItemId()) > 0){
            LOGGER.info("不能重复保存已存在的条目的退款单");
            return new JsonResult<>(401, "forbidden");
        }
        Order order = orderRepository.findById(refund.getOrderId()).get();
        if(order.getStatu().equals(OrderStatu.NO_PAY.getValue()) || order.getStatu().equals(OrderStatu.CANCELED.getValue()) || order.getStatu().equals(OrderStatu.REFUND_ALL.getValue())){
            LOGGER.info("不是期望的订单状态");
            return new JsonResult<>(401, "forbidden");
        }
        refund.setStatu(RefundStatu.WAIT.getValue());
        refund.setUserId(userId);
        Item item = itemRepository.findById(refund.getItemId()).get();
        refund.setStoresId(item.getGood().getStores().getStoresId());
        item.setStatu(ItemStatu.REFUND.getValue());
        itemRepository.save(item);
        /**
         * 重新计算订单的价格
         * */
        BigDecimal totalMoney = new BigDecimal(0.00);
        BigDecimal postage = new BigDecimal(0.00);
        for(Item x : itemRepository.findAllByOrder_OrderIdAndStatu(order.getOrderId(), ItemStatu.ORDER.getValue())){
            LOGGER.info("item:{}", item);
            totalMoney = totalMoney.add(new BigDecimal(x.getTotalMoney())).add(new BigDecimal(x.getFreight()));
            postage = postage.add(new BigDecimal(x.getFreight()));
        }
        order.setTotalMoney(totalMoney.setScale(2).doubleValue());
        order.setFreight(postage.setScale(2).doubleValue());
        orderRepository.save(order);
        Refund newRefund = refundRepository.save(refund);
        return new JsonResult<>(200, "success", newRefund);
    }
}

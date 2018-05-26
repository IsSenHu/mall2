package com.husen.mall2.service;

import com.husen.mall2.vo.CreateOrder;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.OrderInfo;
import com.husen.mall2.vo.OrderVO;

import java.util.List;

/**
 * @author husen
 */
public interface OrderService {
    void createOrder(CreateOrder order);

     void create(CreateOrder createOrder);

     List<OrderVO> findAllByStatu(Integer statu, Integer userId);

    OrderInfo findOrderInfoByOrderId(Integer orderIdInteger);

    JsonResult cancelOrder(Integer orderId);

    JsonResult deleteOrder(Integer orderId);

    List<OrderVO> getTwoMyOrder(Integer userId);

    JsonResult deliveredOrder(Integer orderId);
}

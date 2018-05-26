package com.husen.mall2.controller;

import com.husen.mall2.enums.OrderStatu;
import com.husen.mall2.model.User;
import com.husen.mall2.service.OrderService;
import com.husen.mall2.service.UserService;
import com.husen.mall2.vo.CreateOrder;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.OrderInfo;
import com.husen.mall2.vo.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author husen
 */
@Controller
public class OrderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    /**
     * 根据订单状态查询订单
     * @param request
     * @return
     */
    @RequestMapping("/order")
    private ModelAndView order(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            LOGGER.info("开始查询订单，查询的订单状态为:{}", "all");
            List<OrderVO> orders = orderService.findAllByStatu(null, user.getUserId());
            LOGGER.info("所有订单查询成功，共查询到:{}", orders.size() + "个订单");
            LOGGER.info("开始查询订单，查询的订单状态为:{}", OrderStatu.NO_PAY.getDescription());
            List<OrderVO> orders1 = orderService.findAllByStatu(OrderStatu.NO_PAY.getValue(), user.getUserId());
            List<OrderVO> orders11 = orderService.findAllByStatu(OrderStatu.CANCELED.getValue(), user.getUserId());
            orders1.addAll(orders11);
            LOGGER.info("未支付订单查询成功，共查询到:{}", orders1.size() + "个订单");
            LOGGER.info("开始查询订单，查询的订单状态为:{}", OrderStatu.PAYED_BUT_NO_DELIVER.getDescription());
            List<OrderVO> orders2 = orderService.findAllByStatu(OrderStatu.PAYED_BUT_NO_DELIVER.getValue(), user.getUserId());
            List<OrderVO> orders22 = orderService.findAllByStatu(OrderStatu.SURED.getValue(), user.getUserId());
            orders2.addAll(orders22);
            LOGGER.info("待发货订单查询成功，共查询到:{}", orders2.size() + "个订单");
            LOGGER.info("开始查询订单，查询的订单状态为:{}", OrderStatu.PAYED_AND_DELIVERED.getDescription());
            List<OrderVO> orders3 = orderService.findAllByStatu(OrderStatu.PAYED_AND_DELIVERED.getValue(), user.getUserId());
            LOGGER.info("待收货订单查询成功，共查询到:{}", orders3.size() + "个订单");
            LOGGER.info("开始查询订单，查询的订单状态为:{}", OrderStatu.RECEIVED.getDescription());
            List<OrderVO> orders4 = orderService.findAllByStatu(OrderStatu.RECEIVED.getValue(), user.getUserId());
            LOGGER.info("未评价订单查询成功，共查询到:{}", orders4.size() + "个订单");
            return new ModelAndView("person/order").addObject("orders", orders)
                    .addObject("orders1", orders1).addObject("orders2", orders2)
                    .addObject("orders3", orders3).addObject("orders4", orders4);
        }catch (Exception e){
            LOGGER.error("查询订单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/error");
        }
    }

    /**
     * 生成订单
     * @param order
     * @param request
     * @param result
     * @return
     */
    @PostMapping("/createOrder")
    private @ResponseBody JsonResult createOrder(@Valid CreateOrder order, HttpServletRequest request, BindingResult result){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult(400, "用户没有登录");
        }
        if(order == null){
            return new JsonResult(401, "别搞事");
        }
        try {
            if(result.hasErrors()){
                return new JsonResult(401, "别搞事");
            }
            LOGGER.info("接收到创建订单数据为:{}", order);
            //判断支付密码是否正确
            if(order.getPassword() != null && userService.checkPayPassword(user.getUserId(), order.getPassword()) == 3){
                LOGGER.info("用户还没有设置快捷支付");
                return new JsonResult(301, "用户没有设置快捷支付");
            }else if(order.getPassword() != null && userService.checkPayPassword(user.getUserId(), order.getPassword()) == 1){
                LOGGER.info("支付密码正确");
                //开始创建订单
                order.setUserId(user.getUserId());
                orderService.createOrder(order);
                LOGGER.info("创建订单成功");
                return new JsonResult(200, "创建订单成功", order.getStatu());
            }else {
                LOGGER.info("支付密码错误");
                return new JsonResult(300, "支付密码错误");
            }
        }catch (Exception e){
            LOGGER.error("创建订单失败，发生未知异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "未知错误");
        }
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @RequestMapping("/orderInfo/{orderId}")
    private ModelAndView orderInfo(@PathVariable(name = "orderId") String orderId){
        try {
            Integer orderIdInteger = Integer.valueOf(new String(Base64Utils.decodeFromUrlSafeString(orderId), "UTF-8"));
            OrderInfo orderInfo = orderService.findOrderInfoByOrderId(orderIdInteger);
            LOGGER.info("查询订单详细信息成功");
            return new ModelAndView("person/orderinfo").addObject("orderInfo", orderInfo);
        } catch (Exception e) {
            LOGGER.info("订单详情查询失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/error");
        }
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @RequestMapping("/orderInfo2/{orderId}")
    private ModelAndView orderInfo2(@PathVariable(name = "orderId") Integer orderId){
        try {
            OrderInfo orderInfo = orderService.findOrderInfoByOrderId(orderId);
            LOGGER.info("查询订单详细信息成功");
            return new ModelAndView("person/orderinfo").addObject("orderInfo", orderInfo);
        } catch (Exception e) {
            LOGGER.info("订单详情查询失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/error");
        }
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancelOrder")
    private @ResponseBody JsonResult cancelOrder(Integer orderId){
        LOGGER.info("取消订单:{}", orderId);
        try {
            LOGGER.info("开始取消订单");
            JsonResult jsonResult = orderService.cancelOrder(orderId);
            LOGGER.info("取消订单成功");
            return jsonResult;
        }catch (Exception e){
            LOGGER.error("订单取消失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "error");
        }
    }

    /**
     * 删除已取消的订单
     * @param orderId
     * @return
     */
    @PostMapping("/deleteOrder")
    private @ResponseBody JsonResult deleteOrder(Integer orderId){
        LOGGER.info("删除订单:{}", orderId);
        try {
            LOGGER.info("开始删除订单");
            JsonResult jsonResult = orderService.deleteOrder(orderId);
            LOGGER.info("删除订单成功");
            return jsonResult;
        }catch (Exception e){
            LOGGER.error("订单删除失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "error");
        }
    }


    /**
     * 确认收货
     * @param orderId
     * @return
     */
    @PostMapping("/deliveredOrder")
    private @ResponseBody JsonResult deliveredOrder(Integer orderId) {
        LOGGER.info("确认收货:{}", orderId);
        try {
            return orderService.deliveredOrder(orderId);
        } catch (Exception e) {
            LOGGER.error("确认收货失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "发生异常");
        }
    }
}

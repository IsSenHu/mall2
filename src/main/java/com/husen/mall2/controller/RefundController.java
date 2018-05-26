package com.husen.mall2.controller;

import com.husen.mall2.enums.RefundStatu;
import com.husen.mall2.model.Refund;
import com.husen.mall2.model.User;
import com.husen.mall2.service.RefundService;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.OrderVO;
import com.husen.mall2.vo.RefundVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author husen
 */
@Controller
public class RefundController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RefundController.class);

    @Autowired
    private RefundService refundService;

    /**
     * 跳转到申请退款页面
     * @param request
     * @param itemId
     * @param orderId
     * @return
     */
    @RequestMapping("/refund")
    private ModelAndView refund(String orderId, String itemId, HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录:{}", request.getSession(true).getId());
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            Integer orderIdInt = Integer.valueOf(new String(Base64Utils.decodeFromUrlSafeString(orderId), "UTF-8"));
            Integer itemIdInt = Integer.valueOf(new String(Base64Utils.decodeFromUrlSafeString(itemId), "UTF-8"));
            RefundVO vo = refundService.createRefund(orderIdInt, itemIdInt, user.getUserId());
            return new ModelAndView("person/refund").addObject("refund", vo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("home/error");
        }
    }

    /**
     * 保存退款申请单
     * @param request
     * @param refund
     * @return
     */
    @PostMapping("/submitRefund")
    private @ResponseBody JsonResult submitRefund(HttpServletRequest request, Refund refund){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult(400, "nolOgin");
        }
        try {
            LOGGER.info("开始保存退款单，itemId_orderId:{}", refund.getItemId() + "_" + refund.getOrderId());
            JsonResult jsonResult = refundService.saveRefund(user.getUserId(), refund);
            return jsonResult;
        }catch (Exception e){
            LOGGER.error("保存退款申请单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "error");
        }
    }

    /**取消退款
     * @param refundId
     * @return
     */
    @PostMapping("/cancelRefund")
    private @ResponseBody JsonResult cancelRefund(Integer refundId){
        LOGGER.info("要取消的退款单id是:{}", refundId);
        try{
            LOGGER.info("开始取消退款");
            JsonResult jsonResult = refundService.cancelRefund(refundId);
            return jsonResult;
        }catch (Exception e){
            LOGGER.error("取消订单失败:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "error");
        }
    }

    /**
     * 跳转到成功取消页面
     * @param orderId
     * @param itemId
     * @param request
     * @return
     */
    @RequestMapping("/cancelSuccess")
    private ModelAndView cancelSuccess(String orderId, String itemId, HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录:{}", request.getSession(true).getId());
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            Integer orderIdInt = Integer.valueOf(new String(Base64Utils.decodeFromUrlSafeString(orderId), "UTF-8"));
            Integer itemIdInt = Integer.valueOf(new String(Base64Utils.decodeFromUrlSafeString(itemId), "UTF-8"));
            RefundVO vo = refundService.createRefund(orderIdInt, itemIdInt, user.getUserId());
            return new ModelAndView("person/refundSuccess").addObject("refund", vo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("home/error");
        }
    }

    /**
     * 跳转到退款管理页面
     * @return
     */
    @RequestMapping("/refunds")
    private ModelAndView refunds(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("当前用户没有登录");
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            LOGGER.info("开始显示全部的退款单");
            List<OrderVO> orders = refundService.findAllByStatu(null);
            LOGGER.info("查询到全部退款的数量为:{}", orders.size());
            LOGGER.info("开始查询等待商家商家处理的退款单");
            List<OrderVO> orders1 = refundService.findAllByStatu(RefundStatu.WAIT.getValue());
            LOGGER.info("查询到等待商家处理的退款单数量为:{}", orders1.size());
            LOGGER.info("开始查询退款成功的退款单");
            List<OrderVO> orders2 = refundService.findAllByStatu(RefundStatu.SUCCESS.getValue());
            LOGGER.info("查询到的退款成功的退款单的数量为:{}", orders2.size());
            LOGGER.info("开始查询商家拒绝的退款单");
            List<OrderVO> orders3 = refundService.findAllByStatu(RefundStatu.FAILE.getValue());
            LOGGER.info("查询到商家拒绝的退款单数量为:{}", orders3.size());
            return new ModelAndView("person/refunds").addObject("orders", orders)
                    .addObject("orders1", orders1).addObject("orders2", orders2)
                    .addObject("orders3", orders3);
        }catch (Exception e){
            LOGGER.error("查询退款单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("person/refunds");
        }
    }
    /**
     * 售后管理页面
     * @return
     */
    @RequestMapping("/change")
    private ModelAndView change(){
        return new ModelAndView("person/change");
    }

    /**
     * 钱款去向页面
     * @return
     */
    @RequestMapping("/record")
    private ModelAndView record(){
        return new ModelAndView("person/record");
    }
}

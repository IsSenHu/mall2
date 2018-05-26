package com.husen.mall2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author husen
 */
@Controller
public class CouponController {

    /**
     * 查看自己的优惠券，可用的，已用的，已过期的
     * @return
     */
    @RequestMapping("/coupons")
    private ModelAndView coupons(){
        return new ModelAndView("person/coupon");
    }
    /**
     * 立即使用功能
     * */

    /**
     * 删除已使用或过期的优惠券的功能
     * */
}

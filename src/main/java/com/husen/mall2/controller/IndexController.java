package com.husen.mall2.controller;

import com.husen.mall2.model.BalanceDetail;
import com.husen.mall2.model.User;
import com.husen.mall2.service.BalanceDetailService;
import com.husen.mall2.service.OrderService;
import com.husen.mall2.service.UserService;
import com.husen.mall2.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author husen
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private IntroductionController introductionController;

    @Autowired
    private BalanceDetailService balanceDetailService;

    @Autowired
    private OrderService orderService;

    /**
     * 跳转到个人中心主页
     * @return
     */
    @RequestMapping("/index")
    private ModelAndView index(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        return new ModelAndView("person/index")
                .addObject("user", user)
                .addObject("safeScore", userService.safeScore(user.getUserId()))
                .addObject("couponNumber", introductionController.findMyCouponsNumber(request))
                .addObject("balance", balanceDetailService.getBalance(user.getUserId()))
                .addObject("orders", orderService.getTwoMyOrder(user.getUserId()));
    }
}

package com.husen.mall2.controller;

import com.husen.mall2.enums.OrderStatu;
import com.husen.mall2.model.User;
import com.husen.mall2.service.OrderService;
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
public class ReviewController {

    @Autowired
    private OrderService orderService;

    /**
     * 评价管理，查看自己评价的页面
     * @return
     */
    @RequestMapping("/comment")
    private ModelAndView comment(){
        return new ModelAndView("person/comment");
    }

    /**
     * 跳转到发表评论页面
     * @return
     */
    @RequestMapping("/commentlist")
    private ModelAndView commentlist(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        List<OrderVO> orders = orderService.findAllByStatu(OrderStatu.RECEIVED.getValue(), user.getUserId());
        return new ModelAndView("person/commentlist")
                .addObject("orders", orders);
    }
}

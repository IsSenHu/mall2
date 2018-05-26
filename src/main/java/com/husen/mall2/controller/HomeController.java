package com.husen.mall2.controller;

import com.husen.mall2.service.HomeService;
import com.husen.mall2.vo.AllChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author husen
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HomeService homeService;

    /**
     * 跳转到商城主页
     * @return
     */
    @RequestMapping("/home")
    private ModelAndView home(){
        AllChannel allChannel = homeService.allChannel();
        return new ModelAndView("home/home3").addObject("allChannel", allChannel);
    }
}

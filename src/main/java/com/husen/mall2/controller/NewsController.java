package com.husen.mall2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author husen
 */
@Controller
public class NewsController {

    /**
     * 我的消息页面
     * @return
     */
    @RequestMapping("/news")
    private ModelAndView news(){
        return new ModelAndView("person/news");
    }
}

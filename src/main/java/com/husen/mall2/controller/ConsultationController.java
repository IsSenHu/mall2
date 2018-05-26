package com.husen.mall2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author husen
 */
@Controller
public class ConsultationController {

    /**
     * 商品咨询页面
     * @return
     */
    @RequestMapping("/consultation")
    private ModelAndView consultation(){
        return new ModelAndView("person/consultation");
    }

    /**
     * 提交功能
     * */
}

package com.husen.mall2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author husen
 */
@Controller
public class SuggestController {

    /**
     * 意见反馈页面
     * @return
     */
    @RequestMapping("/suggest")
    private ModelAndView suggest(){
        return new ModelAndView("person/suggest");
    }
}

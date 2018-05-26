package com.husen.mall2.controller;

import com.husen.mall2.model.User;
import com.husen.mall2.service.CollectionService;
import com.husen.mall2.vo.GoodCollectionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CollectionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CollectionController.class);
    @Autowired
    private CollectionService collectionService;
    /**
     * 跳转到我的收藏页面
     * @param request
     * @return
     */
    @RequestMapping("/collection")
    private ModelAndView collection(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            List<GoodCollectionVO> goodCollectionVOS = collectionService.findAllCollectionByUserId(user.getUsername());
            LOGGER.info("查询成功");
            return new ModelAndView("person/collection")
                    .addObject("goods", goodCollectionVOS);
        }catch (Exception e){
            LOGGER.error("查询失败，发生未知异常:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("person/collection");
        }
    }
}

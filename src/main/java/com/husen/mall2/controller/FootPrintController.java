package com.husen.mall2.controller;

import com.husen.mall2.model.User;
import com.husen.mall2.service.FootPrintService;
import com.husen.mall2.vo.FootVO;
import com.husen.mall2.vo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author husen
 */
@Controller
public class FootPrintController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FootPrintController.class);
    @Autowired
    private FootPrintService footPrintService;

    /**
     * 查询用户足迹
     * @param
     * @return
     */
    @RequestMapping("/foot")
    private ModelAndView foot(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }else {
            LOGGER.info("查询足迹的用户id是：{}", user.getUserId());
        }
        FootVO footVO = footPrintService.findAllByUserId(user.getUserId());
        return new ModelAndView("person/foot")
                .addObject("foot", footVO);
    }

    /**
     * 删除足迹
     * @param footId
     * @param request
     * @return
     */
    @PostMapping("/delFoot")
    private @ResponseBody JsonResult<Integer> delete(Integer footId, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        User user  = (User) session.getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "你没有登录", null);
        }
        try {
            LOGGER.info("要删除的footId是：{}", footId);
            footPrintService.delete(footId, user.getUserId());
            LOGGER.info("删除成功：{}", footId);
            return new JsonResult<>(200, "删除成功", footId);
        }catch (Exception e){
            LOGGER.error("删除失败，发生未知错误：{}", e.getMessage());
            return new JsonResult<>(500, "未知错误", footId);
        }
    }
}

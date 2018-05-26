package com.husen.mall2.controller;

import com.husen.mall2.model.User;
import com.husen.mall2.service.UserService;
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

/**
 * 登录的控制器
 * @author husen
 */
@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    private ModelAndView toLogin(){
        return new ModelAndView("home/login");
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/login")
    private @ResponseBody JsonResult<String> login(String username, String password, HttpServletRequest request){
        LOGGER.info("登录的帐号是：{}", username);
        User result = userService.login(username, password);
        if(result != null){
            LOGGER.info("登录成功：{}", username);
            request.getSession(true).setAttribute("user", result);
            return new JsonResult<>(200, "登录成功", username);
        }else {
            LOGGER.info("用户名或密码错误：{}", username);
            return new JsonResult<>(400, "登录失败", username);
        }
    }
}

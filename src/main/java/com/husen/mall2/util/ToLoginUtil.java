package com.husen.mall2.util;

import com.husen.mall2.model.User;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 到登录页面的工具类
 * @author husen
 */
public class ToLoginUtil {

    /**
     * 判断是否应该到登录页面
     * @param session
     * @return
     */
    public static ModelAndView ifLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }else {
            return null;
        }
    }
}

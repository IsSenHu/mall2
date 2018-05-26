package com.husen.mall2.controller;

import com.husen.mall2.model.User;
import com.husen.mall2.service.UserService;
import com.husen.mall2.util.DateUtil;
import com.husen.mall2.util.OssUtil;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author husen
 */
@Controller
public class InformationController {

    private final static Logger LOGGER = LoggerFactory.getLogger(InformationController.class);
    @Autowired
    private UserService userService;
    @RequestMapping("/information")
    private ModelAndView information(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }else {
            try {
                UserVO vo = userService.findById(user.getUserId());
                return new ModelAndView("person/information")
                        .addObject("user", vo);
            }catch (Exception e){
                LOGGER.error("请求出错，发生异常：{}", e.getMessage());
                return new ModelAndView("person/information");
            }
        }
    }

    /**
     * 修改用户信息
     * @param vo
     * @param result
     * @return
     */
    @PostMapping("/updateInformation")
    private @ResponseBody JsonResult<Map<String, String>> updateInformation(@Valid UserVO vo, BindingResult result, MultipartFile header, HttpServletRequest request){
        LOGGER.info("接收到的新数据是：{}", vo);
        Map<String, String> error = new HashMap<>(10);
        if(result.hasErrors()){
            result.getFieldErrors().stream().forEach(x -> {
                error.put(x.getField(), x.getDefaultMessage());
                LOGGER.info(x.getDefaultMessage());
            });

            return new JsonResult<>(400, "输入有误", error);
        }else {
            //先检查昵称、手机号、邮箱
            if(!userService.checkNickName(vo.getUserId(), vo.getNickName())){
                error.put("checkNickName", "该昵称已被使用");
            }
            if(!userService.checkPhone(vo.getUserId(), vo.getPhone())){
                error.put("checkPhone", "该手机号已被注册");
            }
            if(!userService.checkEmail(vo.getUserId(), vo.getEmail())){
                error.put("checkEmail", "该邮箱已被注册");
            }
            if(error.size() > 0){
                return new JsonResult<>(401, "输入有误", error);
            }
            try {
                if(header != null){
                    LOGGER.info("修改头像了");
                    String picPath = OssUtil.uploadFile(header);
                    if(StringUtils.isNotBlank(picPath)){
                        LOGGER.info("上传成功：{}", picPath);
                        vo.setPic(picPath);
                    }
                }else {
                    LOGGER.info("没有修改头像");
                }
                userService.update(vo);
                LOGGER.info("修改成功：{}", vo);
                //重新设置session里的用户
                User user = userService.findByIdPO(vo.getUserId());
                request.getSession(true).setAttribute("user", user);
                return new JsonResult<>(200, "修改成功", null);
            }catch (Exception e){
                LOGGER.error("修改失败，发生未知错误：{}", e.getMessage());
                e.printStackTrace();
                return new JsonResult<>(500, "未知错误", null);
            }
        }
    }
    @PostMapping("/changeDate")
    private @ResponseBody JsonResult<String> changeDate(Integer year, Integer month){
        int lastDate = DateUtil.getCurrentMonthLastDay(year, month);
        StringBuilder day = new StringBuilder("");
        for(int i = 1; i <= lastDate; i++){
            day.append("<option value=\"" + i + "\">" + i + "</option>");
        }
        return new JsonResult<>(200, "请求成功", day.toString());
    }
}

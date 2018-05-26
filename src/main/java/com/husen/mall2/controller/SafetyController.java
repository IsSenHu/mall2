package com.husen.mall2.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.husen.mall2.model.Question;
import com.husen.mall2.model.User;
import com.husen.mall2.service.UserService;
import com.husen.mall2.service.mail.MailService;
import com.husen.mall2.util.JsonResultUtil;
import com.husen.mall2.vo.EmailVO;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.Questions;
import com.husen.mall2.vo.SafetyVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author husen
 */
@Controller
public class SafetyController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SafetyController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MailService mailService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    /**
     * 到安全页面
     * * @param request
     * @return
     */
    @RequestMapping("/safety")
    private ModelAndView safety(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        SafetyVO safetyVO = new SafetyVO();
        safetyVO.setEmail(user.getEmail());
        safetyVO.setPhone(user.getPhone());
        safetyVO.setHeader(user.getPic());
        safetyVO.setUsername(user.getUsername());
        safetyVO.setScore(userService.safeScore(user.getUserId()));
        return new ModelAndView("person/safety")
                .addObject("safe", safetyVO);
    }

    /**
     * 到修改登录密码页面
     * @return
     */
    @RequestMapping("/password")
    private ModelAndView password(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        return new ModelAndView("person/password");
    }

    /**
     * 修改用户密码，不用更新session中的用户，因为安全性以及密码用不到
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @param request
     * @return
     */
    @PostMapping("/updatePassword")
    private @ResponseBody JsonResult<Map<String, String>> updatePassword(String oldPassword, String newPassword, String confirmPassword, HttpServletRequest request){
        //判断旧密码是否正确
        User user = (User) request.getSession(true).getAttribute("user");
        Map<String, String> error = new HashMap<>(4);
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }else {
            if(userService.checkOldPassword(oldPassword, user.getUserId())){
                LOGGER.info("旧密码输入正确");
                if(StringUtils.isBlank(newPassword)){
                    error.put("newPassword", "新密码不能为空");
                }else if(newPassword.trim().length() < 6 || newPassword.trim().length() > 16) {
                    error.put("newPassword", "新密码长度在6到16位之间");
                }
                if(StringUtils.isBlank(confirmPassword)){
                    error.put("confirmPassword", "重复密码不能为空");
                }else if(confirmPassword.trim().length() < 6 || confirmPassword.trim().length() > 16){
                    error.put("confirmPassword", "重复密码长度在6到16位之间");
                }else if(newPassword != null && !newPassword.equals(confirmPassword)){
                    LOGGER.info("两次密码输入不相等");
                    error.put("confirmPassword", "两次密码输入不相等");
                }
                if(error.size() > 0){
                    LOGGER.info("输入有错误");
                    return new JsonResult<>(401, "输入错误", error);
                }else {
                    try {
                        LOGGER.info("输入正确，开始修改密码");
                        userService.updatePassword(user.getUserId(), newPassword);
                        LOGGER.info("密码修改成功");
                        return new JsonResult<>(200, "密码修改成功", null);
                    }catch (Exception e){
                        LOGGER.error("密码修改失败，发生未知异常：{}", e.getMessage());
                        e.printStackTrace();
                        return new JsonResult<>(500, "未知错误", null);
                    }
                }
            }else {
                LOGGER.info("旧密码输入错误");
                if(StringUtils.isBlank(newPassword)){
                    error.put("newPassword", "新密码不能为空");
                }else if(newPassword.trim().length() < 6 || newPassword.trim().length() > 16) {
                    error.put("newPassword", "新密码长度在6到16位之间");
                }
                if(StringUtils.isBlank(confirmPassword)){
                    error.put("confirmPassword", "重复密码不能为空");
                }else if(confirmPassword.trim().length() < 6 || confirmPassword.trim().length() > 16){
                    error.put("confirmPassword", "重复密码长度在6到16位之间");
                }else if(newPassword != null && !newPassword.equals(confirmPassword)){
                    error.put("confirmPassword", "两次密码输入不想等");
                }
                return new JsonResult<>(402, "旧密码输入错误", error);
            }
        }
    }

    /**
     * 跳转到设置支付密码
     * @param request
     * @return
     */
    @RequestMapping("/setpay")
    private ModelAndView setPay(HttpServletRequest request){
        User user;
        if((user = (User) request.getSession(true).getAttribute("user")) == null){
            return new ModelAndView("redirect:/toLogin");
        }
        return new ModelAndView("person/setpay")
                .addObject("phone", user.getPhone());
    }

    /**
     * 修改支付密码，发送验证短信
     * @param request
     * @return
     */
    @PostMapping("/setpaySendSms")
    private @ResponseBody JsonResult<String> setpaySendSms(HttpServletRequest request){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(401, "用户没有登录", null);
        }
        String phone = user.getPhone();
        String codeTime = valueOperations.get(phone + "reset");
        if(codeTime != null){
            return new JsonResult<>(403, "1分钟内不能重复发送", null);
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        //短信API产品名称（短信产品名固定，无需修改）
        final String product = "Dysmsapi";
        //短信API产品域名（接口地址固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";
        //替换成你的AK
        //你的accessKeyId,参考本文档步骤2
        final String accessKeyId = "LTAI07hchIXDO0ZY";
        //你的accessKeySecret，参考本文档步骤2
        final String accessKeySecret = "d08PwCrtoiQGjroNo8IoxdQ4OhuxXO";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            LOGGER.info("短信客户端错误：{}", e.getMessage());
            return JsonResultUtil.faile(phone, "短信客户端错误：{}");
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest requestSms = new SendSmsRequest();
        //使用post提交
        requestSms.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        requestSms.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        requestSms.setSignName("爱动网");
        //必填:短信模板-可在短信控制台中找到
        requestSms.setTemplateCode("SMS_129762977");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //随机生成6位验证码
        int code = 100000 + (int) (Math.random() * 900000);
        requestSms.setTemplateParam("{\"code\":\"" + code + "\"}");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(requestSms);
        } catch (ClientException e) {
            LOGGER.info("短信客户端错误：{}", e.getMessage());
            return JsonResultUtil.faile(phone, "短信客户端错误：{}");
        }
        LOGGER.info("验证码为：{}", code);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            /*
            * 如果发送成功了，就把验证码和手机号存进redis中，并设置超时时间为5分钟
            * */
            valueOperations.set(phone, Integer.valueOf(code).toString(), 60 * 5, TimeUnit.SECONDS);
            valueOperations.set(phone + "reset", Integer.valueOf(code).toString(), 100, TimeUnit.SECONDS);
            return JsonResultUtil.success(phone, "短信已发送");
        }
        return new JsonResult<>(402, "发送失败", phone);
    }

    /**
     * 修改支付密码
     * @param code
     * @param newPassword
     * @param confirmPassword
     * @param request
     * @return
     */
    @PostMapping("/resetpay")
    private @ResponseBody JsonResult resetpay(String code, String newPassword, String confirmPassword, HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult(400, "用户没有登录", null);
        }
        String phone = user.getPhone();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String redisCode = operations.get(phone);
        if(redisCode == null){
            //超时了
            return new JsonResult(401, "验证码已过期", null);
        }
        if(!code.equals(redisCode)){
            //验证码错误
            return new JsonResult(402, "验证码错误", null);
        }
        if (newPassword == null || newPassword.trim().length() != 6 || confirmPassword == null || !confirmPassword.equals(newPassword)){
            return new JsonResult(403, "输入错误", null);
        }
        try {
            LOGGER.info("开始修改支付密码");
            userService.resetpay(user.getUserId(), newPassword);
            LOGGER.info("支付密码修改成功");
            return new JsonResult(200, "修改成功", null);
        }catch (Exception e){
            LOGGER.error("修改支付密码失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "发生未知错误", null);
        }
    }

    /**
     * 跳转到绑定手机号页面
     * @param request
     * @return
     */
    @RequestMapping("/bindPhone")
    private ModelAndView bindPhone(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        String phone = user.getPhone();
        return new ModelAndView("person/bindphone")
                .addObject("phone", phone);
    }

    /**
     * 发送验证短信
     * @param request
     * @return
     */
    @PostMapping("/sendNoPhone")
    private @ResponseBody JsonResult<String> sendNoPhone(HttpServletRequest request){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(401, "用户没有登录", null);
        }
        String phone = user.getPhone();
        String codeTime = valueOperations.get(phone + "reset");
        if(codeTime != null){
            return new JsonResult<>(403, "1分钟内不能重复发送", null);
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        //短信API产品名称（短信产品名固定，无需修改）
        final String product = "Dysmsapi";
        //短信API产品域名（接口地址固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";
        //替换成你的AK
        //你的accessKeyId,参考本文档步骤2
        final String accessKeyId = "LTAI07hchIXDO0ZY";
        //你的accessKeySecret，参考本文档步骤2
        final String accessKeySecret = "d08PwCrtoiQGjroNo8IoxdQ4OhuxXO";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            LOGGER.info("短信客户端错误：{}", e.getMessage());
            return JsonResultUtil.faile(phone, "短信客户端错误：{}");
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest requestSms = new SendSmsRequest();
        //使用post提交
        requestSms.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        requestSms.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        requestSms.setSignName("爱动网");
        //必填:短信模板-可在短信控制台中找到
        requestSms.setTemplateCode("SMS_129762977");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //随机生成6位验证码
        int code = 100000 + (int) (Math.random() * 900000);
        requestSms.setTemplateParam("{\"code\":\"" + code + "\"}");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(requestSms);
        } catch (ClientException e) {
            LOGGER.info("短信客户端错误：{}", e.getMessage());
            return JsonResultUtil.faile(phone, "短信客户端错误：{}");
        }
        LOGGER.info("验证码为：{}", code);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            /*
            * 如果发送成功了，就把验证码和手机号存进redis中，并设置超时时间为5分钟
            * */
            valueOperations.set(phone, Integer.valueOf(code).toString(), 60 * 5, TimeUnit.SECONDS);
            valueOperations.set(phone + "reset", Integer.valueOf(code).toString(), 100, TimeUnit.SECONDS);
            return JsonResultUtil.success(phone, "短信已发送");
        }
        return new JsonResult<>(402, "发送失败", phone);
    }

    /**
     * 发送验证短信
     * @param newPhone
     * @return
     */
    @PostMapping("/sendByPhone")
    private @ResponseBody JsonResult<String> sendByPhone(String newPhone){
        if(userService.checkPhone(newPhone) > 0){
            return new JsonResult<>(233, "该手机号已被注册", null);
        }
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String codeTime = valueOperations.get(newPhone + "reset");
        if(codeTime != null){
            return new JsonResult<>(403, "1分钟内不能重复发送", null);
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        //短信API产品名称（短信产品名固定，无需修改）
        final String product = "Dysmsapi";
        //短信API产品域名（接口地址固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";
        //替换成你的AK
        //你的accessKeyId,参考本文档步骤2
        final String accessKeyId = "LTAI07hchIXDO0ZY";
        //你的accessKeySecret，参考本文档步骤2
        final String accessKeySecret = "d08PwCrtoiQGjroNo8IoxdQ4OhuxXO";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            LOGGER.info("短信客户端错误：{}", e.getMessage());
            return JsonResultUtil.faile(newPhone, "短信客户端错误：{}");
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest requestSms = new SendSmsRequest();
        //使用post提交
        requestSms.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        requestSms.setPhoneNumbers(newPhone);
        //必填:短信签名-可在短信控制台中找到
        requestSms.setSignName("爱动网");
        //必填:短信模板-可在短信控制台中找到
        requestSms.setTemplateCode("SMS_129762977");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //随机生成6位验证码
        int code = 100000 + (int) (Math.random() * 900000);
        requestSms.setTemplateParam("{\"code\":\"" + code + "\"}");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(requestSms);
        } catch (ClientException e) {
            LOGGER.info("短信客户端错误：{}", e.getMessage());
            return JsonResultUtil.faile(newPhone, "短信客户端错误：{}");
        }
        LOGGER.info("验证码为：{}", code);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            /*
            * 如果发送成功了，就把验证码和手机号存进redis中，并设置超时时间为5分钟
            * */
            valueOperations.set(newPhone, Integer.valueOf(code).toString(), 60 * 5, TimeUnit.SECONDS);
            valueOperations.set(newPhone + "reset", Integer.valueOf(code).toString(), 100, TimeUnit.SECONDS);
            return JsonResultUtil.success(newPhone, "短信已发送");
        }
        return new JsonResult<>(402, "发送失败", newPhone);
    }

    /**
     * 修改绑定的手机号
     * 要检查新的手机号是否已经存在
     * 修改手机后要更新session里的用户
     * @param newPhone
     * @param code
     * @param newCode
     * @param request
     * @return
     */
    @PostMapping("/bingingPhone")
    private @ResponseBody JsonResult<Map<String, String>> bingingPhone(String newPhone, String code, String newCode, HttpServletRequest request){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        User user = (User) request.getSession(true).getAttribute("user");
        String phone = user.getPhone();
        if(user == null){
            return new JsonResult(400, "用户没有登录", null);
        }
        Map<String, String> errors = new HashMap<>(4);
        String oldCode = operations.get(phone);
        if(oldCode == null){
            //超时了
            errors.put("oloCode", "a验证码已过期");
        }else if(!oldCode.equals(code)){
            errors.put("oldCode", "a验证码错误");
        }
        String newOldCode = operations.get(newPhone);
        if(newOldCode == null){
            errors.put("newOldCode", "b验证码已过期");
        }else if(!newOldCode.equals(newCode)){
            errors.put("newOldCode", "b验证码错误");
        }
        if(StringUtils.isBlank(newPhone)){
            errors.put("phone", "手机不能为空");
        }else if(userService.checkPhone(newPhone) > 0){
            errors.put("phone", "手机号已被注册");
        }
        if(errors.size() > 0){
            return new JsonResult<>(401, "验证不通过", errors);
        }
        try {
            LOGGER.info("开始绑定新的手机号");
            userService.bingdingPhone(user.getUserId(), newPhone);
            LOGGER.info("绑定新手机号成功");
            request.getSession(true).removeAttribute("user");
            user.setPhone(newPhone);
            request.getSession(true).setAttribute("user", user);
            return new JsonResult<>(200, "绑定新手机号成功", null);
        }catch (Exception e){
            LOGGER.error("绑定新手机号失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 跳转到验证邮箱页面
     * @param request
     * @return
     */
    @RequestMapping("/email")
    private ModelAndView emali(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        return new ModelAndView("person/email")
                .addObject("email", user.getEmail());
    }

    /**
     * 发送邮件验证码
     * @param email
     * @param result
     * @return
     */
    @PostMapping("/sendEmailCode")
    private @ResponseBody JsonResult<String> sendEmailCode(@Valid EmailVO email, BindingResult result, HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        if(userService.checkEmail(email.getEmail()) > 0 && !email.getEmail().equals(user.getEmail())){
            return new JsonResult<>(402, "该邮箱已被注册", null);
        }
        if(result.hasErrors()){
            LOGGER.info("邮箱输入格式不正确");
            return new JsonResult<>(401, "输入错误", result.getFieldError().getDefaultMessage());
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        int code = 100000 + (int) (Math.random() * 900000);
        operations.set(email.getEmail(), String.valueOf(code));
        LOGGER.info("发送邮件");
        //异步发送邮件
        taskExecutor.execute(() -> {
            try {
                mailService.sendSimple(email.getEmail(), "爱动商城邮箱验证", "验证码为：" + code);
                LOGGER.info("邮件发送成功");
            } catch (MessagingException e) {
                LOGGER.error("邮件发送失败");
                e.printStackTrace();
            }
        });
        return new JsonResult<>(200, "邮件发生成功", null);
    }

    /**
     * 验证邮箱和绑定邮箱
     * @param email
     * @param request
     * @param result
     * @return
     */
    @PostMapping("/bindEmail")
    private @ResponseBody JsonResult<String> bindEmail(@Valid EmailVO email, HttpServletRequest request, BindingResult result){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        if(result.hasErrors()){
            return new JsonResult<>(401, "输入有错", result.getFieldError().getDefaultMessage());
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String code = operations.get(email.getEmail());
        if(code == null || !code.equals(email.getCode())){
            return new JsonResult<>(402, "验证码错误", null);
        }
        operations.getOperations().delete(email.getEmail());
        try {
            LOGGER.info("开始修改或绑定邮箱");
            userService.bindEmail(user.getUserId(), email.getEmail());
            LOGGER.info("修改或绑定邮箱成功");
            request.getSession(true).removeAttribute("user");
            user.setEmail(email.getEmail());
            request.getSession(true).setAttribute("user", user);
            return new JsonResult<>(200, "成功", null);
        }catch (Exception e){
            LOGGER.error("修改或绑定邮箱失败，发生未知错误：{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 跳转到安全问题页面
     * @param request
     * @return
     */
    @RequestMapping("/question")
    private ModelAndView question(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        List<Question> questions = userService.getThreeQuestion();
        return new ModelAndView("person/question")
                .addObject("first", questions.get(0))
                .addObject("second", questions.get(1))
                .addObject("three", questions.get(2));
    }

    /**
     * 设置安全问题
     * @param questions
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/setSafeQuestion")
    private @ResponseBody JsonResult<Map<String, String>> setSafeQuestion(@Valid Questions questions, BindingResult result, HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        Map<String, String> errors = new HashMap<>(3);
        if(result.hasErrors()){
            result.getFieldErrors().stream().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        }
        if(errors.size() > 0){
            return new JsonResult<>(401, "输入错误", errors);
        }
        try {
            LOGGER.info("开始保存用户设置的安全问题");
            userService.setSafeQuestion(user.getUserId(), questions);
            LOGGER.info("安全问题设置成功");
            return new JsonResult<>(200, "成功", null);
        }catch (Exception e){
            LOGGER.error("保存用户安全问题失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }
}

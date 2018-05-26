package com.husen.mall2.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.husen.mall2.service.UserService;
import com.husen.mall2.util.JsonResultUtil;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.RegisterForEmail;
import com.husen.mall2.vo.RegisterForPhone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户注册的控制器
 * @author husen
 */
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
    @RequestMapping("/toRegister")
    private ModelAndView toRegister(){
        return new ModelAndView("home/register");
    }

    /**
     * 通过邮箱来注册
     * @return
     */
    @PostMapping("/registerForEmail")
    private @ResponseBody JsonResult<Map<String, String>> registerFoEmail(@Valid RegisterForEmail forEmail, BindingResult result){
        if(forEmail == null){
            LOGGER.info("你没有传递相关的参数：{}", forEmail);
            return JsonResultUtil.faile(null, "输入为空");
        }
        Map<String, String> error = new HashMap<>(4);
        if(result.hasErrors()){
            LOGGER.info("输入有误：{}", forEmail.toString());
            result.getFieldErrors().stream().forEach(x -> {
                error.put(x.getField(), x.getDefaultMessage());
            });
        }
        LOGGER.info("验证两次输入密码是否相同：{}", forEmail.toString());
        if(!forEmail.getPassword().equals(forEmail.getPassword2())){
            error.put("passwordPassword2", "两次密码输入不一致");
        }
        if(error.size() > 0){
            return JsonResultUtil.faile(error, "输入有误");
        }
        LOGGER.info("输入无误，开始注册");
        try {
            //首先判断邮箱是否已经注册过了，返回true表示注册成功，返回false表示该邮箱已被注册，抛出异常，注册失败
            if(userService.registerForEmail(forEmail)){
                LOGGER.info("注册成功：{}", forEmail.toString());
                return JsonResultUtil.success(null, "注册成功");
            }else {
                LOGGER.info("该邮箱已被注册：{}", forEmail.getEmail());
                error.put("email", "该邮箱已被注册");
                return JsonResultUtil.faile(error, "邮箱已被注册");
            }
        }catch (Exception e){
            LOGGER.error("注册失败，未知错误：{}", e.getMessage());
            error.put("result", "注册失败");
            return JsonResultUtil.faile(error, "注册失败");
        }
    }

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @PostMapping("/sendSMS")
    private @ResponseBody JsonResult<String> sendSMS(String phone){
        /*
        * 检查手机号是否被注册
        * */
        if(userService.checkPhone(phone) > 0){
            return new JsonResult<>(401, "该手机号已被注册", phone);
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
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("爱动网");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_129762977");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //随机生成6位验证码
        int code = 100000 + (int) (Math.random() * 900000);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            LOGGER.info("短信客户端错误：{}", e.getMessage());
            return JsonResultUtil.faile(phone, "短信客户端错误：{}");
        }
        LOGGER.info("验证码为：{}", sendSmsResponse.getCode());
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            /*
            * 如果发送成功了，就把验证码和手机号存进redis中，并设置超时时间为5分钟
            * */
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(phone, Integer.valueOf(code).toString(), 60 * 5, TimeUnit.SECONDS);
            return JsonResultUtil.success(phone, "短信已发送");
        }
        return new JsonResult<>(402, "发送失败", phone);
    }

    /**
     * 使用手机号进行注册
     * @param forPhone
     * @return
     */
    @PostMapping("/registerForPhone")
    private @ResponseBody JsonResult<Map<String, String>> registerForPhone(@Valid RegisterForPhone forPhone, BindingResult result){
        if(forPhone == null){
            LOGGER.info("你没有传递相关的参数：{}", forPhone);
            return JsonResultUtil.faile(null, "输入为空");
        }
        //先判断验证码是否正确
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if(forPhone.getCode() == null || forPhone.getCode().trim().length() != 6 || !operations.get(forPhone.getPhone()).equals(forPhone.getCode())){
            return new JsonResult<>(402, "验证码错误", null);
        }else {
            Map<String, String> error = new HashMap<>(3);
            if(result.hasErrors()){
                LOGGER.info("输入有误：{}", forPhone.toString());
                result.getFieldErrors().stream().forEach(x -> {
                    error.put(x.getField(), x.getDefaultMessage());
                });
            }
            if(!forPhone.getPassword().equals(forPhone.getPassword2())){
                error.put("passwordPassword2", "两次密码输入不一致");
            }
            if(error.size() > 0){
                return new JsonResult<>(401, "输入有误", error);
            }else {
                try {
                    LOGGER.info("数据验证成功，开始注册：{}", forPhone.toString());
                    userService.registerForPhone(forPhone);
                    LOGGER.info("注册成功:{}", forPhone.toString());
                    return new JsonResult<>(200, "注册成功", null);
                }catch (Exception e){
                    LOGGER.error("注册失败，发生未知异常：{}", e.getMessage());
                    return new JsonResult<>(500, "未知错误", null);
                }
            }
        }
    }
}

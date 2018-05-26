package com.husen.mall2.controller;

import com.husen.mall2.model.BankCard;
import com.husen.mall2.model.User;
import com.husen.mall2.service.BankService;
import com.husen.mall2.vo.BankVO;
import com.husen.mall2.vo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
public class CardlistController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CardlistController.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BankService bankService;

    /**
     * 快捷支付
     * @param request
     * @return
     */
    @RequestMapping("/cardlist")
    private ModelAndView cardlist(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            List<BankCard> bankCards = bankService.findAllByUserId(user.getUserId());
            LOGGER.info("查询银行卡成功");
            bankCards.stream().forEach(x -> x.setBankCardId(x.getBankCardId().substring(x.getBankCardId().length() - 4, x.getBankCardId().length())));
            return new ModelAndView("person/cardlist")
                    .addObject("bks", bankCards);
        }catch (Exception e){
            LOGGER.error("查询，发生未知异常:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("person/cardlist");
        }
    }

    @RequestMapping("/cardmethod")
    private ModelAndView cardmethod(){
        return new ModelAndView("person/cardmethod");
    }

    /**
     * 添加银行卡发送短信验证码
     * @param phone
     * @param request
     * @return
     */
    @PostMapping("/sendBankSmsCode")
    private @ResponseBody JsonResult<String> sendBankSmsCode(String phone, HttpServletRequest request){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String resetCode = operations.get(phone + "reset");
        if(resetCode != null){
            return new JsonResult<>(401, "发短信太频繁，一分钟内只能发送一次", null);
        }
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        //随机生成6位验证码
        int code = 100000 + (int) (Math.random() * 900000);
        LOGGER.info("验证码为:{}", code);
        try {
            operations.set(phone, String.valueOf(code), 60 * 5, TimeUnit.SECONDS);
            operations.set(phone + "reset", String.valueOf(code), 60, TimeUnit.SECONDS);
            LOGGER.info("发送短信验证码成功");
            return new JsonResult<>(200, "发送短信验证码成功", String.valueOf(code));
        }catch (Exception e){
            LOGGER.error("发送失败，发生未知异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 添加银行卡
     * @param request
     * @param bank
     * @param result
     * @return
     */
    @PostMapping("/addBankCard")
    private @ResponseBody JsonResult<Map<String, String>> addBankCard(HttpServletRequest request, @Valid BankVO bank, BindingResult result){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String code = operations.get(bank.getPhone());
        if(code == null){
            return new JsonResult<>(402, "验证码已过期", null);
        }
        if(!code.equals(bank.getCode())){
            return new JsonResult<>(403, "验证码错误", null);
        }
        Map<String, String> errors = new HashMap<>(5);
        if(result.hasErrors()){
            result.getFieldErrors().stream().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        }
        if(errors.size() > 0){
            LOGGER.info("输入有错");
            return new JsonResult<>(401, "输入错误", errors);
        }
        try {
            LOGGER.info("开始添加银行卡");
            boolean ifExist = bankService.addBankCard(bank, user.getUserId());
            if(!ifExist){
                LOGGER.info("该银行卡号已经存在");
                return new JsonResult<>(404, "该银行卡号已经存在", null);
            }else {
                LOGGER.info("添加成功");
                return new JsonResult<>(200, "添加成功", null);
            }
        }catch (Exception e){
            LOGGER.error("添加失败，发生未知异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 跳转到添加完成页面
     * @return
     */
    @RequestMapping("/cardMethodFinish")
    private ModelAndView cardMethodFinish(){
        return new ModelAndView("person/cardmethodfinish");
    }
}

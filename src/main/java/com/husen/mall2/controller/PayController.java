package com.husen.mall2.controller;

import com.husen.mall2.model.*;
import com.husen.mall2.service.AddressService;
import com.husen.mall2.service.ItemService;
import com.husen.mall2.util.Base64Util;
import com.husen.mall2.vo.ShopCart;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @author husen
 */
@Controller
public class PayController {
    private final static Logger LOGGER = LoggerFactory.getLogger(PayController.class);
    @Autowired
    private ItemService itemService;
    @Autowired
    private AddressService addressService;

    @RequestMapping("/pay")
    private ModelAndView pay(HttpServletRequest request, String itemIdStr){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            itemIdStr = new String(Base64Utils.decodeFromUrlSafeString(itemIdStr), "UTF-8");
            LOGGER.info("解密后的URL参数是:{}", itemIdStr);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URL参数解密失败:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("redirect:/shopCart");
        }
        try {
            //获取该用户的所有地址
            List<Address> addresses = addressService.findByUser(user.getUserId());
            //查询出所有的省
            List<Provinces> provinces = addressService.provinces();
            //查询出北京的区
            List<Cities> cities = new ArrayList<>();
            List<Areas> areas = new ArrayList<>();
            if(provinces != null && provinces.size() > 0){
                cities = addressService.cities(provinces.get(0).getProvinceid());
                if(cities != null && cities.size() > 0){
                    areas = addressService.areas(cities.get(0).getCityid());
                }
            }
            //用选择的条目生成支付单
            List<Integer> itemIdList = new ArrayList<>();
            if(StringUtils.isNotBlank(itemIdStr)){
                String[] itemIdArray = itemIdStr.split(",");
                LOGGER.info("itemIdArray:{}", itemIdArray);
                Arrays.stream(itemIdArray).forEach(x -> itemIdList.add(Integer.valueOf(x)));
            }else {
                return new ModelAndView("redirect:/shopCart");
            }
            ShopCart shopCart = itemService.shopCart(user.getUserId(), itemIdList);
            return new ModelAndView("home/pay")
                    .addObject("as", addresses)
                    .addObject("p", provinces)
                    .addObject("c", cities)
                    .addObject("a", areas)
                    .addObject("sc", shopCart)
                    .addObject("itemIdStr", itemIdStr);
        }catch (Exception e){
            LOGGER.error("跳转到支付页面失败，发生异常：{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/pay");
        }
    }

    @RequestMapping("/pay2")
    private ModelAndView pay2(HttpServletRequest request, String itemIdStr){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            //获取该用户的所有地址
            List<Address> addresses = addressService.findByUser(user.getUserId());
            //查询出所有的省
            List<Provinces> provinces = addressService.provinces();
            //查询出北京的区
            List<Cities> cities = new ArrayList<>();
            List<Areas> areas = new ArrayList<>();
            if(provinces != null && provinces.size() > 0){
                cities = addressService.cities(provinces.get(0).getProvinceid());
                if(cities != null && cities.size() > 0){
                    areas = addressService.areas(cities.get(0).getCityid());
                }
            }
            //用选择的条目生成支付单
            List<Integer> itemIdList = new ArrayList<>();
            if(StringUtils.isNotBlank(itemIdStr)){
                String[] itemIdArray = itemIdStr.split(",");
                LOGGER.info("itemIdArray:{}", itemIdArray);
                Arrays.stream(itemIdArray).forEach(x -> itemIdList.add(Integer.valueOf(x)));
            }else {
                return new ModelAndView("redirect:/shopCart");
            }
            ShopCart shopCart = itemService.shopCart(user.getUserId(), itemIdList);
            return new ModelAndView("home/pay")
                    .addObject("as", addresses)
                    .addObject("p", provinces)
                    .addObject("c", cities)
                    .addObject("a", areas)
                    .addObject("sc", shopCart)
                    .addObject("itemIdStr", itemIdStr);
        }catch (Exception e){
            LOGGER.error("跳转到支付页面失败，发生异常：{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/pay");
        }
    }

    /**
     * 跳转到支付成功页面
     * @param request
     * @param addressId
     * @param totalMoney
     * @return
     */
    @RequestMapping("/success")
    private ModelAndView success(HttpServletRequest request, String addressId, String totalMoney){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            Address address = addressService.findAddressById(Integer.valueOf(new String(Base64Utils.decodeFromUrlSafeString(addressId), "UTF-8")));
            totalMoney = new String(Base64Utils.decodeFromUrlSafeString(totalMoney), "UTF-8");
            return new ModelAndView("home/success")
                    .addObject("a", address)
                    .addObject("t", totalMoney);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URL参数解密失败:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home.error");
        }
    }
}

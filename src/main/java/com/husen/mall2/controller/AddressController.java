package com.husen.mall2.controller;

import com.husen.mall2.model.*;
import com.husen.mall2.service.AddressService;
import com.husen.mall2.vo.AddressVO;
import com.husen.mall2.vo.CityAndArea;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author husen
 */
@Controller
public class AddressController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private AddressService addressService;

    /**
     * 跳转到地址页面
     * @return
     */
    @RequestMapping("/address")
    private ModelAndView address(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new ModelAndView("redirect:/toLogin");
        }
        List<Provinces> provinces = addressService.provinces();
        List<Cities> c = new ArrayList<>();
        List<Areas> a = new ArrayList<>();
        if(provinces != null && provinces.size() > 0){
            Provinces p = provinces.get(0);
            c = addressService.cities(p.getProvinceid());
            if(c != null && c.size() > 0){
                Cities cities = c.get(0);
                a = addressService.areas(cities.getCityid());
            }
        }
        List<Address> addresses = addressService.findByUser(user.getUserId());
        return new ModelAndView("person/address")
                .addObject("p", provinces)
                .addObject("c", c)
                .addObject("a", a)
                .addObject("as", addresses);
    }

    /**
     * 根据选择的省份查询出市和区
     * @param provinceid
     * @return
     */
    @PostMapping("/changeProvince")
    private @ResponseBody JsonResult<CityAndArea> changeProvince(String provinceid){
        try {
            CityAndArea cityAndArea = addressService.cityAndArea(provinceid);
            LOGGER.info("查询成功");
            return new JsonResult<>(200, "查询成功", cityAndArea);
        }catch (Exception e){
            LOGGER.error("查询失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 根据选择的市选择区
     * @param cityid
     * @return
     */
    @PostMapping("/changeCity")
    private @ResponseBody JsonResult<List<Areas>> changeCity(String cityid){
        try{
            List<Areas> areas = addressService.areas(cityid);
            LOGGER.info("查询成功");
            return new JsonResult<>(200, "查询成功", areas);
        }catch (Exception e){
            LOGGER.error("查询失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 添加新地址
     * @param address
     * @param request
     * @return
     */
    @PostMapping("/addAddress")
    private @ResponseBody JsonResult<Address> addAddress(AddressVO address, HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if (user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        try{
            LOGGER.info("开始添加地址，接收到的地址信息为:{}", address.toString());
            Address newAddress = addressService.addAddress(user.getUserId(), address);
            LOGGER.info("添加成功");
            return new JsonResult<>(200, "添加成功", newAddress);
        }catch (Exception e){
            LOGGER.error("添加失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 删除地址
     * @param request
     * @param addressId
     * @return
     */
    @PostMapping("/deleteAddress")
    private @ResponseBody JsonResult deleteAddress(HttpServletRequest request, Integer addressId){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        try {
            LOGGER.info("开始删除地址");
            addressService.delete(addressId);
            LOGGER.info("删除地址成功");
            return new JsonResult(200, "删除成功", null);
        }catch (Exception e){
            LOGGER.error("删除失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 设置默认地址
     * @param request
     * @param addressId
     * @return
     */
    @PostMapping("/setDefault")
    private @ResponseBody JsonResult<Integer> setDefault(HttpServletRequest request, Integer addressId){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        try {
            LOGGER.info("开始设置默认地址");
            LOGGER.info("设置成功");
            addressService.setDefault(user.getUserId(), addressId);
            return new JsonResult(200, "设置成功", addressId);
        }catch (Exception e){
            LOGGER.error("设置默认地址失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 修改地址
     * @param address
     * @param request
     * @return
     */
    @PostMapping("/updateAddress")
    private @ResponseBody JsonResult<Address> updateAddress(AddressVO address, HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if (user == null){
            return new JsonResult<>(400, "用户没有登录", null);
        }
        try{
            LOGGER.info("开始修改地址，接收到的地址信息为:{}", address.toString());
            Address newAddress = addressService.updateAddress(address);
            LOGGER.info("修改成功");
            return new JsonResult<>(200, "修改成功", newAddress);
        }catch (Exception e){
            LOGGER.error("修改失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 根据id查找地址
     * @param addressId
     * @return
     */
    @PostMapping("/findAddressById")
    private @ResponseBody JsonResult<Address> findAddressById(Integer addressId){
        try {
            LOGGER.info("开始查询地址:{}", addressId);
            Address address = addressService.findAddressById(addressId);
            LOGGER.info("查询地址成功");
            return new JsonResult<>(200, "查询成功", address);
        }catch (Exception e){
            LOGGER.error("查询地址失败，发生未知错误：{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误");
        }
    }
}

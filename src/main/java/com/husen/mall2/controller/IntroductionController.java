package com.husen.mall2.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.husen.mall2.model.FootPrint;
import com.husen.mall2.model.Good;
import com.husen.mall2.model.User;
import com.husen.mall2.service.FootPrintService;
import com.husen.mall2.service.IntroductionService;
import com.husen.mall2.vo.GoodIntroductionVO;
import com.husen.mall2.vo.GoodVO;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品介绍
 * @author husen
 */
@Controller
public class IntroductionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(IntroductionController.class);

    private static final String COUPONS = "coupons";
    private static final String HAVED_COUPONS = "havedCoupons";

    @Autowired
    private IntroductionService introductionService;
    @Autowired
    private FootPrintService footPrintService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 查询出商品介绍页面所需要的东西
     * 添加记录我的足迹功能
     * 添加我可能喜欢的功能
     * @param goodId
     * @return
     */
    @RequestMapping("/introduction")
    private ModelAndView introduction(Integer goodId, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        Search search = (Search) session.getAttribute("search");
        //清除先前的条件，否则会出问题
        if(search != null){
            session.removeAttribute("search");
            session.setAttribute("search", search(search));
        }
        //添加我的足迹功能
        User user = (User) session.getAttribute("user");
        if(user != null){
            FootPrint footPrint = footPrint(user.getUserId(), goodId);
            try {
                footPrintService.save(footPrint);
                LOGGER.info("保存我的足迹成功");
            }catch (Exception e){
                LOGGER.info("保存我的足迹失败");
            }
        }
        LOGGER.info("接收到的商品ID为：{}", goodId);
        try {
            GoodIntroductionVO goodIntroduction = introductionService.introduction(goodId);
            //获取猜你喜欢的内容（根据浏览次数，从大到小排列，如果用户没有登录，就查询所有的记录）
            if(user != null){
                List<GoodVO> guestYouLike = footPrintService.guestYouLike(user.getUserId());
                goodIntroduction.setGuestYouLike(guestYouLike);
            }else {
                List<GoodVO> guestYouLike = footPrintService.guestYouLike();
                goodIntroduction.setGuestYouLike(guestYouLike);
            }
            LOGGER.info("查询到的商品介绍为：{}", goodIntroduction);
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            if(user != null){
                String key = user.getUsername() + "collects";
                String values = operations.get(key);
                List<Integer> goodIds = JSONArray.parseArray(values, Integer.class);
                if(goodIds != null && goodIds.contains(goodId)){
                    return new ModelAndView("home/introduction")
                            .addObject("good", goodIntroduction)
                            .addObject("collect", "yes");
                }
            }
            return new ModelAndView("home/introduction")
                    .addObject("good", goodIntroduction)
                    .addObject("collect", "no");
        }catch (Exception e){
            LOGGER.error("未知错误：{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/home3");
        }
    }

    /**
     * 存入redis里面
     * @param request
     * @param goodId
     * @return
     */
    @PostMapping("/collect")
    private @ResponseBody JsonResult<Integer> collect(HttpServletRequest request, Integer goodId){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户未登录", null);
        }
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String key = user.getUsername() + "collects";
            String oldValue = operations.get(key);
            if(oldValue == null){
                List<Integer> values = new ArrayList<>();
                values.add(goodId);
                LOGGER.info("here");
                operations.set(key, JSONArray.toJSONString(values));
            }else {
                List<Integer> values = JSONArray.parseArray(oldValue, Integer.class);
                if (!values.contains(goodId)){
                    values.add(goodId);
                    operations.set(key, JSONArray.toJSONString(values));
                }
            }
            LOGGER.info("收藏成功");
            return new JsonResult<>(200, "收藏成功", goodId);
        }catch (Exception e){
            LOGGER.error("收藏失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 取消收藏
     * @param request
     * @param goodId
     * @return
     */
    @PostMapping("/cancelCollect")
    private @ResponseBody JsonResult<Integer> cancelCollect(HttpServletRequest request, Integer goodId){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "用户未登录", null);
        }
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String key = user.getUsername() + "collects";
            String oldValue = operations.get(key);
            if(oldValue != null){
                List<Integer> goodIds = JSONArray.parseArray(oldValue, Integer.class);
                goodIds.remove(goodId);
                operations.set(key, JSONArray.toJSONString(goodIds));
            }
            LOGGER.info("取消收藏成功");
            return new JsonResult<>(200, "取消成功", goodId);
        }catch (Exception e){
            LOGGER.error("取消失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 获取优惠券
     * @param request
     * @param goodId
     * @return
     */
    @PostMapping("/getCoupons")
    private @ResponseBody JsonResult getCoupons(HttpServletRequest request, Integer goodId){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult(400, "用户没有登录", null);
        }
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String coupons = operations.get(user.getUsername() + COUPONS);
            String haveCoupons = operations.get(user.getUsername() + HAVED_COUPONS);
            if(coupons == null && haveCoupons == null){
                List<Integer> couponIds = introductionService.coupons(goodId);
                if(couponIds == null || couponIds.size() == 0){
                    return new JsonResult(402, "没有优惠券", null);
                }
                operations.set(user.getUsername() + COUPONS, JSONArray.toJSONString(couponIds));
                operations.set(user.getUsername() + HAVED_COUPONS, JSONArray.toJSONString(couponIds));
                LOGGER.info("领取优惠券成功");
                return new JsonResult(200, "领券成功", null);
            }else {
                LOGGER.info("haveCoupons:{}", haveCoupons);
                List<Integer> haveCouponsIdList = JSONArray.parseArray(haveCoupons, Integer.class);
                List<Integer> couponIds = introductionService.coupons(goodId);
                if(couponIds == null || couponIds.size() == 0){
                    return new JsonResult(402, "没有优惠券", null);
                }
                for(Integer id : couponIds){
                    LOGGER.info("haveCouponsIdList:{}", haveCouponsIdList);
                    if(haveCouponsIdList.contains(id)){
                        return new JsonResult(401, "你已经领取过优惠券了", null);
                    }
                }
                List<Integer> couponList = JSONArray.parseArray(coupons, Integer.class);
                for(Integer id : couponIds){
                    couponList.add(id);
                }
                operations.set(user.getUsername() + COUPONS, JSONArray.toJSONString(couponList));
                return new JsonResult(200, "领券成功", null);
            }
        }catch (Exception e){
            LOGGER.error("领取失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误", null);
        }
    }

    /**
     * 查询我拥有的优惠券数量
     * @param request
     * @return
     */
    public Integer findMyCouponsNumber(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return 0;
        }
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String coupons = operations.get(user.getUsername() + "coupons");
            if(coupons == null){
                return 0;
            }else {
                List<Integer> couponList = JSONArray.parseArray(coupons, Integer.class);
                return couponList.size();
            }
        }catch (Exception e){
            LOGGER.error("获取优惠券数量失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    private Search search(Search search){
        search.setCurrentPage(1);
        search.setPageSize(12);
        search.setApplyerId(null);
        search.setBrandId(null);
        search.setMaterialId(null);
        search.setSort(1);
        search.setGoodName("");
        return search;
    }
    private FootPrint footPrint(Integer userId, Integer goodId){
        FootPrint footPrint = new FootPrint();
        footPrint.setGood(new Good());
        footPrint.getGood().setGoodId(goodId);
        footPrint.setUser(new User());
        footPrint.getUser().setUserId(userId);
        return footPrint;
    }

}

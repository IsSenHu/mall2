package com.husen.mall2.controller;
import com.husen.mall2.model.User;
import com.husen.mall2.service.ShopCartService;
import com.husen.mall2.vo.ItemMoney;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.ShopCart;
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
 * @author husen
 */
@Controller
public class ShopCartController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ShopCartController.class);
    @Autowired
    private ShopCartService shopCartService;

    /**
     * 跳转到购物车
     * @return
     */
    @RequestMapping("/shopCart")
    private ModelAndView shopCart(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new ModelAndView("redirect:/toLogin");
        }
        try {
            ShopCart shopCart = shopCartService.shopCart(user.getUserId());
            LOGGER.info("查询购物车信息成功");
            return new ModelAndView("home/shopcart")
                    .addObject("sc", shopCart);
        }catch (Exception e){
            LOGGER.error("查询购物车失败，发生异常：{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/shopcart");
        }
    }

    /**
     * 加入购物车
     * @return
     */
    @RequestMapping("/addGoodToShopCart")
    private @ResponseBody JsonResult addGoodToShopCart(HttpServletRequest request, Integer goodId, Integer number){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new JsonResult(400, "用户没有登录");
        }
        try {
            LOGGER.info("开始加入购物车");
            shopCartService.saveItem(user.getUserId(), goodId, number);
            LOGGER.info("加入购物车成功");
            return new JsonResult(200, "加入成功");
        }catch (Exception e){
            LOGGER.error("加入购物车失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "未知错误");
        }
    }

    /**
     * 立即支付
     * @param request
     * @param goodId
     * @param number
     * @return
     */
    @RequestMapping("/quickPay")
    private ModelAndView quickPay(HttpServletRequest request, Integer goodId, Integer number){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new ModelAndView("redirect:/toLogin");
        }
        //开始加入购物车
        Integer itemId = shopCartService.saveItem2(user.getUserId(), goodId, number);
        return new ModelAndView("redirect:/pay2?itemIdStr=" + itemId);
    }

    /**
     * 改变商品数量的时候修改价格
     * @param request
     * @param goodId
     * @param number
     * @return
     */
    @PostMapping("/changeNumber")
    private @ResponseBody JsonResult<ItemMoney> changeNumber(HttpServletRequest request, Integer goodId, String number){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new JsonResult(400, "用户没有登录");
        }
        try {
            LOGGER.info("开始加入购物车");
            ItemMoney itemMoney = shopCartService.changeNumber(user.getUserId(), goodId, number);
            LOGGER.info("加入购物车成功");
            return new JsonResult(200, "加入成功", itemMoney);
        }catch (Exception e){
            LOGGER.error("加入购物车失败，发生未知错误:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "未知错误");
        }
    }
    /**
     * 删除条目
     * @param request
     * @param itemId
     * @return
     */
    @PostMapping("/deleteItem")
    private @ResponseBody JsonResult deleteItem(HttpServletRequest request, Integer itemId){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult(400, "用户没有登录");
        }
        try {
            LOGGER.info("开始删除条目：{}", itemId);
            shopCartService.deleteItem(itemId, user.getUsername());
            LOGGER.info("删除成功");
            return new JsonResult(200, "删除成功");
        }catch (Exception e){
            LOGGER.error("删除条目失败，发生异常：{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500, "发生异常");
        }
    }

}

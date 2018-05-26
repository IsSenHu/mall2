package com.husen.mall2.controller;

import com.husen.mall2.model.User;
import com.husen.mall2.service.BillDetailService;
import com.husen.mall2.util.Page;
import com.husen.mall2.vo.BillDetailVO;
import com.husen.mall2.vo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author husen
 */
@Controller
public class BillDetailController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BillDetailController.class);
    @Autowired
    private BillDetailService billDetailService;

    /**
     * 跳转到账单详情页面
     * @param request
     * @return
     */
    @RequestMapping("/billDetail")
    private ModelAndView billDetail(HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            LOGGER.info("用户没有登录");
            return new ModelAndView("redirect:/toLogin");
        }
        return new ModelAndView("person/billlist");
    }

    /**
     * 获取分页查询账单详情的json数据
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/billDetails/{currentPage}/{pageSize}")
    private @ResponseBody JsonResult<Page<BillDetailVO>> billDetails(@PathVariable(name = "currentPage", required = false) Integer currentPage,
                                                                     @PathVariable(name = "pageSize", required = false) Integer pageSize,
                                                                     HttpServletRequest request){
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            return new JsonResult<>(400, "noLogin");
        }
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 12 : pageSize;
        try {
            Page<BillDetailVO> page = billDetailService.billDetails(currentPage, pageSize, user.getUserId());
            LOGGER.info("查询第" + currentPage + "页" + "每页" + pageSize + "条数据成功:{}", page);
            return new JsonResult<>(200, "成功", page);
        }catch (Exception e){
            LOGGER.error("分页查询账单详情失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult<>(500, "未知错误");
        }
    }
}

package com.husen.mall2.controller;

import com.husen.mall2.service.LogisticsService;
import com.husen.mall2.vo.LogisticsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author husen
 */
@Controller
public class LogisticsController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogisticsController.class);
    @Autowired
    private LogisticsService logisticsService;
    /**
     * 物流跟踪页面
     * @return
     */
    @RequestMapping("/logistics/{orderId}")
    private ModelAndView logistics(@PathVariable(name = "orderId") String orderId){
        Integer orderIdInt = Integer.valueOf(new String(Base64Utils.decodeFromUrlSafeString(orderId)));
        LOGGER.info("接收到查询物流信息的订单编号为:{}", orderIdInt);
        try {
            LogisticsVO vo = logisticsService.logistics(orderIdInt);
            LOGGER.info("查询物流信息成功:{}", vo);
            return new ModelAndView("person/logistics")
                    .addObject("vo", vo);
        }catch (Exception e){
            LOGGER.error("查询物流信息失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new ModelAndView("home/error");
        }
    }
}

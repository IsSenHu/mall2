package com.husen.mall2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author husen
 */
@Controller
public class WalletlistController {

    /**
     * 钱包明细，主要是分页功能(钱包对应余额)
     * @return
     */
    @RequestMapping("/walletlist")
    private ModelAndView walletlist(){
        return new ModelAndView("person/walletlist");
    }

    /**
     * 账户余额
     * @return
     */
    @RequestMapping("/wallet")
    private ModelAndView wallet(){
        return new ModelAndView("person/wallet");
    }
}

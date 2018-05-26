package com.husen.mall2.service;

import com.husen.mall2.vo.GoodIntroductionVO;

import java.util.List;

/**
 * @author husen
 */
public interface IntroductionService {
    GoodIntroductionVO introduction(Integer goodId);


    List<Integer> coupons(Integer goodId);
}

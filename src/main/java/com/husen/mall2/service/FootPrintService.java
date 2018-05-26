package com.husen.mall2.service;

import com.husen.mall2.model.FootPrint;
import com.husen.mall2.util.Page;
import com.husen.mall2.vo.FootVO;
import com.husen.mall2.vo.GoodVO;

import java.util.List;

/**
 * @author husen
 */
public interface FootPrintService {
    void save(FootPrint footPrint);
    List<GoodVO> guestYouLike(Integer userId);
    List<GoodVO> guestYouLike();

    void add(FootPrint footPrint);

    FootVO findAllByUserId(Integer userId);

    void delete(Integer footId, Integer userId);

}

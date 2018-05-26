package com.husen.mall2.service;

import com.husen.mall2.model.Refund;
import com.husen.mall2.vo.JsonResult;
import com.husen.mall2.vo.OrderVO;
import com.husen.mall2.vo.RefundVO;

import java.util.List;

/**
 * @author husen
 */
public interface RefundService {
    RefundVO createRefund(Integer orderId, Integer itemId, Integer userId);

    JsonResult saveRefund(Integer userId, Refund refund);

    JsonResult cancelRefund(Integer refundId);

    List<OrderVO> findAllByStatu(Integer statu);
}

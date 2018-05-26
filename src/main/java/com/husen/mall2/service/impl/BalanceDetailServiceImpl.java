package com.husen.mall2.service.impl;

import com.husen.mall2.model.BalanceDetail;
import com.husen.mall2.repository.BalanceDetailRepository;
import com.husen.mall2.service.BalanceDetailService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 12:29 2018/5/19
 * @Modified By:
 */
@Service
public class BalanceDetailServiceImpl implements BalanceDetailService {
    @Autowired
    private BalanceDetailRepository balanceDetailRepository;

    @Override
    public Double getBalance(Integer userId) {
        Pageable pageable = new PageRequest(0, 1, new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        List<BalanceDetail> balanceDetails = balanceDetailRepository.findAllByUser_UserId(userId, pageable);
        if(CollectionUtils.isNotEmpty(balanceDetails)){
            return balanceDetails.get(0).getBalanceMoney();
        }else {
            return 0.00;
        }
    }
}

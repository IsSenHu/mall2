package com.husen.mall2.service.impl;

import com.husen.mall2.model.BillDetail;
import com.husen.mall2.repository.BillDetailRepository;
import com.husen.mall2.service.BillDetailService;
import com.husen.mall2.util.Page;
import com.husen.mall2.vo.BillDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author husen
 */
@Service
@Transactional
public class BillDetailServiceImpl implements BillDetailService  {
    private final static Logger LOGGER = LoggerFactory.getLogger(BillDetailServiceImpl.class);
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public Page<BillDetailVO> billDetails(Integer currentPage, Integer pageSize, Integer userId) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "dealTime");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, sort);
        org.springframework.data.domain.Page<BillDetail> page = billDetailRepository.findAllByUser_UserId(userId, pageable);
        page.getContent().stream().forEach(x -> LOGGER.info("查询到的一条账单详情是:{}", x));
        Page<BillDetailVO> voPage = new Page<>();
        List<BillDetailVO> voList = new ArrayList<>();
        page.getContent().stream().forEach(x -> {
            BillDetailVO billDetailVO = new BillDetailVO(x.getBdId(), x.getPic(), x.getDealTime(), x.getGoodName(), x.getExpenditure(),x.getBankName());
            voList.add(billDetailVO);
        });
        voPage.setContent(voList);
        voPage.setCurrentPage(page.getNumber() + 1);
        voPage.setTotalPage(page.getTotalPages());
        voPage.setPageSize(page.getSize());
        voPage.setRowsTotal(page.getTotalElements());
        return voPage;
    }
}

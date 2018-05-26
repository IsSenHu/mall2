package com.husen.mall2.service;

import com.husen.mall2.util.Page;
import com.husen.mall2.vo.BillDetailVO;

/**
 * @author husen
 */
public interface BillDetailService {
    Page<BillDetailVO> billDetails(Integer currentPage, Integer pageSize, Integer userId);
}

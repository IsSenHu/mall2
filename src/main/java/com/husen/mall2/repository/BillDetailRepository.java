package com.husen.mall2.repository;

import com.husen.mall2.model.BillDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
    Page<BillDetail> findAllByUser_UserId(Integer userId, Pageable pageable);
    List<BillDetail> findAllByOrder_OrderId(Integer orderId);
}

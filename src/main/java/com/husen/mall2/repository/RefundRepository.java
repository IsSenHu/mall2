package com.husen.mall2.repository;

import com.husen.mall2.model.Refund;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Integer> {
    Integer countByItemId(Integer itemId);
    Refund findByItemId(Integer itemId);
    List<Refund> findAllByStatu(Integer statu, Sort sort);
}

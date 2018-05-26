package com.husen.mall2.repository;

import com.husen.mall2.model.BalanceDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 12:29 2018/5/19
 * @Modified By:
 */
@Repository
public interface BalanceDetailRepository extends JpaRepository<BalanceDetail, Integer> {

    List<BalanceDetail> findAllByUser_UserId(Integer userId, Pageable pageable);
}

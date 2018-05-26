package com.husen.mall2.repository;

import com.husen.mall2.model.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 11:30 2018/5/20
 * @Modified By:
 */
@Repository
public interface LogisticsRepository extends JpaRepository <Logistics, Integer> {
}

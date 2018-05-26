package com.husen.mall2.repository;

import com.husen.mall2.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    List<Coupon> findAllByGood_GoodId(Integer goodId);
    List<Coupon> findAllByIdIn(List<Integer> ids);
}

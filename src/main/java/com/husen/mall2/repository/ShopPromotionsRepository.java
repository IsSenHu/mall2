package com.husen.mall2.repository;

import com.husen.mall2.model.ShopPromotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopPromotionsRepository extends JpaRepository<ShopPromotions, Integer> {
    List<ShopPromotions> findAllByGood_GoodId(Integer goodId);
}

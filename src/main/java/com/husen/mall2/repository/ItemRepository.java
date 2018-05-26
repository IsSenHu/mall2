package com.husen.mall2.repository;

import com.husen.mall2.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findByUserIdAndGood_GoodIdAndStatu(Integer userId, Integer goodId, Integer statu);

    List<Item> findAllByItemIdIn(List<Integer> itemIdList);

    List<Item> findAllByOrder_OrderIdAndStatu(Integer orderId, Integer statu);

    List<Item> findAllByStatuAndUserId(Integer statu, Integer userId);
}

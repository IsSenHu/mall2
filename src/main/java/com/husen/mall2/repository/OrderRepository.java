package com.husen.mall2.repository;

import com.husen.mall2.model.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByStatuAndDelAndUser_UserId(Integer statu, String del, Integer userId, Sort sort);
    List<Order> findAllByDelAndUser_UserId(String del, Integer userId, Sort sort);

    List<Order> findAllByStatu(Integer value);
}

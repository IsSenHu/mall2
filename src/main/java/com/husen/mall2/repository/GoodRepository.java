package com.husen.mall2.repository;

import com.husen.mall2.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodRepository extends JpaRepository<Good, Integer> {
    List<Good> findAllByItemIdIn(List<Integer> ids);
    List<Good> findAllByGoodIdIn(List<Integer> ids);
}

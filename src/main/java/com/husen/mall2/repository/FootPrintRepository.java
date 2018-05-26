package com.husen.mall2.repository;

import com.husen.mall2.model.FootPrint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FootPrintRepository extends JpaRepository<FootPrint, Integer> {
    @Query(value = "select f.footPrintId from FootPrint f where user.userId = ?1 group by good.goodId", nativeQuery = true)
    List<Integer> countAllByUserIdGroupByGoodId(Integer userId);
}

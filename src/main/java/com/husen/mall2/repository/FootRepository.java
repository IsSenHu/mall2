package com.husen.mall2.repository;

import com.husen.mall2.model.Foot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FootRepository extends JpaRepository<Foot, Integer> {
    List<Foot> findAllByUserIdAndFootDateEquals(Integer userId, Date today);
    List<Foot> findAllByUserIdAndFootDateGreaterThanEqualAndFootDateLessThanEqual(Integer userId, Date start, Date end);
}

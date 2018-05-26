package com.husen.mall2.repository;

import com.husen.mall2.model.Areas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreasRepository extends JpaRepository<Areas, Integer> {
    List<Areas> findAllByCityid(String cityid);
    Areas findByAreaid(String areaid);
}

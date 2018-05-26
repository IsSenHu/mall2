package com.husen.mall2.repository;

import com.husen.mall2.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesRepository extends JpaRepository<Cities, Integer> {
    List<Cities> findAllByProvinceid(String provinceid);

    Cities findByCityid(String cityid);
}

package com.husen.mall2.repository;

import com.husen.mall2.model.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, Integer> {
    Provinces findByProvinceid(String provinceid);
}

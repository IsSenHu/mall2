package com.husen.mall2.repository;

import com.husen.mall2.model.LogisticsRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticsRecordRepository extends JpaRepository<LogisticsRecord, Integer> {
    List<LogisticsRecord> findAllByLogistics_Id(Integer id, Sort sort);
    List<LogisticsRecord> findAllByLogistics_Id(Integer id, Pageable pageable);
    List<LogisticsRecord> findAllByLogistics_Id(Integer id);
}

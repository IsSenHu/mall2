package com.husen.mall2.repository;

import com.husen.mall2.model.SafeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafeQuestionRepository extends JpaRepository<SafeQuestion, Integer> {
    void deleteAllByUser_UserId(Integer userId);
    Integer countByUser_UserId(Integer userId);
}

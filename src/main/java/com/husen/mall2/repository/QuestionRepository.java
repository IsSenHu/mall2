package com.husen.mall2.repository;

import com.husen.mall2.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author husen
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}

package com.husen.mall2.repository;

import com.husen.mall2.model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<BankCard, Integer> {
    Integer countByBankCardId(String bankCardId);

    List<BankCard> findAllByUser_UserId(Integer userId);
}

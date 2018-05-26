package com.husen.mall2.service;

import com.husen.mall2.model.BankCard;
import com.husen.mall2.vo.BankVO;

import java.util.List;

/**
 * @author husen
 */
public interface BankService {
    boolean addBankCard(BankVO bank, Integer userId);

     List<BankCard> findAllByUserId(Integer userId);
 }

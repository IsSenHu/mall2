package com.husen.mall2.service.impl;

import com.husen.mall2.controller.CardlistController;
import com.husen.mall2.model.BankCard;
import com.husen.mall2.repository.BankRepository;
import com.husen.mall2.repository.UserRepository;
import com.husen.mall2.service.BankService;
import com.husen.mall2.vo.BankVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author husen
 */
@Service
@Transactional
public class BankServiceImpl implements BankService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CardlistController.class);
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean addBankCard(BankVO bank, Integer userId) {
        /**
         * 先检查该银行卡号是否已经注册
         * */
        if(bankRepository.countByBankCardId(bank.getBankCardId()) > 0){
            LOGGER.info("该银行卡号已存在，不能重复添加:{}", bank.getBankCardId());
            return false;
        }
        /**
         * 开始添加
         * */
        BankCard bankCard  = new BankCard();
        bankCard.setUser(userRepository.findById(userId).get());
        bankCard.setRealName(bank.getRealName());
        bankCard.setPhone(bank.getPhone());
        bankCard.setBankCardId(bank.getBankCardId());
        bankCard.setIdCardId(bank.getCardId());
        bankCard.setBankName(randomBankName());
        bankRepository.save(bankCard);
        return true;
    }

    @Override
    public List<BankCard> findAllByUserId(Integer userId) {
        return bankRepository.findAllByUser_UserId(userId);
    }

    /**
     * 随机生成一个银行的名称
     * @return
     */
    private String randomBankName(){
        String[] banks = {"中国建设银行", "中国工商银行", "中国农业银行", "中国招商银行"};
        List<String> bankNames = Arrays.asList(banks);
        Random random = new Random();
        int index = random.nextInt(bankNames.size());
        return bankNames.get(index);
    }
}

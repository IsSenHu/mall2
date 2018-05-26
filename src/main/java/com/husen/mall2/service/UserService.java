package com.husen.mall2.service;

import com.husen.mall2.model.Question;
import com.husen.mall2.model.User;
import com.husen.mall2.vo.*;
import java.util.List;


/**
 * @author husen
 */
public interface UserService {
    boolean registerForEmail(RegisterForEmail forEmail) throws Exception;

    Integer checkPhone(String phone);

    void registerForPhone(RegisterForPhone forPhone) throws Exception;

    User login(String username, String password);

    UserVO findById(Integer userId);

    void update(UserVO vo);

    boolean checkNickName(Integer userId, String nickName);

    boolean checkPhone(Integer userId, String phone);

    boolean checkEmail(Integer userId, String email);

    User findByIdPO(Integer userId);

    boolean checkOldPassword(String oldPassword, Integer userId);

    void updatePassword(Integer userId, String newPassword);

    void resetpay(Integer userId, String newPassword);

    void bingdingPhone(Integer userId, String newPhone);

    Integer checkEmail(String email);

    void bindEmail(Integer userId, String email);

    List<Question> getThreeQuestion();

    void setSafeQuestion(Integer userId, Questions questions);

    Integer safeScore(Integer userId);

    int checkPayPassword(Integer userId, String password);
}

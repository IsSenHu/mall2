package com.husen.mall2.repository;

import com.husen.mall2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Integer countByEmail(String email);
    Integer countByPhone(String phone);
    User findByUsernameAndPassword(String username, String password);
    User findByEmailAndPassword(String email, String password);
    User findByPhoneAndPassword(String phone, String password);
    Integer countByNickNameAndUserIdNot(String nickName, Integer userId);
    Integer countByPhoneAndUserIdNot(String phone, Integer userId);
    Integer countByEmailAndUserIdNot(String email, Integer userId);
}

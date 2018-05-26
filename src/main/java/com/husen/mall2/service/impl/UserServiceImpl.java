package com.husen.mall2.service.impl;

import com.husen.mall2.enums.UserStatu;
import com.husen.mall2.model.Question;
import com.husen.mall2.model.SafeQuestion;
import com.husen.mall2.model.User;
import com.husen.mall2.repository.QuestionRepository;
import com.husen.mall2.repository.SafeQuestionRepository;
import com.husen.mall2.repository.UserRepository;
import com.husen.mall2.service.UserService;
import com.husen.mall2.util.DateUtil;
import com.husen.mall2.vo.Questions;
import com.husen.mall2.vo.RegisterForEmail;
import com.husen.mall2.vo.RegisterForPhone;
import com.husen.mall2.vo.UserVO;
import ecjtu.husen.pojo.DTO.Gender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author husen
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SafeQuestionRepository safeQuestionRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public boolean registerForEmail(RegisterForEmail forEmail) throws Exception {
        if(userRepository.countByEmail(forEmail.getEmail()) > 0){
            return false;
        }else {
            /**
             * 注册并初始化用户信息
             * 1，用户名默认为邮箱
             * 2，昵称按一定规则生成默认的
             * 3，没有实名认证
             * 4，可用余额为0
             * 5，用户状态启用
             * 6，性别默认为未知性别
             * */
            User newUser = userRepository.save(transformUser(forEmail));
            if(newUser != null){
                LOGGER.info("注册成功：{}", newUser.toString());
                return true;
            }else {
                throw new Exception("插入数据库失败");
            }
        }
    }

    @Override
    public int checkPayPassword(Integer userId, String password) {
        String payPassword = userRepository.findById(userId).get().getPayPassword();
        if(StringUtils.isBlank(payPassword)){
            return 3;
        }else {
            if(payPassword.equals(password)){
                return 1;
            }else {
                return 2;
            }
        }
    }

    @Override
    public Integer checkPhone(String phone) {
        return userRepository.countByPhone(phone);
    }

    @Override
    public void registerForPhone(RegisterForPhone forPhone) throws Exception {
        /**
         * 注册并初始化用户信息
         * 1，用户名默认为手机号
         * 2，昵称按一定规则生成默认的
         * 3，没有实名认证
         * 4，可用余额为0
         * 5，用户状态启用
         * 6，性别默认为未知性别
         * */
        User newUser = userRepository.save(transformUserForPhone(forPhone));
        if(newUser == null){
            throw new Exception("插入数据库失败");
        }else {
            LOGGER.info("注册成功：{}", newUser.toString());
        }
    }

    @Override
    public User login(String username, String password) {
        User user;
        if((user = userRepository.findByUsernameAndPassword(username, password)) != null){
            return user;
        }else if((user = userRepository.findByEmailAndPassword(username, password)) != null){
            return user;
        }else {
            return userRepository.findByPhoneAndPassword(username, password);
        }
    }

    @Override
    public UserVO findById(Integer userId) {
        User user = userRepository.findById(userId).get();
        UserVO vo = new UserVO();
        vo.setUserId(userId);
        vo.setUsername(user.getUsername());
        vo.setNickName(user.getNickName());
        vo.setRealName(user.getName());
        vo.setGender(user.getGender().getValue());
        vo.setPhone(user.getPhone());
        vo.setEmail(user.getEmail());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(user.getBirthday());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //获取当月有多少天
        int maxDay = DateUtil.getCurrentMonthLastDay(year, month);
        //月有12月，年从1970年开始到2200年为止;
        StringBuilder yearHtml = new StringBuilder("");
        for(int i = 1970; i <= 2200; i++){
            if(i == year){
                yearHtml.append("<option selected value=\"" + i + "\">" + i + "</option>");
            }else {
                yearHtml.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }
        StringBuilder monthHtml = new StringBuilder("");
        for(int i = 1; i <= 12; i++){
            if(i == month){
                monthHtml.append("<option selected value=\"" + i + "\">" + i + "</option>");
            }else {
                monthHtml.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }
        StringBuilder dayHtml = new StringBuilder("");
        for(int i = 1; i <= maxDay; i++){
            if(i == day){
                dayHtml.append("<option selected value=\"" + i + "\">" + i + "</option>");
            }else {
                dayHtml.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }
        vo.setDay(dayHtml.toString());
        vo.setMonth(monthHtml.toString());
        vo.setYear(yearHtml.toString());
        vo.setPic(user.getPic());
        return vo;
    }

    @Override
    public void update(UserVO vo) {
        User user = userRepository.findById(vo.getUserId()).get();
        user.setPhone(vo.getPhone());
        user.setEmail(vo.getEmail());
        user.setNickName(vo.getNickName());
        user.setName(vo.getRealName());
        user.setPic(vo.getPic());
        LocalDateTime time = LocalDateTime.of(Integer.valueOf(vo.getYear()), Integer.valueOf(vo.getMonth()), Integer.valueOf(vo.getDay()), 0, 0);
        user.setBirthday(DateUtil.LocalDateTimeForDate(time));
        Arrays.stream(Gender.values()).forEach(x -> {
            if(x.getValue().equals(vo.getGender())){
                user.setGender(x);
                return;
            }
        });
        userRepository.save(user);
    }

    @Override
    public boolean checkNickName(Integer userId, String nickName) {
        return userRepository.countByNickNameAndUserIdNot(nickName, userId) > 0 ? false : true;
    }

    @Override
    public boolean checkPhone(Integer userId, String phone) {
        return userRepository.countByPhoneAndUserIdNot(phone, userId) > 0 ? false : true;
    }

    @Override
    public boolean checkEmail(Integer userId, String email) {
        return userRepository.countByEmailAndUserIdNot(email, userId) > 0 ? false : true;
    }

    @Override
    public User findByIdPO(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public boolean checkOldPassword(String oldPassword, Integer userId) {
        User user = userRepository.findById(userId).get();
        if(user.getPassword().equals(oldPassword)){
            return true;
        }
        return false;
    }

    @Override
    public void updatePassword(Integer userId, String newPassword) {
        User user = userRepository.findById(userId).get();
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void resetpay(Integer userId, String newPassword) {
        User user = userRepository.findById(userId).get();
        user.setPayPassword(newPassword);
        userRepository.save(user);
    }

    /**
     * 转化vo为po和初始化用户信息
     * @param forEmail
     * @return
     */
    private User transformUser(RegisterForEmail forEmail){
        User user = new User();
        user.setUsername(forEmail.getEmail());
        user.setNickName("adw" + UUID.randomUUID().toString().hashCode());
        user.setStatu(UserStatu.ACCESS.getValue());
        user.setAvailableBalance(0.00);
        user.setIsVerified(UserStatu.REALNAME_NO_AUTHENTICATE.getValue());
        user.setPassword(forEmail.getPassword());
        user.setEmail(forEmail.getEmail());
        user.setGender(Gender.unknown);
        user.setBirthday(new Date());
        return user;
    }

    /**
     * 转化vo为po和初始化用户信息
     * @param forPhone
     * @return
     */
    private User transformUserForPhone(RegisterForPhone forPhone){
        User user = new User();
        user.setUsername(forPhone.getPhone());
        user.setNickName("adw" + UUID.randomUUID().toString().hashCode());
        user.setStatu(UserStatu.ACCESS.getValue());
        user.setAvailableBalance(0.00);
        user.setIsVerified(UserStatu.REALNAME_NO_AUTHENTICATE.getValue());
        user.setPassword(forPhone.getPassword());
        user.setGender(Gender.unknown);
        user.setPhone(forPhone.getPhone());
        user.setBirthday(new Date());
        return user;
    }

    @Override
    public void bingdingPhone(Integer userId, String newPhone) {
        User user = userRepository.findById(userId).get();
        user.setPhone(newPhone);
        userRepository.save(user);
    }

    @Override
    public Integer checkEmail(String email) {
        return userRepository.countByEmail(email);
    }

    @Override
    public void bindEmail(Integer userId, String email) {
        User user = userRepository.findById(userId).get();
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    public List<Question> getThreeQuestion() {
        List<Question> questions = questionRepository.findAll();
        if(questions.size() <= 3){
            return questions;
        }else {
            Random random = new Random();
            List<Integer> list = new ArrayList<>();
            List<Question> questionList = new ArrayList<>();
            while (true){
                if(questionList.size() >= 3){
                    return questionList;
                }
                int x  = random.nextInt(questions.size());
                if(list.contains(x)){
                    continue;
                }
                questionList.add(questions.get(x));
                list.add(x);
            }
        }
    }

    @Override
    public void setSafeQuestion(Integer userId, Questions questions) {
        safeQuestionRepository.deleteAllByUser_UserId(userId);
        SafeQuestion firstSafeQuestion = new SafeQuestion();
        User user = userRepository.findById(userId).get();
        firstSafeQuestion.setUser(user);
        firstSafeQuestion.setAnswer(questions.getAnswer1());
        firstSafeQuestion.setQuestion(questionRepository.findById(questions.getQ1()).get().getQuestion());

        SafeQuestion secondtSafeQuestion = new SafeQuestion();
        secondtSafeQuestion.setUser(user);
        secondtSafeQuestion.setAnswer(questions.getAnswer2());
        secondtSafeQuestion.setQuestion(questionRepository.findById(questions.getQ2()).get().getQuestion());

        SafeQuestion threetSafeQuestion = new SafeQuestion();
        threetSafeQuestion.setUser(user);
        threetSafeQuestion.setAnswer(questions.getAnswer3());
        threetSafeQuestion.setQuestion(questionRepository.findById(questions.getQ3()).get().getQuestion());

        safeQuestionRepository.save(firstSafeQuestion);
        safeQuestionRepository.save(secondtSafeQuestion);
        safeQuestionRepository.save(threetSafeQuestion);
    }

    @Override
    public Integer safeScore(Integer userId) {
        User user = userRepository.findById(userId).get();
        int score = 0;
        if(StringUtils.isNotBlank(user.getPassword())){
            score += 20;
        }
        if(StringUtils.isNotBlank(user.getPayPassword())){
            score += 20;
        }
        if(StringUtils.isNotBlank(user.getPhone())){
            score += 20;
        }
        if(StringUtils.isNotBlank(user.getEmail())){
            score += 20;
        }
        if(safeQuestionRepository.countByUser_UserId(userId) > 0){
            score += 20;
        }
        return score;
    }
}

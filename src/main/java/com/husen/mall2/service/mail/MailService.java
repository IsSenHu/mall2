package com.husen.mall2.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender sender;

    /**
     * 发送邮件的方法
     * @param to
     * @param title
     * @param content
     */
    public void sendSimple(String to, String title, String content) throws MessagingException {
        LOGGER.info("调用发送邮件的方法");
        final MimeMessage mimeMessage = sender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        LOGGER.info("开始发生邮件，接收邮箱是：{}", to);
        sender.send(mimeMessage);
        LOGGER.info("邮件发送成功，接收的邮箱是:{}", to);
    }
}

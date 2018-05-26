package com.husen.mall2.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Configuration
public class Sender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            logger.info("消息发送成功：{}", correlationData.toString());
        }else {
            logger.info("消息发送失败：{}", correlationData.toString());
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String queueName) {
        logger.info("发送失败：{}", message.getMessageProperties().getCorrelationIdString());
    }


    /**
     * 发送消息
     * @param message
     */
    public void send(String message, String exchange, String key, String queue){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("开始发送消息：{}", message.toLowerCase());
        String response = (String) rabbitTemplate.convertSendAndReceive(exchange, key, message, correlationData);
        logger.info("结束消息发送：{}", message.toLowerCase());
        logger.info("消费者响应：{}", response + "消息处理完成");
    }
}

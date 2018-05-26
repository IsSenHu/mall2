package com.husen.mall2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *rabbitmq配置
 * @author husen
 */
@Configuration
public class AmqpConfig {
    public final static String CREATE_ORDER_QUEUE = "createOrderQueue";
    public final static String TOPIC_EXCHANGE_NAME = "topicExchangeName";
    /**
     * 配置队列
     * */
    @Bean
    public Queue createOrderQueue(){
        return new Queue(CREATE_ORDER_QUEUE);
    }

    /**
     * 配置主题交换器
     * */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }
    /**
     * 绑定
     * */
    @Bean
    public Binding createOrderBind(){
        return BindingBuilder.bind(createOrderQueue()).to(topicExchange()).with(CREATE_ORDER_QUEUE);
    }
}

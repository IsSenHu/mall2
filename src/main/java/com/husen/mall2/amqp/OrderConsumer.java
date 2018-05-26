package com.husen.mall2.amqp;

import com.alibaba.fastjson.JSONObject;
import com.husen.mall2.config.AmqpConfig;
import com.husen.mall2.service.OrderService;
import com.husen.mall2.vo.CreateOrder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 接收生成订单的消息
 * @author husen
 */
@Component
public class OrderConsumer {
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 接收创建订单的消息
     * @param message
     * @return
     */
    @RabbitListener(queues = AmqpConfig.CREATE_ORDER_QUEUE)
    public String receiveMessage(String message){
        logger.info("接收到的订单生成信息为:{}", message);
        if(StringUtils.isNotBlank(message)){
            taskExecutor.execute(() -> createOrder(message));
        }
        return message;
    }

    /**
     * 创建订单
     * @param message
     */
    private void createOrder(String message){
        CreateOrder createOrder = JSONObject.parseObject(message, CreateOrder.class);
        logger.info("开始处理的数据是:{}", createOrder);
        orderService.create(createOrder);
    }
}

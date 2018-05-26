package com.husen.mall2.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 订单id生成器
 * @author husen
 */
@Component
public class OrderIdProduct {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 生成唯一的订单号
     * @return
     */
    public String productOrderId(){
        ValueOperations<String, String > operations = redisTemplate.opsForValue();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        int day = localDate.getDayOfMonth();
        if(operations.get("orderId#" + year + day + month) == null){
            StringBuilder orderId = new StringBuilder();
            orderId.append(year).append(month < 10 ? "0" + month : month).append(day < 10 ? "0" + day : day).append(13).append(1000001);
            operations.set("orderId#" + year + day + month, orderId.toString());
            return orderId.toString();
        }else {
            operations.increment("orderId#" + year + day + month, 1);
            return operations.get("orderId#" + year + day + month);
        }
    }
}

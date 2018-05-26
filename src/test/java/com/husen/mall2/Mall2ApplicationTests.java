package com.husen.mall2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Mall2ApplicationTests {
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Test
	public void contextLoads() {
		ValueOperations<String, String > operations = redisTemplate.opsForValue();
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonth().getValue();
		int day = localDate.getDayOfMonth();

		if(operations.get("orderId_" + year + day + month) == null){
			StringBuilder orderId = new StringBuilder();
			orderId.append(year).append(month < 10 ? "0" + month : month).append(day < 10 ? "0" + day : day).append(13).append(1000001);
			operations.set("orderId_" + year + day + month, orderId.toString());
			System.out.println(orderId);
		}else {
			operations.increment("orderId_" + year + day + month, 1);
			System.out.println(operations.get("orderId_" + year + day + month));
		}
	}

}

package com.yinan.iloubackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Resource
    RedisTemplate redisTemplate;

    @Test
    void redisTest(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String key = "yinan";
        valueOperations.set(key, "lou");

        String o = (String) valueOperations.get(key);
        System.out.println(o);
    }
}

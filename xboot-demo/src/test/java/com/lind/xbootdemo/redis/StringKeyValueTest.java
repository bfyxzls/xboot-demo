package com.lind.xbootdemo.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class StringKeyValueTest {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void getSetStr() {
        redisTemplate.opsForValue().set("author", "zzl");
        Assert.assertEquals("zzl", redisTemplate.opsForValue().get("author"));
    }
}

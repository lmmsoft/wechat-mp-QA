package com.github.binarywang.demo.wx.mp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        stringRedisTemplate.opsForValue().set("MyKey", "MyValue");
        Assert.assertEquals("MyValue", stringRedisTemplate.opsForValue().get("MyKey"));

        //Also, test in redis-cli: get "MyKey"  -> MyValue
    }
}

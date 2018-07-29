package com.github.binarywang.demo.wx.mp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void test() {
        stringRedisTemplate.opsForValue().set("MyKey", "MyValue");
        Assert.assertEquals("MyValue", stringRedisTemplate.opsForValue().get("MyKey"));

        //Also, test in redis-cli: get "MyKey"  -> MyValue
    }

    @Test
    public void testObj() throws Exception {
        User user = new User(1, "aa", "aa@126.com");
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        operations.set("userKey", user);
        operations.set("userKey.timeout", user, 1, TimeUnit.SECONDS);

        Assert.assertEquals("aa", ((User) operations.get("userKey")).name);
        Assert.assertEquals("aa", ((User) operations.get("userKey.timeout")).name);

        Thread.sleep(1000);
        //redisTemplate.delete("userKey.timeout");

        boolean exists = redisTemplate.hasKey("userKey.timeout");
        Assert.assertFalse(exists);
    }
}

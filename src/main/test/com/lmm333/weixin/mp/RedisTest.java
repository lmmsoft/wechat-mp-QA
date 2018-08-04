package com.lmm333.weixin.mp;

import com.lmm333.weixin.mp.model.HelloWorldModel;

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
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        stringRedisTemplate.opsForValue().set("MyKey", "MyValue");
        Assert.assertEquals("MyValue", stringRedisTemplate.opsForValue().get("MyKey"));

        //Also, test in redis-cli: get "MyKey"  -> MyValue
    }

    @Test
    public void testObj() throws Exception {
        HelloWorldModel helloWorldModel = new HelloWorldModel(1, "aa", 81.8f);
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("userKey", helloWorldModel);
        operations.set("userKey.timeout", helloWorldModel, 1, TimeUnit.SECONDS);

        Assert.assertEquals("aa", ((HelloWorldModel) operations.get("userKey")).getName());
        Assert.assertEquals("aa", ((HelloWorldModel) operations.get("userKey.timeout")).getName());

        Thread.sleep(1000);
        //redisTemplate.delete("userKey.timeout");

        boolean exists = redisTemplate.hasKey("userKey.timeout");
        Assert.assertFalse(exists);
    }
}

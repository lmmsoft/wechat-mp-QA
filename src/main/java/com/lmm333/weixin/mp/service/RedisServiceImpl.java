package com.lmm333.weixin.mp.service;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate; //stringRedisTemplate只能缓存key-value的String类型

    @Override
    public void set(String key, Object value) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    @Override
    public Object get(String key) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    public void setList(String key, Collection<Object> collection) {
        ListOperations<String, Object> valueOperations = redisTemplate.opsForList();
        valueOperations.rightPushAll(key, collection);
    }
}
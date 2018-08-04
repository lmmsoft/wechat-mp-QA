package com.lmm333.weixin.mp.service;

public interface RedisService {

    void set(String key, Object value);

    Object get(String key);
}

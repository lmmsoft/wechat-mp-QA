package com.github.binarywang.demo.wx.mp.service;

public interface RedisService {

    void set(String key, Object value);

    Object get(String key);
}

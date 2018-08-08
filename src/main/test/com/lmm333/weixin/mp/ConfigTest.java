package com.lmm333.weixin.mp;

import com.lmm333.weixin.mp.config.WechatMpProperties;
import com.lmm333.weixin.mp.config.WxGzh1Config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigTest {
    @Autowired
    WechatMpProperties wechatMpProperties;

    @Autowired
    WxGzh1Config config;

    @Test
    public void WechatMpProperties() {
        Assert.assertTrue(wechatMpProperties.getAppId().startsWith("wx"));
        Assert.assertTrue(config.getAppid().startsWith("wx"));
    }
}

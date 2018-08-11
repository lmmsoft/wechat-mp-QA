package com.lmm333.weixin.mp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Binary Wang
 */
@Configuration
public class WxGzh1Config extends WxConfig {

    @Value("${wx_appid}")
    private String appid;

    @Value("${wx_appsecret}")
    private String appsecret;

    @Value("${wx_oauth_callback_url}")
    private String oauthCallbackUrl;

    @Override
    public String getToken() {
        return "";
    }

    @Override
    public String getAppid() {
        return this.appid;
    }

    @Override
    public String getAppsecret() {
        return this.appsecret;
    }

    @Override
    public String getAesKey() {
        return "";
    }

    @Override
    public WxAccountEnum getWxAccountEnum() {
        return WxAccountEnum.GZH1;
    }

    public String getOauthCallbackUrl() {
        return oauthCallbackUrl;
    }
}

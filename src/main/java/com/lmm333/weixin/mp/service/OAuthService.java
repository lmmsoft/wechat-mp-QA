package com.lmm333.weixin.mp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

//Doc: https://github.com/Wechat-Group/weixin-java-tools/wiki/MP_OAuth2%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83
@Service
public class OAuthService {

    @Autowired
    private Gzh1WxService wxMpService;

    public String getOauthUrl(String userId) {
        String url = "http://www.gfitgo.com/lmm/";
        return wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, userId);
    }

    public String getOauthUrlText(String userId) {
        return String.format("请点击链接： <a href=\"%s\">报名参加抽奖</a> ", getOauthUrl(userId));
    }

    public WxMpOAuth2AccessToken getAccessToken(String code) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return wxMpOAuth2AccessToken;
    }

    public WxMpUser getWxMpUser(WxMpOAuth2AccessToken wxMpOAuth2AccessToken) {
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return wxMpUser;
    }
}

package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.dao.UserMapper;
import com.lmm333.weixin.mp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

//Doc: https://github.com/Wechat-Group/weixin-java-tools/wiki/MP_OAuth2%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83
@Service
public class OAuthService {

    @Autowired
    private Gzh1WxService wxMpService;

    @Resource
    private UserMapper userMapper;

    public String getOauthUrl(String userId) {
        String url = wxMpService.getOauthCallbackUrl();
        return wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, userId);
    }

    String getOauthUrlText(String userId) {
        return String.format("请点击链接： <a href=\"%s\">报名参加抽奖</a> ", getOauthUrl(userId));
    }

    public WxMpUser getWxMpUser(@RequestParam("code") String code, @RequestParam("state") String state) {
        //Step 1: Save code first
        User user = new User(state, User.TYPE_WECHAT_OAUTHED)
                .setCode(code);
        userMapper.replaceUserRegisterTypeByWechatUserId(user);

        //Step 2: get and save token info
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = getAccessToken(code);
        user.setAccess_token(wxMpOAuth2AccessToken.getAccessToken())
                .setRefresh_token(wxMpOAuth2AccessToken.getRefreshToken())
                .setUnionid(wxMpOAuth2AccessToken.getUnionId())
                .setOpenid(wxMpOAuth2AccessToken.getOpenId());
        userMapper.updateUser(user);

        //Step3: get and save user info
        WxMpUser wxMpUser = getWxMpUser(wxMpOAuth2AccessToken);
        user.setNickname(wxMpUser.getNickname())
                .setHeadimgurl(wxMpUser.getHeadImgUrl())
                .setSex(wxMpUser.getSex().toString())
                .setLanguage(wxMpUser.getLanguage())
                .setCity(wxMpUser.getCity())
                .setProvince(wxMpUser.getProvince())
                .setCountry(wxMpUser.getCountry());
        userMapper.updateUser(user);

        return wxMpUser;
    }

    private WxMpOAuth2AccessToken getAccessToken(String code) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return wxMpOAuth2AccessToken;
    }

    private WxMpUser getWxMpUser(WxMpOAuth2AccessToken wxMpOAuth2AccessToken) {
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return wxMpUser;
    }
}

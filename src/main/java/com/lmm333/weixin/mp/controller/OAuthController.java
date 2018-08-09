package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.service.OAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
public class OAuthController {

    @Autowired
    OAuthService oAuthService;


    @GetMapping("/url")
    @ResponseBody
    public String oAuthLandingPage() {
        return "<a href='" + oAuthService.getOauthUrl("") + "'>go</a>";
    }


    @GetMapping("/")
    public String oAuthLandingPage(@RequestParam("code") String code,
                                   @RequestParam("state") String state) {
        //TODO 如果注册失败，显示错误页面

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = oAuthService.getAccessToken(code);
        WxMpUser wxMpUser = oAuthService.getWxMpUser(wxMpOAuth2AccessToken);
        String imageUrl = wxMpUser.getHeadImgUrl();

        //save token and user info
        User user = new User(state, User.TYPE_WECHAT_OAUTHED)
                .setNickname(wxMpUser.getNickname())
                .setHeadimgurl(wxMpUser.getHeadImgUrl())
                .setSex(wxMpUser.getSex().toString())
                .setLanguage(wxMpUser.getLanguage())
                .setCity(wxMpUser.getCity())
                .setProvince(wxMpUser.getProvince())
                .setCountry(wxMpUser.getCountry())
                .setAccess_token(wxMpOAuth2AccessToken.getAccessToken())
                .setRefresh_token(wxMpOAuth2AccessToken.getRefreshToken())
                .setUnionid(wxMpOAuth2AccessToken.getUnionId())
                .setOpenid(wxMpOAuth2AccessToken.getOpenId())
                .setCode(code);

        //return page to user
        return wxMpUser.getNickname() + "恭喜你，注册成功~~~!";
    }
}

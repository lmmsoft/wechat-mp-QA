package com.lmm333.weixin.mp.controller;

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


    @GetMapping("/url2")
    public String oAuthLandingPage(@RequestParam("code") String code,
                                   @RequestParam("state") String state) {
        //如果注册失败，显示错误页面

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = oAuthService.getAccessToken(code);
        WxMpUser wxMpUser = oAuthService.getWxMpUser(wxMpOAuth2AccessToken);
        String userName = wxMpUser.getNickname();
        String imageUrl = wxMpUser.getHeadImgUrl();

        //save token and many info

        //return page to user
        return "注册成功";
    }
}

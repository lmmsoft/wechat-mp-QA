package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.service.OAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    public String oAuthLandingPage(@RequestParam("code") String code,
                                   @RequestParam("state") String state) {
        //TODO 如果注册失败，显示错误页面
        WxMpUser wxMpUser = oAuthService.getWxMpUser(code, state);

        //return page to user
        return wxMpUser.getNickname() + "恭喜你，抽奖注册成功~~~!\n您现在可以关闭网页，直接在公众号页面发送祝福并参与抽奖~";
    }
}

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

    @GetMapping("/")
    @ResponseBody
    public String oAuthLandingPage(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "state", required = false) String state) {
        if (code == null || state == null) {
            return "报名请点击: <a href='" + oAuthService.getOauthUrl("") + "'>go</a>";
        }

        String message;
        try {
            WxMpUser wxMpUser = oAuthService.getWxMpUser(code, state);
            message = wxMpUser.getNickname() + "恭喜你，抽奖注册成功~~~!\n您现在可以关闭网页，直接在公众号页面发送祝福并参与抽奖~";
        } catch (Exception e) {
            e.printStackTrace();
            message = "报名出错，请求联系管理员，错误信息:\n " + e.getMessage();
        }

        //return page to user
        return message;
    }
}

package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.service.OAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String oAuthLandingPage(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "state", required = false) String state,
                                   Model model) {
        return handleOauthRequest(code, state, model);
    }


    @GetMapping("/oauth")
    public String oAuthLandingPage2(@RequestParam(value = "code", required = false) String code,
                                    @RequestParam(value = "state", required = false) String state,
                                    Model model) {
        return handleOauthRequest(code, state, model);
    }

    private String handleOauthRequest(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            Model model) {

        if (code == null || state == null) {
            model.addAttribute("message1", "请点击报名");
            model.addAttribute("url", oAuthService.getOauthUrl(""));
            return "oauthfinish";
        }

        String message;
        try {
            WxMpUser wxMpUser = oAuthService.handleOAuthResultAndGetWxMpUser(code, state);
            message = wxMpUser.getNickname() + " 恭喜，婚礼抽奖报名成功~~~!";
        } catch (Exception e) {
            e.printStackTrace();
            message = "报名出错，请联系管理员，错误信息:\n " + e.getMessage();
        }
        model.addAttribute("message2", message);

        //return page to user
        return "oauthfinish";
    }
}

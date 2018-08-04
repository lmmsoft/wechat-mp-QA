package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.model.HelloWorldModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HelloWorldController {

    @ResponseBody
    @RequestMapping(value = "hello")
    public HelloWorldModel helloworld() {
        return new HelloWorldModel(1, "myname", 80.88f);
    }
}

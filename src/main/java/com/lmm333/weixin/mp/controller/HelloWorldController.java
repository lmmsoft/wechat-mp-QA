package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.model.HelloWorldModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HelloWorldController {

    @ResponseBody
    @RequestMapping(value = "hello")
    public HelloWorldModel helloworld() {
        return new HelloWorldModel(1, "myname", 80.88f);
    }

    @RequestMapping("/hello2")
    public String hello2(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://lmm333.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "hello";
    }
}
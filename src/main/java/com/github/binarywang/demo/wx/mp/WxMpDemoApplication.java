package com.github.binarywang.demo.wx.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@SpringBootApplication
@MapperScan("com.github.binarywang.demo.wx.mp.dao")
public class WxMpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxMpDemoApplication.class, args);
    }
}

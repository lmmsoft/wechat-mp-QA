package com.github.binarywang.demo.wx.mp;

import com.github.binarywang.demo.wx.mp.dao.HelloWorldMapper;
import com.github.binarywang.demo.wx.mp.dao.UserAnswerMapper;
import com.github.binarywang.demo.wx.mp.dao.UserMapper;
import com.github.binarywang.demo.wx.mp.model.HelloWorldModel;
import com.github.binarywang.demo.wx.mp.model.User;
import com.github.binarywang.demo.wx.mp.model.UserAnswer;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseDataBaseTest {
    @Autowired
    protected HelloWorldMapper helloWorldMapper;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected UserAnswerMapper userAnswerMapper;

    protected void initDatabase() {
        for (HelloWorldModel helloWorldModel : helloWorldMapper.findAll()) {
            helloWorldMapper.delete(helloWorldModel.getId());
        }

        for (User user : userMapper.findAll()) {
            userMapper.deleteById(user.getId());
        }

        for (UserAnswer userAnswer : userAnswerMapper.findAll()) {
            userAnswerMapper.deleteById(userAnswer.getId());
        }
    }
}

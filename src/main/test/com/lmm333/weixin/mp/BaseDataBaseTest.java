package com.lmm333.weixin.mp;

import com.lmm333.weixin.mp.dao.HelloWorldMapper;
import com.lmm333.weixin.mp.dao.UserAnswerMapper;
import com.lmm333.weixin.mp.dao.UserMapper;
import com.lmm333.weixin.mp.model.HelloWorldModel;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

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

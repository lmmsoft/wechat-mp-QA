package com.lmm333.weixin.mp;

import com.lmm333.weixin.mp.dao.HelloWorldMapper;
import com.lmm333.weixin.mp.dao.UserAnswerMapper;
import com.lmm333.weixin.mp.dao.UserMapper;
import com.lmm333.weixin.mp.model.HelloWorldModel;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

import org.junit.Assert;

import java.sql.Timestamp;

import javax.annotation.Resource;

public class BaseDataBaseTest {
    @Resource
    HelloWorldMapper helloWorldMapper;
    @Resource
    protected UserMapper userMapper;
    @Resource
    UserAnswerMapper userAnswerMapper;

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

    protected void prepareDataForTest(String wechatUserId1, String wechatUserId2, int questionId1, int questionId2, int userAnswerIndex1, int userAnswerIndex2, Timestamp updateTime) {
        User user1 = new User(wechatUserId1, 0);
        User user2 = new User(wechatUserId2, 0);
        userMapper.insert(user1);
        userMapper.insert(user2);
        Assert.assertEquals(2, userMapper.findAll().size());

        UserAnswer u1q1a1 = new UserAnswer(wechatUserId1, questionId1, userAnswerIndex1, updateTime);
        UserAnswer u1q2a2 = new UserAnswer(wechatUserId1, questionId2, userAnswerIndex2, updateTime);
        UserAnswer u2q1a1 = new UserAnswer(wechatUserId2, questionId1, userAnswerIndex1, updateTime);
        userAnswerMapper.insert(u1q1a1);
        userAnswerMapper.insert(u1q2a2);
        userAnswerMapper.insert(u2q1a1);
        Assert.assertEquals(3, userAnswerMapper.findAll().size());
    }
}

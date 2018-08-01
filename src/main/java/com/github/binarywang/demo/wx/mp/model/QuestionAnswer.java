package com.github.binarywang.demo.wx.mp.model;

import com.github.binarywang.demo.wx.mp.service.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionAnswer implements IQA {

    @Autowired
    private RedisService redisService;

    @Override
    public void init(List<Question> questionList) {

    }

    @Override
    public boolean insertUserAnswer(String wechatUserId, int qid, int answer) {

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.wechatUserId = wechatUserId;
        userAnswer.questionId = qid;
        userAnswer.userAnswerIndex = answer;

        redisService.set(wechatUserId, userAnswer);

        return true;
    }

    @Override
    public List<Integer> getUserList(int qid, int answerIndex) {
        return null;
    }
}

package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.dao.UserAnswerMapper;
import com.lmm333.weixin.mp.dao.UserMapper;
import com.lmm333.weixin.mp.model.Enum;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
class QAServiceImpl implements QAService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAnswerMapper userAnswerMapper;

    private Map<Integer, Integer> answerQuestionMap = new HashMap<>();//<AnswerId, questionId>

    public QAServiceImpl() {
        answerQuestionMap.put(11, 1);
        answerQuestionMap.put(12, 1);
        answerQuestionMap.put(13, 1);
        answerQuestionMap.put(14, 1);

        answerQuestionMap.put(21, 2);
        answerQuestionMap.put(22, 2);
        answerQuestionMap.put(23, 2);
        answerQuestionMap.put(24, 2);
    }

    @Override
    public Enum.InsertAnswerResultType insertUserAnswer(User user, UserAnswer userAnswer) {

        Enum.InsertAnswerResultType resultType = Enum.InsertAnswerResultType.Succeed;

        // Handle user
        User foundUser = userMapper.findByWechatUserId(user.getWechatUserId());
        if (foundUser == null) {
            try {
                userMapper.insert(user);
            } catch (Exception e) {
                return Enum.InsertAnswerResultType.Error;
            }

            foundUser = user;
        }

        if (foundUser.getRegisterType() == 0) {
            resultType = Enum.InsertAnswerResultType.SucceedNoUserInfo;
        }

        // Handle userAnswer
        boolean succeed = userAnswerMapper.replace(userAnswer);
        if (succeed) {
            return resultType;
        } else {
            return Enum.InsertAnswerResultType.Error;
        }
    }

    @Override
    public int findQuestionIdFromAnswerId(int questionId) {
        return answerQuestionMap.getOrDefault(questionId, -1);
    }
}

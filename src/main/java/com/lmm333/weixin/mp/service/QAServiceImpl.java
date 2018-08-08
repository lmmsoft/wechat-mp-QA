package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.dao.UserAnswerMapper;
import com.lmm333.weixin.mp.dao.UserMapper;
import com.lmm333.weixin.mp.model.Answer;
import com.lmm333.weixin.mp.model.Enum;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
class QAServiceImpl implements QAService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAnswerMapper userAnswerMapper;

    private Map<Integer, Integer> answerQuestionMap = new HashMap<>();//<AnswerId, QuestionId>
    private Map<Integer, List<Integer>> questionAnswerMap = new HashMap<>();//<QuestionId, AnswerId>
    private Map<Integer, Integer> questionRightAnswerMap = new HashMap<>();//<QuestionId, QuestionId>


    public QAServiceImpl() {
        answerQuestionMap.put(11, 1);
        answerQuestionMap.put(12, 1);
        answerQuestionMap.put(13, 1);
        answerQuestionMap.put(14, 1);

        answerQuestionMap.put(21, 2);
        answerQuestionMap.put(22, 2);
        answerQuestionMap.put(23, 2);
        answerQuestionMap.put(24, 2);

        questionAnswerMap.put(1, Arrays.asList(11, 12, 13, 14));
        questionAnswerMap.put(2, Arrays.asList(21, 22, 23, 24));

        questionRightAnswerMap.put(1, 11);
        questionRightAnswerMap.put(2, 22);
    }

    @Override
    public void updateUserRegisterType(User user) {
        User foundUser = userMapper.findByWechatUserId(user.getWechatUserId());
        if (foundUser == null) {
            try {
                userMapper.insert(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            foundUser.setRegisterType(user.getRegisterType());
            userMapper.update(foundUser);
        }
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

    @Override
    public Result findResultFromQuestionId(int questionId) {
        Result result = new Result();
        result.questionId = questionId;

        int rightAnswerId = questionRightAnswerMap.getOrDefault(questionId, -1);
        if (rightAnswerId == -1) {
            result.resultType = RESULE_INVALID_QUESTION_ID;
            return result;
        }
        result.rightAnswerId = rightAnswerId;

        List<Integer> answerList = questionAnswerMap.getOrDefault(questionId, null);
        if (answerList == null) {
            result.resultType = RESULE_INVALID_QUESTION_ID;
            return result;
        }


        // 没使用sql聚合函数，分多次(4次)查询结果，效率稍微差一点，但是应该也够了
        result.answerList = new ArrayList<>();
        for (int answerId : answerList) {
            Answer answerItem = new Answer();
            answerItem.answerId = answerId;
            answerItem.userCount = userAnswerMapper.findByUserAnswerIndex(answerId).size();
            result.answerList.add(answerItem);
        }

        // 用聚合函数，只要一次查询，但是count(*) 为 0的结果会被省略，需要自己处理0的结果，暂时不用
        //result.answerList = userAnswerMapper.findAnswerListByQuestionId(questionId);

        result.userList = userAnswerMapper.findUserByAnswerId(rightAnswerId);

        result.resultType = RESULE_SUCCEED;
        return result;
    }
}

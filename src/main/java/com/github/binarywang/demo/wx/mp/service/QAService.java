package com.github.binarywang.demo.wx.mp.service;

import com.github.binarywang.demo.wx.mp.model.Enum;
import com.github.binarywang.demo.wx.mp.model.User;
import com.github.binarywang.demo.wx.mp.model.UserAnswer;

/**
 * Created by lmm333 on 2018/7/31.
 */
public interface QAService {

    //boolean init(List<Question> questionList);

    Enum.InsertAnswerResultType insertUserAnswer(User user, UserAnswer userAnswer);

    int findQuestionIdFromAnswerId(int answerId);

    //List<Integer> getUserList(int qid, int answerIndex);
}

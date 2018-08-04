package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.model.Enum;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

public interface QAService {

    //boolean init(List<Question> questionList);

    Enum.InsertAnswerResultType insertUserAnswer(User user, UserAnswer userAnswer);

    int findQuestionIdFromAnswerId(int answerId);

    //List<Integer> getUserList(int qid, int answerIndex);
}

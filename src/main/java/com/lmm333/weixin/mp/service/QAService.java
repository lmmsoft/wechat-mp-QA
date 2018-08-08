package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.model.Enum;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

public interface QAService {
    int RESULE_SUCCEED = 0;
    int RESULE_INVALID_QUESTION_ID = 1;

    //boolean init(List<Question> questionList);

    void updateUserRegisterType(User user);

    Enum.InsertAnswerResultType insertUserAnswer(User user, UserAnswer userAnswer);

    int findQuestionIdFromAnswerId(int answerId);

    Result findResultFromQuestionId(int questionId);
}

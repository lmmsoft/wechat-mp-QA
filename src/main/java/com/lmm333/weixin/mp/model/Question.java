package com.lmm333.weixin.mp.model;

import java.util.List;

public class Question {
    int questionId;
    String questionTitle;//第一题
    String questionType;//送分题
    String questionDescription;//新郎叫什么名字

    int answersSize;
    int rightAnswerIndex;
    List<Answer> answerList;
}

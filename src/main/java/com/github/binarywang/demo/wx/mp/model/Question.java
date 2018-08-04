package com.github.binarywang.demo.wx.mp.model;

import java.util.List;

/**
 * Created by lmm333 on 2018/7/31.
 */
public class Question {
    int questionId;
    String questionTitle;//第一题
    String questionType;//送分题
    String questionDescription;//新浪叫什么名字

    int answersSize;
    int rightAnswerIndex;
    List<Answer> answerList;
}

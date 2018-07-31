package com.github.binarywang.demo.wx.mp.model;

import java.util.List;

/**
 * Created by lmm333 on 2018/7/31.
 */
public interface IQA {

    void init(List<Question> questionList);

    /**
     * @param wechatId wechatUserId
     * @param qid      question id
     * @param answer   user's answer to this question
     * @return succeed
     */
    boolean insertUserAnswer(String wechatId, int qid, int answer);

    List<Integer> getUserList(int qid, int answerIndex);
}

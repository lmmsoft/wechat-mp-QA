package com.lmm333.weixin.mp.model;

import java.sql.Timestamp;

public class UserAnswer {

    private int id;
    private String wechatUserId;
    private int questionId;
    private int userAnswerIndex;
    private int isright;
    private Timestamp updateTime;

    public UserAnswer() {
    }

    public UserAnswer(String wechatUserId, int questionId, int userAnswerIndex, Timestamp updateTime) {
        this.wechatUserId = wechatUserId;
        this.questionId = questionId;
        this.userAnswerIndex = userAnswerIndex;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public UserAnswer setId(int id) {
        this.id = id;
        return this;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public UserAnswer setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public int getQuestionId() {
        return questionId;
    }

    public UserAnswer setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public int getUserAnswerIndex() {
        return userAnswerIndex;
    }

    public UserAnswer setUserAnswerIndex(int userAnswerIndex) {
        this.userAnswerIndex = userAnswerIndex;
        return this;
    }

    public int getIsright() {
        return isright;
    }

    public UserAnswer setIsright(int isright) {
        this.isright = isright;
        return this;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public UserAnswer setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
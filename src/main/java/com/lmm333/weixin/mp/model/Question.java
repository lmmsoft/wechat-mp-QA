package com.lmm333.weixin.mp.model;

import java.util.List;

public class Question {
    int questionId;
    String questionTitle;//第一题
    String questionType;//送分题
    String questionDescription;//新郎叫什么名字

    int rightAnswerIndex;
    List<Answer2> answerList;

    public Question(int questionId, String questionTitle, String questionType, String questionDescription, int rightAnswerIndex, List<Answer2> answerList) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.questionDescription = questionDescription;
        this.rightAnswerIndex = rightAnswerIndex;
        this.answerList = answerList;
    }

    public int getQuestionId() {
        return questionId;
    }

    public Question setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public Question setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
        return this;
    }

    public String getQuestionType() {
        return questionType;
    }

    public Question setQuestionType(String questionType) {
        this.questionType = questionType;
        return this;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public Question setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
        return this;
    }

    public int getRightAnswerIndex() {
        return rightAnswerIndex;
    }

    public Question setRightAnswerIndex(int rightAnswerIndex) {
        this.rightAnswerIndex = rightAnswerIndex;
        return this;
    }

    public List<Answer2> getAnswerList() {
        return answerList;
    }

    public Question setAnswerList(List<Answer2> answerList) {
        this.answerList = answerList;
        return this;
    }
}

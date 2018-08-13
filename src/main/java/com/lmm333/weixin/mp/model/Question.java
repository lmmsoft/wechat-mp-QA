package com.lmm333.weixin.mp.model;

import java.util.List;

public class Question {
    private int questionId;
    private String questionTitle;//第一题
    private String questionType;//送分题
    private String answerDescription;//新郎叫什么名字

    private int rightAnswerIndex;
    private List<Answer2> answerList;

    public Question(int questionId, String questionType, String questionTitle, String answerDescription, int rightAnswerIndex, List<Answer2> answerList) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.answerDescription = answerDescription;
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

    public String getAnswerDescription() {
        return answerDescription;
    }

    public Question setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
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

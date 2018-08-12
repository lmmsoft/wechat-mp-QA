package com.lmm333.weixin.mp.model;

public class Answer2 {
    int answerId;
    String answerDescription;

    public Answer2(int answerId, String answerDescription) {
        this.answerId = answerId;
        this.answerDescription = answerDescription;
    }

    public int getAnswerId() {
        return answerId;
    }

    public Answer2 setAnswerId(int answerId) {
        this.answerId = answerId;
        return this;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public Answer2 setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
        return this;
    }
}

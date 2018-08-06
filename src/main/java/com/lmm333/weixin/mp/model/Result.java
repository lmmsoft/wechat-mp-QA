package com.lmm333.weixin.mp.model;

import java.util.List;

public class Result {
    public int resultType;// 0 succeed, 1 wrong questionId
    public int questionId;
    public int rightAnswerId;
    public List<Answer> answerList;
    public List<User> userList;
}
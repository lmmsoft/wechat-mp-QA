package com.github.binarywang.demo.wx.mp.model;

import java.util.List;

/**
 * Created by lmm333 on 2018/7/28.
 */
public class Model {

    class User {
        int uid;
        String wechatId;
        String wechatName;
        String wechatImageUrl;
        String userName;
        int registerType;//0没注册 1微信注册 2个人注册
    }

    class Question {
        int questionId;
        String questionTitle;//第一题
        String questionType;//送分题
        String questionDescription;//新浪叫什么名字

        int answersSize;
        int rightAnswerIndex;
        List<Answer> answerList;
    }

    class Answer {
        int index;//11 12 13 14
        int content;// 张三 李四 王二麻
        String imageUrl;// may be null
    }

    class UserAnswer {
        int userAnswerId;
        int uid;
        int questionId;
        int userAnswerIndex;
        long time;
    }

    class MessageLog {
        //text, image
    }

    public interface IQA {

        void init(List<Question> questionList);

        /**
         * @param uid    user id
         * @param qid    question id
         * @param answer user's andswer to this question
         * @return succeed
         */
        boolean insertUserAnswer(int uid, int qid, int answer);

        List<Integer> getUserList(int qid, int answerIndex);
    }
}

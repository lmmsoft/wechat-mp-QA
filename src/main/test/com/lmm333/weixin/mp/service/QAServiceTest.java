package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.BaseDataBaseTest;
import com.lmm333.weixin.mp.model.Enum;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QAServiceTest extends BaseDataBaseTest {

    @Autowired
    QAService qaService;

    @Before
    @After
    public void cleanUp() {
        initDatabase();
    }

    @Test
    public void insertUserAnswer() {
        String userId1 = "aa";
        String userId2 = "bb";
        int qid1 = 1;
        int qid2 = 2;
        int answerid1 = 11;
        int answerid2 = 22;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        User user1 = new User(userId1, 0);
        User user2 = new User(userId2, 1);

        UserAnswer userAnswer1 = new UserAnswer(userId1, qid1, answerid1, timestamp);
        UserAnswer userAnswer2 = new UserAnswer(userId1, qid2, answerid2, timestamp);

        Assert.assertEquals(Enum.InsertAnswerResultType.SucceedNoUserInfo, qaService.insertUserAnswer(user1, userAnswer1));
        Assert.assertEquals(Enum.InsertAnswerResultType.Succeed, qaService.insertUserAnswer(user2, userAnswer2));
    }

    @Test
    public void findQuestionIdFromAnswerId() {
        Assert.assertEquals(1, qaService.findQuestionIdFromAnswerId(11));
        Assert.assertEquals(1, qaService.findQuestionIdFromAnswerId(12));
        Assert.assertEquals(1, qaService.findQuestionIdFromAnswerId(13));
        Assert.assertEquals(1, qaService.findQuestionIdFromAnswerId(14));

        Assert.assertEquals(2, qaService.findQuestionIdFromAnswerId(21));
        Assert.assertEquals(2, qaService.findQuestionIdFromAnswerId(22));
        Assert.assertEquals(2, qaService.findQuestionIdFromAnswerId(23));
        Assert.assertEquals(2, qaService.findQuestionIdFromAnswerId(24));

        Assert.assertEquals(-1, qaService.findQuestionIdFromAnswerId(15));
        Assert.assertEquals(-1, qaService.findQuestionIdFromAnswerId(-1));
    }

    @Test
    public void findResultFromQuestionId() {
        // No result
        Result result = qaService.findResultFromQuestionId(123);
        Assert.assertNotNull(result);
        Assert.assertEquals(qaService.RESULE_INVALID_QUESTION_ID, result.resultType);
        Assert.assertEquals(123, result.questionId);
        Assert.assertNull(result.answerList);
        Assert.assertNull(result.userList);

        // Has result, but no user
        result = qaService.findResultFromQuestionId(1);
        Assert.assertNotNull(result);
        Assert.assertEquals(qaService.RESULE_SUCCEED, result.resultType);
        Assert.assertEquals(1, result.questionId);
        Assert.assertEquals(11, result.rightAnswerId);
        Assert.assertEquals(4, result.answerList.size());//11,12,13,14
        Assert.assertEquals(0, result.userList.size());

        //Has result and user
        String wechatUserId1 = "user1";
        String wechatUserId2 = "user2";
        int questionId1 = 1;
        int questionId2 = 2;
        int userAnswerIndex1 = 11;
        int userAnswerIndex2 = 12;
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());

        prepareDataForTest(wechatUserId1, wechatUserId2, questionId1, questionId2, userAnswerIndex1, userAnswerIndex2, updateTime);

        result = qaService.findResultFromQuestionId(questionId1);
        Assert.assertNotNull(result);
        Assert.assertEquals(qaService.RESULE_SUCCEED, result.resultType);
        Assert.assertEquals(questionId1, result.questionId);
        Assert.assertEquals(userAnswerIndex1, result.rightAnswerId);
        Assert.assertEquals(4, result.answerList.size());//11,12,13,14
        Assert.assertEquals(2, result.userList.size());
    }
}
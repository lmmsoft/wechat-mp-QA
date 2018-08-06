package com.lmm333.weixin.mp;

import com.lmm333.weixin.mp.model.Answer;
import com.lmm333.weixin.mp.model.HelloWorldModel;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTests extends BaseDataBaseTest {

    @Before
    @After
    public void cleanUp() {
        initDatabase();
    }

    @Test
    public void helloWorldMapperTest() {

        helloWorldMapper.insert(new HelloWorldModel(1, "tom", 81.8f));
        List<HelloWorldModel> list = helloWorldMapper.findAll();
        Assert.assertEquals(1, list.size());

//        helloWorldMapper.update("tom", 81.8f);

        HelloWorldModel helloWorldModel = helloWorldMapper.findByName("tom");
        Assert.assertEquals("tom", helloWorldModel.getName());
        Assert.assertEquals(81.8f, helloWorldModel.getWeight(), 0.01f);

        helloWorldModel.setWeight(88.8f);
        helloWorldMapper.update(helloWorldModel);
        Assert.assertEquals(88.8f, helloWorldModel.getWeight(), 0.01f);

        Assert.assertEquals(1, helloWorldMapper.findAll().size());

        helloWorldMapper.delete(helloWorldModel.getId());
        Assert.assertEquals(0, helloWorldMapper.findAll().size());
    }

    @Test
    public void userMapperTest() {
        String wechatUserId = "ok3SF1s4vWK48-1aM3b4p9gMq3Bs";
        String wechatName = "tom";

        User user1 = new User(wechatUserId, null, null, null, 0);

        userMapper.insert(user1);
        Assert.assertEquals(1, userMapper.findAll().size());

        boolean exceptionHappened = false;
        try {
            userMapper.insert(user1);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            exceptionHappened = true;
        }
        Assert.assertTrue(exceptionHappened);
        Assert.assertEquals(1, userMapper.findAll().size());

        User user2 = userMapper.findByWechatUserId("no-user");
        Assert.assertNull(user2);

        User user3 = userMapper.findByWechatUserId(wechatUserId);
        Assert.assertNotNull(user3);
        Assert.assertEquals(0, user3.getRegisterType());

        user3.setWechatName(wechatName);
        userMapper.update(user3);

        User user4 = userMapper.findByWechatUserId(wechatUserId);
        Assert.assertEquals(wechatName, user4.getWechatName());

        userMapper.deleteById(user4.getId());
        Assert.assertEquals(0, userMapper.findAll().size());

        userMapper.insert(user4);
        Assert.assertEquals(1, userMapper.findAll().size());

        userMapper.deleteByWechatUserId(user4.getWechatUserId());
        Assert.assertEquals(0, userMapper.findAll().size());
    }

    @Test
    public void userAnswerMapperTest() {
        String wechatUserId1 = "ok3SF1s4vWK48-1aM3b4p9gMq3B1";
        String wechatUserId2 = "ok3SF1s4vWK48-1aM3b4p9gMq3B2";
        int questionId1 = 101;
        int questionId2 = 102;
        int userAnswerIndex1 = 81;
        int userAnswerIndex2 = 82;
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());

        // Add user1-q1
        UserAnswer userAnswer1 = new UserAnswer(wechatUserId1, questionId1, userAnswerIndex1, updateTime);
        userAnswerMapper.insert(userAnswer1);
        Assert.assertEquals(1, userAnswerMapper.findAll().size());

        // Add user2-q1
        UserAnswer userAnswer2 = new UserAnswer(wechatUserId2, questionId1, userAnswerIndex2, updateTime);
        userAnswerMapper.replace(userAnswer2);
        Assert.assertEquals(2, userAnswerMapper.findAll().size());

        Assert.assertEquals(2, userAnswerMapper.findByQuestionId(questionId1).size());
        Assert.assertEquals(1, userAnswerMapper.findByUserAnswerIndex(userAnswerIndex1).size());

        // Update user2-q1
        UserAnswer userAnswer3 = new UserAnswer(wechatUserId2, questionId1, userAnswerIndex1, updateTime);
        userAnswerMapper.update(userAnswer3);
        Assert.assertEquals(2, userAnswerMapper.findByUserAnswerIndex(userAnswerIndex1).size());

        // Test unique key of [wecahtUserId,questionId1]
        UserAnswer u1q1 = new UserAnswer(wechatUserId1, questionId1, userAnswerIndex1, updateTime);
        userAnswerMapper.replace(u1q1);
        Assert.assertEquals(2, userAnswerMapper.findAll().size());

        UserAnswer u1q2 = new UserAnswer(wechatUserId1, questionId2, userAnswerIndex1, updateTime);
        userAnswerMapper.replace(u1q2);
        Assert.assertEquals(3, userAnswerMapper.findAll().size());

        UserAnswer u2q1 = new UserAnswer(wechatUserId2, questionId1, userAnswerIndex1, updateTime);
        userAnswerMapper.replace(u2q1);
        Assert.assertEquals(3, userAnswerMapper.findAll().size());

        UserAnswer u2q2 = new UserAnswer(wechatUserId2, questionId2, userAnswerIndex1, updateTime);
        userAnswerMapper.replace(u2q2);
        Assert.assertEquals(4, userAnswerMapper.findAll().size());
    }

    //tableJoin (User and UserAnswer)
    @Test
    public void findUserByAnswerId() {
        String wechatUserId1 = "user1";
        String wechatUserId2 = "user2";
        int questionId1 = 101;
        int questionId2 = 102;
        int userAnswerIndex1 = 81;
        int userAnswerIndex2 = 82;
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());

        prepareDataForTest(wechatUserId1, wechatUserId2, questionId1, questionId2, userAnswerIndex1, userAnswerIndex2, updateTime);

        List<User> userList = userAnswerMapper.findUserByAnswerId(userAnswerIndex1);
        Assert.assertEquals(2, userList.size());

        User u1 = userList.get(0);
        User u2 = userList.get(1);

        Assert.assertTrue(u1.getWechatUserId().equals(wechatUserId1) || u1.getWechatUserId().equals(wechatUserId2));
        Assert.assertTrue(u2.getWechatUserId().equals(wechatUserId1) || u2.getWechatUserId().equals(wechatUserId2));
    }

    @Test
    public void findAnswerListByQuestionId() {
        // check null data
        List<Answer> answerEmpty = userAnswerMapper.findAnswerListByQuestionId(1);
        Assert.assertEquals(0, answerEmpty.size());

        // valid data
        String wechatUserId1 = "user1";
        String wechatUserId2 = "user2";
        int questionId1 = 1;
        int questionId2 = 2;
        int userAnswerIndex1 = 11;
        int userAnswerIndex2 = 12;
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());

        prepareDataForTest(wechatUserId1, wechatUserId2, questionId1, questionId2, userAnswerIndex1, userAnswerIndex2, updateTime);

        List<Answer> answerList1 = userAnswerMapper.findAnswerListByQuestionId(questionId1);
        Assert.assertEquals(1, answerList1.size());
        Assert.assertEquals(2, answerList1.get(0).userCount);

        List<Answer> answerList2 = userAnswerMapper.findAnswerListByQuestionId(questionId2);
        Assert.assertEquals(1, answerList2.size());
        Assert.assertEquals(userAnswerIndex2, answerList2.get(0).answerId);
        Assert.assertEquals(1, answerList2.get(0).userCount);

        List<Answer> answerList3 = userAnswerMapper.findAnswerListByQuestionId(3);
        Assert.assertEquals(0, answerList3.size());
    }
}

package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.BaseDataBaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrepareData extends BaseDataBaseTest {

    //只用来准备测试数据（方便API的手动测试），不做实际Assert判断
    @Test
    public void prepareTestData() {
        // valid data
        String wechatUserId1 = "user1";
        String wechatUserId2 = "user2";
        int questionId1 = 1;
        int questionId2 = 2;
        int userAnswerIndex1 = 11;
        int userAnswerIndex2 = 12;
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());

        prepareDataForTest(wechatUserId1, wechatUserId2, questionId1, questionId2, userAnswerIndex1, userAnswerIndex2, updateTime);
    }
}

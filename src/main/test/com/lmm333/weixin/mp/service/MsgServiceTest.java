package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.BaseDataBaseTest;
import com.lmm333.weixin.mp.model.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsgServiceTest extends BaseDataBaseTest {

    @Autowired
    MsgServiceImpl msgService;

    @Before
    @After
    public void cleanUp() {
        initDatabase();
    }

    @Test
    public void handleWechatMessages() {
        WxMpXmlMessage message = new WxMpXmlMessage();
        message.setCreateTime(System.currentTimeMillis() / 1000L);

        message.setContent("18");
        Assert.assertEquals("数据输入错误，请重新输入：18", msgService.handleWechatMessages(message));

        message.setContent("百年好合");
        Assert.assertEquals("谢谢您的祝福：百年好合", msgService.handleWechatMessages(message));

        message.setContent("11");
        message.setCreateTime(System.currentTimeMillis() / 1000);
        Assert.assertEquals("系统出错，请重试（questionId=1,answerId=11）", msgService.handleWechatMessages(message));

        message.setFromUser("aa");
        Assert.assertTrue(msgService.handleWechatMessages(message).startsWith("第1题的答案已收到"));
    }

    @Test
    public void handleAnswer() {
        String wechatuserId = "fromUser";
        String content = msgService.handleAnswer(
                "content",
                wechatuserId,
                System.currentTimeMillis() / 1000L, 11);
        Assert.assertTrue(content.startsWith("第1题的答案已收到"));
        Assert.assertTrue(content.indexOf("报名参加抽奖") > 0);

        userMapper.replaceUserRegisterTypeByWechatUserId(new User(wechatuserId, User.TYPE_WECHAT_OAUTHED));
        Assert.assertEquals(User.TYPE_WECHAT_OAUTHED, userMapper.findByWechatUserId(wechatuserId).getRegisterType());

        Assert.assertEquals("第1题的答案已收到，愿您中奖~！",
                msgService.handleAnswer(
                        "content",
                        "fromUser",
                        System.currentTimeMillis() / 1000L, 11));

        Assert.assertEquals("数据输入错误，请重新输入：content",
                msgService.handleAnswer(
                        "content",
                        "fromUser",
                        System.currentTimeMillis() / 1000L, 15));

        Assert.assertEquals("系统出错，请重试（questionId=1,answerId=11）",
                msgService.handleAnswer(
                        "content",
                        null,
                        System.currentTimeMillis() / 1000L, 11));

    }

    @Test
    public void parseInt() {

        Assert.assertEquals(11, msgService.parseInt("11"));
        Assert.assertEquals(-1, msgService.parseInt(" 11 "));
        Assert.assertEquals(-1, msgService.parseInt(" 1 1 "));
        Assert.assertEquals(-1, msgService.parseInt("11a"));
        Assert.assertEquals(-1, msgService.parseInt(""));
        Assert.assertEquals(-1, msgService.parseInt("aa"));
        Assert.assertEquals(-1, msgService.parseInt("-2"));
    }
}
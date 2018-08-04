package com.github.binarywang.demo.wx.mp.service;

import com.github.binarywang.demo.wx.mp.model.Enum;
import com.github.binarywang.demo.wx.mp.model.User;
import com.github.binarywang.demo.wx.mp.model.UserAnswer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private QAService qaService;

    @Override
    public String handleWechatMessages(WxMpXmlMessage wxMessage) {
        String content = wxMessage.getContent();

        int answerId = parseInt(wxMessage.getContent().trim());
        if (answerId == -1) {
            //当做祝福话语，弹幕墙
            content = handleGreetingWords(content);

        } else {
            content = handleAnswer(
                    wxMessage.getContent(),
                    wxMessage.getFromUser(),
                    wxMessage.getCreateTime(),
                    answerId
            );
        }
        return content;
    }

    String handleAnswer(String content, String wechatUserId, long createTime, int answerId) {

        int questionid = qaService.findQuestionIdFromAnswerId(answerId);
        if (questionid == -1) {
            //invaled answer id
            return "数据输入错误，请重新输入：" + content.trim();
        }

        User user = new User();
        user.setWechatUserId(wechatUserId);

        UserAnswer userAnswer = new UserAnswer(
                wechatUserId,
                questionid,
                answerId,
                new Timestamp(createTime * 1000L));

        Enum.InsertAnswerResultType resultType = qaService.insertUserAnswer(user, userAnswer);
        switch (resultType) {
            case SucceedNoUserInfo:
                return String.format("第%d题的答案已收到，点击链接 http://lmm333.com/ 报名参加抽奖", questionid);
            case Error:
                return String.format("系统出错，请重试（questionId=%d,answerId=%d）", questionid, answerId);
            case Succeed:
            default:
                return String.format("第%d题的答案已收到，愿您中奖~！", questionid);
        }
    }

    int parseInt(String input) {
        int value;
        try {
            value = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            value = -1;
        }

        if (value > 0) {
            return value;
        } else {
            return -1;
        }
    }

    private String handleGreetingWords(String content) {
        return "谢谢您的祝福：" + content.trim();
    }
}
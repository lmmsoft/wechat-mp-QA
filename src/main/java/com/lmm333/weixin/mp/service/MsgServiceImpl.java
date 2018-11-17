package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.model.Enum;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private QAService qaService;
    @Autowired
    private OAuthService oAuthService;

    private static final List<String> stringList = Arrays.asList("愿您中奖~！", "万福金安，感谢有你^^", "大吉大利，晚上吃鸡!", "祝您财旺福旺身体旺!");
    private static int index = 0;

    @Override
    public String handleWechatEvent(WxMpXmlMessage wxMessage) {
        String content = "谢谢！";

        //对于普通公众号，只能拿到 wechatUserId 和 event两种信息
        User user = new User(wxMessage.getFromUser(), User.TYPE_SUBSCRIBED);

        switch (wxMessage.getEvent()) {
            case WxConsts.EventType.SUBSCRIBE:
                content = String.format("感谢您关注明明和虹虹~\n%s", oAuthService.getOauthUrlText(wxMessage.getFromUser()));
                user.setRegisterType(User.TYPE_SUBSCRIBED);
                qaService.replaceUserRegisterType(user);
                break;
            case WxConsts.EventType.UNSUBSCRIBE:
                user.setRegisterType(User.TYPE_UNSUBSCRIBED);
                qaService.updateUserRegisterType(user);
                break;
        }
        
        return content;
    }

    @Override
    public String handleWechatMessages(WxMpXmlMessage wxMessage) {
        String content = wxMessage.getContent();

        int answerId = parseInt(wxMessage.getContent().trim());
        if (answerId == -1) {
            //当做祝福话语，弹幕墙
            //content = handleGreetingWords(content);
            return "数据输入错误，请重新输入：" + content.trim();

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

        User user = new User(wechatUserId, User.TYPE_SUBSCRIBED);

        UserAnswer userAnswer = new UserAnswer(
                wechatUserId,
                questionid,
                answerId,
                new Timestamp(createTime * 1000L));

        Enum.InsertAnswerResultType resultType = qaService.insertUserAnswer(user, userAnswer);

        String firstString = String.format("第%d题的答案已收到\n%s", questionid, stringList.get(index++ % stringList.size()));
        switch (resultType) {
            case DuplicateQuestionId:
                return String.format("您已回答过第%d题，请确认后重新输入~", questionid);
            case SucceedNoUserInfo:
                return String.format("%s\n%s", firstString, oAuthService.getOauthUrlText(wechatUserId));
            case Error:
                return String.format("系统出错，请重试（questionId=%d,answerId=%d）", questionid, answerId);
            case Succeed:
            default:
                return firstString;
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
        //TODO, save this
        return "谢谢您的祝福：" + content.trim();
    }
}
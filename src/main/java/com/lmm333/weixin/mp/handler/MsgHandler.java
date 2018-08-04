package com.lmm333.weixin.mp.handler;

import com.lmm333.weixin.mp.builder.TextBuilder;
import com.lmm333.weixin.mp.service.MsgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    MsgService msgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService weixinService,
                                    WxSessionManager sessionManager) {

//        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
//            //TODO 可以选择将消息保存到本地
//        }
//        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
//        try {
//            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
//                    && weixinService.getKefuService().kfOnlineList()
//                    .getKfOnlineList().size() > 0) {
//                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
//                        .fromUser(wxMessage.getToUser())
//                        .toUser(wxMessage.getFromUser()).build();
//            }
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);


        String content = msgService.handleWechatMessages(wxMessage);

        return new TextBuilder().build(content, wxMessage, weixinService);
    }

}
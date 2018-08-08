package com.lmm333.weixin.mp.handler;

import com.lmm333.weixin.mp.builder.TextBuilder;
import com.lmm333.weixin.mp.service.MsgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
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

        String content = null;

        if (wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            //比如关注事件
            content = msgService.handleWechatEvent(wxMessage);
        } else {
            content = msgService.handleWechatMessages(wxMessage);
        }


        return new TextBuilder().build(content, wxMessage, weixinService);
    }

}

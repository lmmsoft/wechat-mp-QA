package com.lmm333.weixin.mp.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public interface MsgService {
    String handleWechatMessages(WxMpXmlMessage wxMessage);
}

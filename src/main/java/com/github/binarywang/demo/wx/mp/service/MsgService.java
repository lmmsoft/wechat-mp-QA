package com.github.binarywang.demo.wx.mp.service;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public interface MsgService {
    String handleWechatMessages(WxMpXmlMessage wxMessage);
}

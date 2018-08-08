package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.config.WxConfig;
import com.lmm333.weixin.mp.handler.KfSessionHandler;
import com.lmm333.weixin.mp.handler.LogHandler;
import com.lmm333.weixin.mp.handler.NullHandler;
import com.lmm333.weixin.mp.handler.StoreCheckNotifyHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfOnlineList;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
public abstract class BaseWxService extends WxMpServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected LogHandler logHandler;

    @Autowired
    protected NullHandler nullHandler;

    @Autowired
    protected KfSessionHandler kfSessionHandler;

    @Autowired
    protected StoreCheckNotifyHandler storeCheckNotifyHandler;

    private WxMpMessageRouter router;

    protected abstract WxConfig getServerConfig();

    @PostConstruct
    public void init() {
        final WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(this.getServerConfig().getAppid());// 设置微信公众号的appid
        config.setSecret(this.getServerConfig().getAppsecret());// 设置微信公众号的app corpSecret
        config.setToken(this.getServerConfig().getToken());// 设置微信公众号的token
        config.setAesKey(this.getServerConfig().getAesKey());// 设置消息加解密密钥
        super.setWxMpConfigStorage(config);

        this.refreshRouter();
    }

    private void refreshRouter() {

        final WxMpMessageRouter newRouter = new WxMpMessageRouter(this);

        // 记录所有事件的日志
        newRouter.rule().handler(this.logHandler).next();

        this.router = newRouter;
    }


    public WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    public boolean hasKefuOnline() {
        try {
            WxMpKfOnlineList kfOnlineList = this.getKefuService().kfOnlineList();
            return kfOnlineList != null && kfOnlineList.getKfOnlineList().size() > 0;
        } catch (Exception e) {
            this.logger.error("获取客服在线状态异常: " + e.getMessage(), e);
        }

        return false;
    }
}
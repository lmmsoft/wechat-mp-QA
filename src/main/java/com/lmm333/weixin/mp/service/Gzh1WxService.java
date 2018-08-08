package com.lmm333.weixin.mp.service;


import com.lmm333.weixin.mp.config.WxConfig;
import com.lmm333.weixin.mp.config.WxGzh1Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Binary Wang
 */
@Service
public class Gzh1WxService extends BaseWxService {
    @Autowired
    private WxGzh1Config wxConfig;

    @Override
    protected WxConfig getServerConfig() {
        return this.wxConfig;
    }
}
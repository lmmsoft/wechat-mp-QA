package com.lmm333.weixin.mp.model;

public class UserRightAnswer {
    private String wechatUserId;
    private String nickname;
    private String headimgurl;
    private int rightcount;

    public String getWechatUserId() {
        return wechatUserId;
    }

    public UserRightAnswer setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserRightAnswer setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public UserRightAnswer setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
        return this;
    }

    public int getRightcount() {
        return rightcount;
    }

    public UserRightAnswer setRightcount(int rightcount) {
        this.rightcount = rightcount;
        return this;
    }
}

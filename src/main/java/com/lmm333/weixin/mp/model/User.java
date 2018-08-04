package com.lmm333.weixin.mp.model;

public class User {
    private int id;
    private String wechatUserId;
    private String wechatName;
    private String wechatImageUrl;
    private String userName;
    private int registerType;//0没注册 1微信注册 2个人注册

    public User() {
    }

    public User(String wechatUserId, String wechatName, String wechatImageUrl, String userName, int registerType) {
        this.wechatUserId = wechatUserId;
        this.wechatName = wechatName;
        this.wechatImageUrl = wechatImageUrl;
        this.userName = userName;
        this.registerType = registerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getWechatImageUrl() {
        return wechatImageUrl;
    }

    public void setWechatImageUrl(String wechatImageUrl) {
        this.wechatImageUrl = wechatImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }
}

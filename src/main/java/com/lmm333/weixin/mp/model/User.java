package com.lmm333.weixin.mp.model;

public class User {
    public static final int TYPE_SUBSCRIBED = 0;
    public static final int TYPE_WECHAT_OAUTHED = 1;
    public static final int TYPE_PERSONAL_REGIESTERED = 2;
    public static final int TYPE_UNSUBSCRIBED = 9;

    private int id;
    private String wechatUserId;
    private int registerType;//0已关注没注册 1微信注册 2个人注册 9已取关

    private String nickname;
    private String headimgurl;
    private String userName;//个人设置的用户名，暂时没用

    private String sex;
    private String language;
    private String city;
    private String province;
    private String country;

    private String access_token;
    private String refresh_token;
    private String unionid;
    private String openid;//different from wechatUserId because openid is for mp2 and wechatUserId is for mp1
    private String code;

    public User() {
    }

    public User(String wechatUserId, int registerType) {
        this.wechatUserId = wechatUserId;
        this.registerType = registerType;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public User setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public int getRegisterType() {
        return registerType;
    }

    public User setRegisterType(int registerType) {
        this.registerType = registerType;
        return this;
    }


    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public User setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }


    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public User setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public User setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getAccess_token() {
        return access_token;
    }

    public User setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public User setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
        return this;
    }

    public String getUnionid() {
        return unionid;
    }

    public User setUnionid(String unionid) {
        this.unionid = unionid;
        return this;
    }

    public String getOpenid() {
        return openid;
    }

    public User setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public String getCode() {
        return code;
    }

    public User setCode(String code) {
        this.code = code;
        return this;
    }
}

package com.lmm333.weixin.mp.config;

/**
 * 公众号标识的枚举类
 *
 * @author Binary Wang
 */
public enum WxAccountEnum {
    GZH1(1, "公众号1"),
    GZH2(2, "公众号2");

    private int pubid;
    private String name;

    WxAccountEnum(int pubid, String name) {
        this.name = name;
        this.pubid = pubid;
    }

    public static int queryPubid(String wxCode) {
        return WxAccountEnum.valueOf(wxCode.toUpperCase()).getPubid();
    }

    public static String queryWxCode(int pubid) {
        for (WxAccountEnum e : values()) {
            if (e.getPubid() == pubid) {
                return e.name().toLowerCase();
            }
        }

        return null;
    }

    public int getPubid() {
        return this.pubid;
    }

    public String getName() {
        return this.name;
    }
}

package com.mabang.android.entity;

/**
 * Created by walke.Z on 2018/3/7.
 */

public enum ApiType {

    /**
     * 用户
     */
    MEMBER(1, "app_member_info"),

    /**
     * 工人
     */
    WORKER(2, "app_member_info");

    private int value;
    private String sessionKey;

    private ApiType(int value, String sessionKey) {
        this.sessionKey = sessionKey;
        return;
    }

    public int getValue() {
        return this.value;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }


}

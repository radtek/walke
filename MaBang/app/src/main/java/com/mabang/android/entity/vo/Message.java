package com.mabang.android.entity.vo;


import java.io.Serializable;

import walke.base.tool.RandomUtil;

/**
 * 客户端Api消息体
 *
 * @author xiongrui
 */
public class Message implements Serializable {

    /**
     * 请求id
     */
    private String id;

    /**
     * 协议版本
     */
    private String version;

    /**
     * 业务类型
     */
    private String type;

    /**
     * 业务数据
     */
    private String message;

    /**
     * 签名
     */
    private String sign;

    public Message() {
        this.id = System.currentTimeMillis() + "_" + RandomUtil.Random(3);
        return;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        return;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
        return;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
        return;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
        return;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
        return;
    }
}

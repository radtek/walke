package com.mabang.android.entity.vo;

import java.io.Serializable;

/**
 * VO基础类
 * 
 * @author xiong
 * 
 */
@SuppressWarnings("serial")
public class VoBase implements Serializable {

	/**
	 * 代码
	 */
	private int code;

	/**
	 * 文本
	 */
	private String text;

	/**
	 * 用户名/账号
	 */
	private String account;

	/**
	 * 用户token
	 */
	private String token;

	final public int getCode() {
		return this.code;
	}

	final public void setCode(int code) {
		this.code = code;
		return;
	}

	final public String getText() {
		return this.text;
	}

	final public void setText(String text) {
		this.text = text;
		return;
	}

	final public String getAccount() {
		return this.account;
	}

	final public void setAccount(String account) {
		this.account = account;
		return;
	}

	final public String getToken() {
		return this.token;
	}

	final public void setToken(String token) {
		this.token = token;
		return;
	}
}
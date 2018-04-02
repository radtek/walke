package com.mabang.android.entity.vo;

import java.io.Serializable;

/**
 * WebSocket返回结果
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
public class WebSocketResult implements Serializable {

	private String id;
	private String status;
	private String text;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
		return;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatusEnum(Status status) {
		this.status = status.toString().toLowerCase();
		return;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		return;
	}

	public enum Status {
		/**
		 * 开始
		 */
		START,

		/**
		 * 成功
		 */
		OK,

		/**
		 * 失败
		 */
		ERROR,

		/**
		 * 警告
		 */
		WARN;
	}
}
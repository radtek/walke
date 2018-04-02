package com.mabang.android.entity.vo;

@SuppressWarnings("serial")
public class AliyunInfo extends VoBase {
	private String accessKeyId; // id
	private String accessKeySecret; // key
	private String bucket; // bucket 桶
	private String region; // 地区域

	public String getAccessKeyId() {
		return this.accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
		return;
	}

	public String getAccessKeySecret() {
		return this.accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
		return;
	}

	public String getBucket() {
		return this.bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
		return;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
		return;
	}

}

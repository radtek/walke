package com.mabang.android.entity.vo;

/**
 * 手机验证码信息
 * 
 * @author xiong
 * 
 */
public class MobileValidateCodeInfo extends VoBase {
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 4942334939895083795L;

	private String mobile; // 手机号码

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
		return;
	}
}

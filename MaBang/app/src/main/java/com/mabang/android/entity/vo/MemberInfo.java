package com.mabang.android.entity.vo;


import com.mabang.android.entity.ApiType;

/**
 * 会员信息
 * 
 * @author xiong
 * 
 */
public class MemberInfo extends VoBase {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -6151502778476126648L;

	private String mobile; // 手机号
	private String password; // 工人登录的密码
	private String validate; // 用户登录的验证码
	private ApiType type; // 接口用户类型

	public MemberInfo() {
		return;
	}

	public MemberInfo(String mobile, ApiType type) {
		if (mobile == null)
			return;

		this.setAccount(mobile);
		this.mobile = mobile; // 手机号码
		this.type = type;
		return;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
		return;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
		return;
	}

	public String getValidate() {
		return this.validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
		return;
	}

	public ApiType getType() {
		return this.type;
	}

	public void setType(ApiType type) {
		this.type = type;
		return;
	}

}

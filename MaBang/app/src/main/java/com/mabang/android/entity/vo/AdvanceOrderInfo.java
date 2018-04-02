package com.mabang.android.entity.vo;

import java.util.List;

/**
 * 预约订单信息
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
public class AdvanceOrderInfo extends VoBase {

	private String name; // 客户姓名
	private String phone; // 联系电话
	private String company; // 所属公司
	private List<Integer> billboardList; // 预约广告位  --是广告位id?

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		return;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		return;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
		return;
	}

	public List<Integer> getBillboardList() {
		return this.billboardList;
	}

	public void setBillboardList(List<Integer> billboardList) {
		this.billboardList = billboardList;
		return;
	}

	@Override
	public String toString() {
		return "AdvanceOrderInfo{" +
				"name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", company='" + company + '\'' +
				", billboardList=" + billboardList +
				'}';
	}
}

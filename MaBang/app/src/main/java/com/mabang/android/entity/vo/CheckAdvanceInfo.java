package com.mabang.android.entity.vo;

import java.util.LinkedHashMap;

/**
 * 检查广告位预约信息
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
public class CheckAdvanceInfo extends VoBase {

	private LinkedHashMap<Integer, Boolean> billboardMap; // 检查预约广告位是否可用：true为可用; false为占用或其它

	public LinkedHashMap<Integer, Boolean> getBillboardMap() {
		return this.billboardMap;
	}

	public void setBillboardMap(LinkedHashMap<Integer, Boolean> billboardMap) {
		this.billboardMap = billboardMap;
		return;
	}

}

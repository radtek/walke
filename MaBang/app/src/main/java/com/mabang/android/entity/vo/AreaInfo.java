package com.mabang.android.entity.vo;

/**
 * 区域信息
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
public class AreaInfo extends VoBase {
	private Integer areaId;
	private String areaName;
	private boolean check;

	public Integer getAreaId() {
		return this.areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
		return;
	}
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
		return;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
}

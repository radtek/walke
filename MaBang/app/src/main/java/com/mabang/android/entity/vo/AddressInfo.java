package com.mabang.android.entity.vo;

import java.util.List;

@SuppressWarnings("serial")
public class AddressInfo extends VoBase {

	private Integer provinceId; // 省份ID--默认选择广东
	private Integer cityId; // 城市ID
	private Integer zoneId; // 区域ID
	private Integer streetId; // 街道ID

	private List<AreaInfo> provinceList; // 省份列表--全部
	private List<AreaInfo> cityList; // 城市列表--省份ID下的所有城市
	private List<AreaInfo> zoneList; // 区域列表--城市ID下的所有区域
	private List<AreaInfo> streetList; // 街道列表--区域下的所有街道（注：有些区域没有街道数据）

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
		return;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
		return;
	}

	public Integer getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
		return;
	}

	public Integer getStreetId() {
		return this.streetId;
	}

	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
		return;
	}

	public List<AreaInfo> getProvinceList() {
		return this.provinceList;
	}

	public void setProvinceList(List<AreaInfo> provinceList) {
		this.provinceList = provinceList;
		return;
	}

	public List<AreaInfo> getCityList() {
		return this.cityList;
	}

	public void setCityList(List<AreaInfo> cityList) {
		this.cityList = cityList;
		return;
	}

	public List<AreaInfo> getZoneList() {
		return this.zoneList;
	}

	public void setZoneList(List<AreaInfo> zoneList) {
		this.zoneList = zoneList;
		return;
	}

	public List<AreaInfo> getStreetList() {
		return this.streetList;
	}

	public void setStreetList(List<AreaInfo> streetList) {
		this.streetList = streetList;
		return;
	}
}

package com.mabang.android.entity.vo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;


/**
 * 广告位信息
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
@DatabaseTable(tableName = "billboardInfo")
public class BillboardInfo extends VoBase {

	@DatabaseField(generatedId = true)
	private Integer dbid;//仅用于本地数据库(特有属性，后台没有该属性)

	@DatabaseField(unique = true)
	private Integer id; //这个要保持唯一性

	@DatabaseField(columnName = "uniqueCode")
	private String uniqueCode; // 唯一码

	@DatabaseField(columnName = "manageCode")
	private String manageCode; // 管理码

	@DatabaseField(columnName = "shortName")
	private String shortName; // 名称

	@DatabaseField(columnName = "longAddress")
	private String longAddress; // 地址全称

	@DatabaseField(columnName = "shedMaterial")
	private String shedMaterial; // 雨棚材质

	@DatabaseField(columnName = "statusText")
	private String statusText; // 状态 已使用、空闲

	@DatabaseField(columnName = "statusDesc")
	private String statusDesc ; // 状态描述 有可能是空，有值就是公司名称

	@DatabaseField(columnName = "spec")
	private String spec; // 规格

	@DatabaseField(columnName = "otherDescribe")
	private String otherDescribe; // 其它描述

	@DatabaseField(columnName = "locationLng")
	private Double locationLng; // 经度

	@DatabaseField(columnName = "locationLat")
	private Double locationLat; // 纬度

	@DatabaseField(columnName = "available")
	private boolean available; // 是否可用、可被预约

	//	@DatabaseField(columnName = "isSelected") //应不要，会在app上用户改动
	private boolean isSelected; // 预选广告位列表界面用来记录是否已勾选，仅用于本地(特有属性)，

//	private boolean myAdvance; // 是否为我预约的  这个参数只会在“可预约”的条件中会有值体现，其它条件查询时值都为：false
	// myAdvance 取消了，会导致解析出错
	private int advanceType; // 1为可预订的，2为我预订的，0就是其他数据

	private Integer provinceId; // 省份ID
	private Integer cityId; // 城市ID
	private Integer areaId; // 区域ID
	private Integer streetId; // 街道ID

	private String address; // 详细地址

	private int status; // 状态：1为启用、非1为禁用

	private List<BillboardImageInfo> advertisingImageList; // 广告位图
	private List<BillboardImageInfo> acceptanceImageList; // 验收图

	private AliyunInfo aliyunInfo; // 阿里云配置文件

	public BillboardInfo() {
		return;
	}

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
		return;
	}

	public String getUniqueCode() {
		return this.uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
		return;
	}

	public String getManageCode() {
		return this.manageCode;
	}

	public void setManageCode(String manageCode) {
		this.manageCode = manageCode;
		return;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
		return;
	}

	public String getLongAddress() {
		return this.longAddress;
	}

	public void setLongAddress(String longAddress) {
		this.longAddress = longAddress;
		return;
	}

	public String getShedMaterial() {
		return this.shedMaterial;
	}

	public void setShedMaterial(String shedMaterial) {
		this.shedMaterial = shedMaterial;
		return;
	}

	public String getStatusText() {
		return this.statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
		return;
	}

	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
		return;
	}

	public String getOtherDescribe() {
		return this.otherDescribe;
	}

	public void setOtherDescribe(String otherDescribe) {
		this.otherDescribe = otherDescribe;
		return;
	}

	public Double getLocationLng() {
		return this.locationLng;
	}

	public void setLocationLng(Double locationLng) {
		this.locationLng = locationLng;
		return;
	}

	public Double getLocationLat() {
		return this.locationLat;
	}

	public void setLocationLat(Double locationLat) {
		this.locationLat = locationLat;
		return;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

//	public boolean isMyAdvance() {
//		return myAdvance;
//	}
//
//	public void setMyAdvance(boolean myAdvance) {
//		this.myAdvance = myAdvance;
//	}


	public int getAdvanceType() {
		return advanceType;
	}

	public void setAdvanceType(int advanceType) {
		this.advanceType = advanceType;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getStreetId() {
		return streetId;
	}

	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<BillboardImageInfo> getAdvertisingImageList() {
		return this.advertisingImageList;
	}

	public void setAdvertisingImageList(List<BillboardImageInfo> advertisingImageList) {
		this.advertisingImageList = advertisingImageList;
		return;
	}

	public List<BillboardImageInfo> getAcceptanceImageList() {
		return this.acceptanceImageList;
	}

	public void setAcceptanceImageList(List<BillboardImageInfo> acceptanceImageList) {
		this.acceptanceImageList = acceptanceImageList;
		return;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public AliyunInfo getAliyunInfo() {
		return aliyunInfo;
	}

	public void setAliyunInfo(AliyunInfo aliyunInfo) {
		this.aliyunInfo = aliyunInfo;
	}

	@Override
	public String toString() {
		return "BillboardInfo{" +
				" id=" + id +
				", uniqueCode='" + uniqueCode + '\'' +
				", manageCode='" + manageCode + '\'' +
				", advanceType=" + advanceType +
				", shortName='" + shortName + '\'' +
				", longAddress='" + longAddress + '\'' +
				", shedMaterial='" + shedMaterial + '\'' +
				", statusText='" + statusText + '\'' +
				", statusDesc='" + statusDesc + '\'' +
				", spec='" + spec + '\'' +
				", otherDescribe='" + otherDescribe + '\'' +
				", locationLng='" + locationLng + '\'' +
				", locationLat='" + locationLat + '\'' +
				", available=" + available +
				", isSelected=" + isSelected +
				", advertisingImageList=" + advertisingImageList +
				", acceptanceImageList=" + acceptanceImageList +
				", aliyunInfo=" + aliyunInfo +
				'}';
	}
}

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
public class BillboardInfo_old extends VoBase {

	@DatabaseField(generatedId = true)
	private Integer dbid;

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
	private String statusText; // 状态

	@DatabaseField(columnName = "spec")
	private String spec; // 规格

	@DatabaseField(columnName = "otherDescribe")
	private String otherDescribe; // 其它描述

	@DatabaseField(columnName = "locationLng")
	private String locationLng; // 经度

	@DatabaseField(columnName = "locationLat")
	private String locationLat; // 纬度

	@DatabaseField(columnName = "available")
	private boolean available; // 是否可用、可被预约

	private List<String> advertisingImageList; // 广告位图
	private List<String> acceptanceImageList; // 验收图

	public BillboardInfo_old() {
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

	public String getLocationLng() {
		return this.locationLng;
	}

	public void setLocationLng(String locationLng) {
		this.locationLng = locationLng;
		return;
	}

	public String getLocationLat() {
		return this.locationLat;
	}

	public void setLocationLat(String locationLat) {
		this.locationLat = locationLat;
		return;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public List<String> getAdvertisingImageList() {
		return this.advertisingImageList;
	}

	public void setAdvertisingImageList(List<String> advertisingImageList) {
		this.advertisingImageList = advertisingImageList;
		return;
	}

	public List<String> getAcceptanceImageList() {
		return this.acceptanceImageList;
	}

	public void setAcceptanceImageList(List<String> acceptanceImageList) {
		this.acceptanceImageList = acceptanceImageList;
		return;
	}
}

package com.mabang.sys.entity.vo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mabang.sys.entity.base.ApiVOBase;
import com.mabang.sys.entity.po.Billboard;
import com.mabang.sys.entity.po.Billboard.BillboardStatus;

/**
 * 广告位信息
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
public class BillboardInfo extends ApiVOBase {

	private Integer id;
	private String uniqueCode; // 唯一码
	private String manageCode; // 管理码
	private String shortName; // 名称
	private String longAddress; // 地址全称
	private String shedMaterial; // 雨棚材质
	private String statusText; // 状态
	private String statusDesc; // 状态描述
	private String spec; // 规格
	private String otherDescribe; // 其它描述
	private Integer provinceId; // 省份ID
	private Integer cityId; // 城市ID
	private Integer areaId; // 区域ID
	private Integer streetId; // 街道ID
	private String address; // 详细地址
	private Double locationLng; // 经度
	private Double locationLat; // 纬度
	private List<BillboardImageInfo> advertisingImageList; // 广告位图
	private List<BillboardImageInfo> acceptanceImageList; // 验收图
	private boolean available; // 是否可用、可被预约
	private AliyunInfo aliyunInfo; // 阿里云配置文件
	private int advanceType; // 预约类型， 1：可预约的 2：我预约的
	private int status; // 状态：1为启用、非1为禁用

	public BillboardInfo() {
		return;
	}

	public BillboardInfo(Billboard billboard) {
		this.id = billboard.getId();
		this.uniqueCode = billboard.getUniqueCode() != null ? billboard.getUniqueCode() : "";
		this.manageCode = billboard.getManageCode() != null ? billboard.getManageCode() : "";
		this.shortName = billboard.getShortName() != null ? billboard.getShortName() : "";
		if (StringUtils.isEmpty(this.shortName) && !StringUtils.isEmpty(billboard.getStreetName())) {
			this.shortName = billboard.getStreetName();
		}
		this.longAddress = billboard.getLongAddress() != null ? billboard.getLongAddress() : "";
		this.shedMaterial = billboard.getShedMaterial() != null ? billboard.getShedMaterial() : "";
		this.statusText = billboard.getBillboardStatus() != null ? billboard.getBillboardStatus().getText() : "";
		this.statusDesc = billboard.getStatusDesc() != null ? billboard.getStatusDesc() : "";
		this.spec = billboard.getSpec() != null ? billboard.getSpec() : "";
		this.otherDescribe = billboard.getOtherDescribe() != null ? billboard.getOtherDescribe() : "";
		this.areaId = billboard.getZoneId();
		this.locationLng = billboard.getLocationLng();
		this.locationLat = billboard.getLocationLat();
		if (billboard.getBillboardStatus() == null || BillboardStatus.IDLE.equals(billboard.getBillboardStatus()))
			this.available = true;
		return;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getStatusDesc() {
		return this.statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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

	public Integer getStreetId() {
		return this.streetId;
	}

	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
		return;
	}

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
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

	public boolean isAvailable() {
		return this.available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
		return;
	}

	public AliyunInfo getAliyunInfo() {
		return this.aliyunInfo;
	}

	public void setAliyunInfo(AliyunInfo aliyunInfo) {
		this.aliyunInfo = aliyunInfo;
		return;
	}

	public int getAdvanceType() {
		return this.advanceType;
	}

	public void setAdvanceType(int advanceType) {
		this.advanceType = advanceType;
		return;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
		return;
	}
}

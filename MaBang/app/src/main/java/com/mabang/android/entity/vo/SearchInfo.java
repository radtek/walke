package com.mabang.android.entity.vo;

import java.util.List;

/**
 * 查询条件
 * 
 * @author xiong
 *
 */
@SuppressWarnings("serial")
public class SearchInfo extends VoBase {

	private Integer provinceId; // 省份ID
	private Integer cityId; // 城市ID
	private Integer zoneId; // 区域ID
	private Integer streetId; // 街道ID
	private String address; // 模糊地址
	private double[] startPoint; // 地图开始点
	private double[] endPoint; // 地图结束点
	private SearchType searchType; // 查询类型
	private List<BillboardInfo> billboardInfoList; // 查询结果

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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
		return;
	}

	public double[] getStartPoint() {
		return this.startPoint;
	}

	public void setStartPoint(double[] startPoint) {
		this.startPoint = startPoint;
		return;
	}

	public double[] getEndPoint() {
		return this.endPoint;
	}

	public void setEndPoint(double[] endPoint) {
		this.endPoint = endPoint;
		return;
	}

	public SearchType getSearchType() {
		return this.searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
		return;
	}

	public List<BillboardInfo> getBillboardInfoList() {
		return this.billboardInfoList;
	}

	public void setBillboardInfoList(List<BillboardInfo> billboardInfoList) {
		this.billboardInfoList = billboardInfoList;
		return;
	}

	/**
	 * 查询类型
	 * 
	 * @author xiong
	 * 
	 * @describe 1.可预订的广告位 <br/>
	 *           2.当前用户确定的广告位 <br/>
	 *           3.当前用户预定的广告位 <br/>
	 *           4.被确定的广告位
	 *
	 */

	public enum SearchType {
		/**
		 * 可预约 can_bespeak
		 */
		CAN_BESPEAK(1, "可预约"),

		/**
		 * 我预约 my_bespeak
		 */
		MY_BESPEAK(2, "我预约"),

		/**
		 * 我占用 my_occupy
		 */
		MY_OCCUPY(3, "我占用"),

		/**
		 * 已占用 occupy
		 */
		OCCUPY(4, "已占用");

		private Integer value;
		private String text;

		private SearchType(Integer value, String text) {
			this.value = value;
			this.text = text;
			return;
		}

		public Integer getValue() {
			return this.value;
		}

		public String getText() {
			return this.text;
		}
	}

}

package com.grandsea.ticketvendingapplication.model.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户电子票信息
 * */
public class TicketInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private int shiftDateTicketNumId;//下车站点id
	
	@Expose
	private int getOnStationId;//上车站点id
	
	@Expose
	private String getOnStation;//上车站点
	
	@Expose
	private String takeOffStation;//下车站点
	
	@Expose
	private String ticketDate;//车票日期
	
	@Expose
	private String getOnTime;//
	
	@Expose
	private String plateNum;//车牌号
	
	@Expose
	private int busPartnerId;//大巴合作伙伴id
	
	@Expose
	private String busPartnerPhone;///大巴合作伙伴手机号
	
	@Expose
	private String busPartnerName;//大巴合作伙伴名称
	
	@Expose
	private String qrCode;//二维码字符串

	private int id;
	private String code;//验票码  申请退票时上传
	private String name;//联系人姓名
	private String phone;//联系人电话
	private String idCard;//联系人身份证号份证id
	private Integer seat;//座位号
	private Date createtime;//生成时间
	private String orderId;//订单id
	private Date refundTime;//退款时间
	private int isContact;//是否为联系人
	private Integer status;//验票状态 0：未验票，1：已验票
	private Integer type;//1：成人（全票），3：学生（学生票），4：免票儿童
	private Timestamp updatetime;//

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getSeat() {
		return seat;
	}

	public void setSeat(Integer seat) {
		this.seat = seat;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public int getIsContact() {
		return isContact;
	}

	public void setIsContact(int isContact) {
		this.isContact = isContact;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public int getShiftDateTicketNumId() {
		return shiftDateTicketNumId;
	}

	public void setShiftDateTicketNumId(int shiftDateTicketNumId) {
		this.shiftDateTicketNumId = shiftDateTicketNumId;
	}

	public int getGetOnStationId() {
		return getOnStationId;
	}

	public void setGetOnStationId(int getOnStationId) {
		this.getOnStationId = getOnStationId;
	}

	public String getGetOnStation() {
		return getOnStation;
	}

	public void setGetOnStation(String getOnStation) {
		this.getOnStation = getOnStation;
	}

	public String getTakeOffStation() {
		return takeOffStation;
	}

	public void setTakeOffStation(String takeOffStation) {
		this.takeOffStation = takeOffStation;
	}

	public String getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}

	public String getGetOnTime() {
		return getOnTime.length() == 5 ? getOnTime + ":00" : getOnTime;
	}

	public void setGetOnTime(String getOnTime) {
		this.getOnTime = getOnTime;
	}

	public String getPlateNum() {
		return plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	public int getBusPartnerId() {
		return busPartnerId;
	}

	public void setBusPartnerId(int busPartnerId) {
		this.busPartnerId = busPartnerId;
	}

	public String getBusPartnerPhone() {
		return busPartnerPhone;
	}

	public void setBusPartnerPhone(String busPartnerPhone) {
		this.busPartnerPhone = busPartnerPhone;
	}

	public String getBusPartnerName() {
		return busPartnerName;
	}

	public void setBusPartnerName(String busPartnerName) {
		this.busPartnerName = busPartnerName;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

}

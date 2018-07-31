package com.grandsea.ticketvendingapplication.model.common;

/**
 * 协议头
 * 
 * @author moyuhui
 * 
 */
public class Phead {
	private int pversion;			// 协议版本号
	private String sid;				// system id机器系统唯一标释，如安卓系统为手机androidid，苹果系统为IOS。。。
	private String imei;			// 手机imei
	private String uid = "58ff4eddda0e83185baa7eb7cc4fc5d1";				// 用户id
	private int cid;				// 产品id
	private int cversion;			// 客户端软件版本num
	private String cversionname;	// 客户端软件版名称
	private int channel;			// 渠道号
	private String local;			// 国家(大写)
	private String lang;			// 语言（小写）
	private String imsi;			// 运营商编码
	private int sdk;				// 系统sdklevel
	private String sys;				// 系统版本
	private String model;			// 机型
	private String requesttime;		// 请求时间，客户端请求服务器的手机时间格式：yyyy-MM-dd HH:mm:ss
	private String net;				// 网络类型
	private String coordinates;		// 经纬度信息:经度#纬度
	private String positions;		// 定位到的位置信息:国家#省份#城市
	private String cip;				// 客户端ip
	private String pushcid;
	private String phone;
	private String euid = "0f3326eadcc11eff76b1e7f369f103b9";




	public void setEuid(String euid) {
		this.euid = euid;
	}

	public String getEuid() {
		return euid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("pversion=");
		builder.append(pversion);
		builder.append(", id=");
		builder.append(sid);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", uid=");
		builder.append(uid);
		builder.append(", cid=");
		builder.append(cid);
		builder.append(", cversion=");
		builder.append(cversion);
		builder.append(", cversionname=");
		builder.append(cversionname);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", local=");
		builder.append(local);
		builder.append(", lang=");
		builder.append(lang);
		builder.append(", imsi=");
		builder.append(imsi);
		builder.append(", sdk=");
		builder.append(sdk);
		builder.append(", sys=");
		builder.append(sys);
		builder.append(", model=");
		builder.append(model);
		builder.append(", requesttime=");
		builder.append(requesttime);
		builder.append(", net=");
		builder.append(net);
		builder.append(", coordinates=");
		builder.append(coordinates);
		builder.append(", positions=");
		builder.append(positions);
//		builder.append(", gadid=");
//		builder.append(gadid);
		builder.append(", cip=");
		builder.append(cip);
		builder.append(", pushcid=");
		builder.append(pushcid);
		builder.append(", phone=");
		builder.append(phone);
		return builder.toString();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPversion() {
		return pversion;
	}

	public void setPversion(int pversion) {
		this.pversion = pversion;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getCversion() {
		return cversion;
	}

	public void setCversion(int cversion) {
		this.cversion = cversion;
	}

	public String getCversionname() {
		return cversionname;
	}

	public void setCversionname(String cversionname) {
		this.cversionname = cversionname;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getLocal() {
		return local;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public int getSdk() {
		return sdk;
	}

	public void setSdk(int sdk) {
		this.sdk = sdk;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRequesttime() {
		return requesttime;
	}

	public void setRequesttime(String requesttime) {
		this.requesttime = requesttime;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getPushcid() {
		return pushcid;
	}

	public void setPushcid(String pushcid) {
		this.pushcid = pushcid;
	}

	public void validate() throws Exception {
		
	}
}
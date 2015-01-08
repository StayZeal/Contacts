package co.stayzeal.contact.model;

import java.util.Date;

/**
 * 
 * @author ArthorK
 *
 */
public class CallLogInfo {
	
	private Long id;
	private String Name;
	
	private String phone;
	
	private String provinceCity;
	//记录通话日期
	private Date callDate;
	//1.拨出 ；2.已接 ；3.未接
	private int callType;
	//通话次数，包括以上三种类型
	private int count;
	//通话时长
	private int callDuration;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	 
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvinceCity() {
		return provinceCity;
	}
	public void setProvinceCity(String provinceCity) {
		this.provinceCity = provinceCity;
	}
	 
	 
	public Date getCallDate() {
		return callDate;
	}
	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	public int getCallType() {
		return callType;
	}
	public void setCallType(int callType) {
		this.callType = callType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}
	 
	
	
	
}

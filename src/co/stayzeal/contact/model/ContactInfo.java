package co.stayzeal.contact.model;

import android.graphics.Bitmap;

public class ContactInfo {
	
	private int id;
	private String contactName;
	private String contactNumber;
	private Bitmap contactIcon;
	//private Integer contactIcon;
	private String address;
	private String eamil;
	private String relation;
	//排序方式
	private String sortKey;
	private String provinceCity;
	private String namePinYin;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	 
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEamil() {
		return eamil;
	}
	public void setEamil(String eamil) {
		this.eamil = eamil;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getProvinceCity() {
		return provinceCity;
	}
	public void setProvinceCity(String provinceCity) {
		this.provinceCity = provinceCity;
	}
	public String getNamePinYin() {
		return namePinYin;
	}
	public void setNamePinYin(String namePinYin) {
		this.namePinYin = namePinYin;
	}
	public Bitmap getContactIcon() {
		return contactIcon;
	}
	public void setContactIcon(Bitmap contactIcon) {
		this.contactIcon = contactIcon;
	}
	
	
}

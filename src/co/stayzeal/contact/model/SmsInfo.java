package co.stayzeal.contact.model;

import java.util.Date;

/**
 * 短信息model类
 * @author YOUNG
 *
 */
public class SmsInfo {
	
	//sms主要结构：
	private int id;   //短消息序号 如100 
	private String contactName;   //发件人的名字
	private String threadId;   //对话的序号 如100  
	private String address;    //发件人地址，手机号.如+8613811810000  
	private String person;     //发件人，返回一个数字就是联系人列表里的序号，陌生人为null  
	private Date date;         //日期  long型。如1256539465022  
	private int protocol;      //协议 0 SMS_RPOTO, 1 MMS_PROTO   
	private int read;          //是否阅读 0未读， 1已读   
	private int status;        // 状态 -1接收，0 complete, 64 pending, 128 failed   
	private int type;          //类型 1是接收到的，2是已发出   
	private String body;       //短消息内容   
	private String serviceCenter;//短信服务中心号码编号。如+8613800755500
	private String snippe;//短信的前45个字符
	private int msgCount;//每个回话的短消息数
	private int layoutType;//记录layout的类型
	
	public SmsInfo(){
		
	}
	/**
	 * 
	 * @param threadId
	 * @param msgCount
	 * @param snippe
	 */
	public SmsInfo(String threadId,int msgCount,String snippe){
		this.threadId=threadId;
		this.msgCount=msgCount;
		this.snippe=snippe;
		
	}
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
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getServiceCenter() {
		return serviceCenter;
	}
	public void setServiceCenter(String serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
	public String getSnippe() {
		return snippe;
	}
	public void setSnippe(String snippe) {
		this.snippe = snippe;
	}
	public int getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}
	
}

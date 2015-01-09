package co.stayzeal.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.stayzeal.contact.model.SmsInfo;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * 短信息的相关操作
 * 
 * @author YOUNG
 *
 */
public class SmsOperation {

	private Context context;
	private List<SmsInfo> smsList;
	private Uri uri;

	/**
	 * 为了兼容以前的版本，只能采用这种方法
	 */
	public static final String CONTENT_URI_SMS = "content://sms"; // 短信
	public static final String CONTENT_URI_SMS_INBOX = "content://sms/inbox";// 收件箱
	public static final String CONTENT_URI_SMS_OUTBOX="content://sms/outbox";//发件箱
	public static final String CONTENT_URI_SMS_SENT = "content://sms/sent"; // 发送
	public static final String CONTENT_URI_SMS_CONVERSATIONS = "content://sms/conversations";

	public SmsOperation(Context context) {
		this.context = context;
		//需要用到新的api
		//this.uri = Telephony.Sms.CONTENT_URI;  
	}
	
	/**
	 * 获取收件箱所有的信息，默认每一条信息为一天记录，相同联系人不合并
	 * @return
	 */
	public List<SmsInfo> getSmsInfoList() {
		smsList = new ArrayList<SmsInfo>();
		String[] projection = new String[] { "_id", "address", "person","body", "date", "type" };  
		Cursor cursor=context.getContentResolver().query(Uri.parse(CONTENT_URI_SMS_INBOX), projection, null, null, null);
		if(cursor.moveToFirst()){
			do{
				SmsInfo sms=new SmsInfo();
				sms.setId(cursor.getInt(cursor.getColumnIndex("_id")));
				sms.setPerson(cursor.getColumnName(cursor.getColumnIndex("person")));
				sms.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				sms.setBody(cursor.getString(cursor.getColumnIndex("body")));
				sms.setDate(new Date(cursor.getLong(cursor.getColumnIndex("date"))));
				sms.setType(cursor.getInt(cursor.getColumnIndex("type")));
				
				smsList.add(sms);
			}while(cursor.moveToNext());
		}
		
		return smsList;
	}
}

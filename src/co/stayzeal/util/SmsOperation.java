package co.stayzeal.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.stayzeal.contact.model.SmsInfo;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

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
	
	public static String[] SMS_COLUMNS = new String[] { "_id", // 0  
        "thread_id", // 1  
        "address", // 2  
        "person", // 3  
        "date", // 4  
        "body", // 5  
        "read", // 6; 0:not read 1:read; default is 0  
        "type", // 7; 0:all 1:inBox 2:sent 3:draft 4:outBox 5:failed  
                // 6:queued  
        "service_center" // 8
        };  

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
		cursor.close();
		return smsList;
	}
	
	/**
	 * 获取每一个联系人的短信总数，和短信片段
	 * @param number
	 * @return
	 */
	public List<SmsInfo> getThreads(int number){
		smsList = new ArrayList<SmsInfo>();
		String[] projection = new String[] { "thread_id","msg_count", "snippet" };  
		Cursor cursor=context.getContentResolver().query(Uri.parse(CONTENT_URI_SMS_CONVERSATIONS), projection, null, null, null);
		if(cursor.moveToFirst()){
			do{
				SmsInfo sms=new SmsInfo(cursor.getString(0),cursor.getInt(1),cursor.getString(2));
				 
				smsList.add(sms);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return smsList;
	}
	
	public List<SmsInfo> getThreadNum(List<SmsInfo> smsPara){
		//System.out.println(smsPara.size());
		String[] projection = SMS_COLUMNS;
		List<SmsInfo> l=new ArrayList<SmsInfo>();
		for (SmsInfo smsInfo : smsPara) {
			//System.out.println("getThreadNum-->threadId: "+smsInfo.getThreadId());
			Cursor cursor=context.getContentResolver().query(Uri.parse(CONTENT_URI_SMS), 
					                                           projection, 
					                                           "thread_id="+smsInfo.getThreadId(),
					                                           null, 
					                                           null);
			
			if(cursor.moveToFirst()){ 
				
				smsInfo.setAddress(cursor.getString(2));
				smsInfo.setDate(new Date(cursor.getLong(4)));
				smsInfo.setRead(cursor.getInt(6));
				Cursor cursorName=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ "display_name", "sort_key",
    							"contact_id", "data1" }, 
                        "data1="+smsInfo.getAddress(), 
                        null, 
                        null);
				if(cursorName.moveToFirst()){
					smsInfo.setContactName(cursorName.getString(0));
				}
				l.add(smsInfo);
				cursorName.close();
		}
	    
		cursor.close();
				
		}
		return l;
	}
}

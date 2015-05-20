package co.stayzeal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import co.stayzeal.contact.model.SmsInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * 短信息的相关操作
 * 
 * @author YOUNG
 *
 */
public class SmsOperation {

	private static final String TAG = "SmsOperation";
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
        "thread_id",  // 1  
        "address",    // 2  
        "person",     // 3  
        "date",       // 4  
        "body",       // 5  
        "read",       // 6; 0:not read 1:read; default is 0  
        "type",       // 7; 0:all 1:inBox 2:sent 3:draft 4:outBox 5:failed   6:queued  
        "service_center" // 8
        };  

	@SuppressLint("NewApi")
	public SmsOperation(Context context) {
		this.context = context;
		//需要用到新的api
		//this.uri = Telephony.Sms.CONTENT_URI;  
		this.uri = Telephony.Sms.CONTENT_URI;
	}
	
	/**
	 * 获取收件箱所有的信息，默认每一条信息为一天记录，相同联系人不合并
	 * @return
	 */
	public List<SmsInfo> getSmsInfoList() {
		Long start = System.currentTimeMillis();
		smsList = new ArrayList<SmsInfo>();
		String[] projection = new String[] { "_id", "address", "person","body", "date", "type" };  
		Cursor cursor=context.getContentResolver().query(Uri.parse(CONTENT_URI_SMS_INBOX), projection, null, null, "date desc");
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
		Long end = System.currentTimeMillis();
		Long d = end-start;
		Log.i(TAG+" getSmsInfoList", "用时： "+d);
		return smsList;
	}
	
	/**
	 * 获取每一个联系人的短信总数，和短信片段
	 * @param number
	 * @return
	 */
	@SuppressLint("NewApi")
	public List<SmsInfo> getThreads(int number){
		Long start = System.currentTimeMillis();
		smsList = new ArrayList<SmsInfo>();
		String[] projection = new String[] { "thread_id","msg_count", "snippet" };  
		//Cursor cursor=context.getContentResolver().query(Uri.parse(CONTENT_URI_SMS_CONVERSATIONS), projection, null, null,null);
		Cursor cursor=context.getContentResolver().query(Telephony.Sms.Conversations.CONTENT_URI, projection, null, null,null);
		
		if(cursor.moveToFirst()){
			do{
				SmsInfo sms=new SmsInfo(cursor.getString(0),cursor.getInt(1),cursor.getString(2));
				 //sms.setContactName(cursor.getString(3));
				//sms.setDate(new Date(cursor.getLong(3)));
				smsList.add(sms);
			}while(cursor.moveToNext());
		}
		cursor.close();
		Long end = System.currentTimeMillis();
		Long d = end-start;
		Log.i(TAG+" getThreads", "用时： "+d);
		return smsList;
	}
	
	public List<SmsInfo> getThreadNum(List<SmsInfo> smsPara){
		Long start = System.currentTimeMillis();
		//System.out.println(smsPara.size());
		String[] projection = SMS_COLUMNS;
		List<SmsInfo> l=new ArrayList<SmsInfo>();
		int flag;  //记录最新的短息
		for (SmsInfo smsInfo : smsPara) {
			System.out.println("getThreadNum-->threadId: "+smsInfo.getThreadId());
			/**
			 * 当有草稿时，这里会崩溃，？？？待解决
			 */
			Cursor cursor=context.getContentResolver().query(Uri.parse(CONTENT_URI_SMS), 
					                                           projection, 
					                                           "thread_id="+smsInfo.getThreadId(),
					                                           null, 
					                                           "date desc");
			flag=0;
			if(cursor.moveToFirst()){ 
				
				smsInfo.setAddress(cursor.getString(2));
				smsInfo.setDate(new Date(cursor.getLong(4)));
				smsInfo.setRead(cursor.getInt(6));
//System.out.println(smsInfo.getAddress());
				Cursor cursorName = context.getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						new String[]{ "display_name", "sort_key","contact_id", "data1" }, 
                        "data1="+smsInfo.getAddress(), 
                        null, 
                        null);
				if(cursorName.moveToFirst()){
					smsInfo.setContactName(cursorName.getString(0));
				}
				if(flag==0){
					smsInfo.setLastDate(new Date(cursor.getLong(4)));
					flag=1;
				}
				l.add(smsInfo);
				cursorName.close();
		}
	    
		cursor.close();
				
		}
		
		/*
		 * 排序
		 */
		Collections.sort(l, new Comparator<SmsInfo>() {

			@Override
			public int compare(SmsInfo lhs, SmsInfo rhs) {
			        try {
			        	Date dt1=lhs.getLastDate();
			        	Date dt2=rhs.getLastDate();
			            if (dt1.getTime() < dt2.getTime()) {
//System.out.println(dt1.toString()+"在后: "+dt2.toString());
			                return 1;
			            } else if (dt1.getTime() > dt2.getTime()) {
//System.out.println(dt1.toString()+"在前: "+dt2.toString());
			                return -1;
			            } else {
			                return 0;
			            }
			        } catch (Exception exception) {
			            exception.printStackTrace();
			        }
			        return 0;
			    }
             
        });
		
		Long end = System.currentTimeMillis();
		Long d = end-start;
		Log.i(TAG+" getThreadNum", "用时： "+d);
		
		return l;
	}
	
	public String sentSms(String destinationAddress ,String smsContent){
		SmsManager smsManager = SmsManager.getDefault();
		if(smsContent.length()>70){
			List<String> contents = smsManager.divideMessage(smsContent);
			for(int i=0;i<contents.size();i++){
				smsManager.sendTextMessage(destinationAddress, null, contents.get(i), null, null);
			}
		}else{
			smsManager.sendTextMessage(destinationAddress, null, smsContent, null, null);
		}
		return "send msg success!";
	}
	
	/**
	 * 通过电话号码 （address）获取sms conversion 的thread id。
	 * @param address
	 * @return thread_id
	 */
	public String getSmsConversationByAddress(String address){
		Log.i(TAG, "getSmsConversationByAddress-->");
		String selection="address="+address;
		Cursor cursor = context.getContentResolver().query(Uri.parse(CONTENT_URI_SMS), new String[]{"thread_id"}, selection, null, null);
		String threadId = null;
		if(cursor.moveToFirst()){
			threadId = cursor.getString(0);
		}
		Log.i(TAG, "thread id : " + threadId);
		return threadId;
	}
}

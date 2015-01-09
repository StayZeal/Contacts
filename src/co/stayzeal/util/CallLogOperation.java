package co.stayzeal.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import co.stayzeal.contact.model.CallLogInfo;

/**
 * 关于通话记录的操作 
 * @author ArthorK
 *
 */
public class CallLogOperation {

	private Context context;
	private List<CallLogInfo> callLogList;
	private Uri uri;

	public CallLogOperation(Context context) {
		this.context = context;
		this.uri = CallLog.Calls.CONTENT_URI;
	}

	/**
	 * 获取所有的通话记录
	 * 
	 * @return
	 */
	public List<CallLogInfo> getCallLogList() {

		callLogList = new ArrayList<CallLogInfo>();
		// 查询的列
		String[] projection = { CallLog.Calls.DATE, // 日期
				CallLog.Calls.NUMBER, // 号码
				CallLog.Calls.TYPE, // 类型
				CallLog.Calls.CACHED_NAME, // 名字
				CallLog.Calls._ID, // id
				CallLog.Calls.DURATION };
		Cursor cursor = context.getContentResolver().query(uri, projection,
				null, null, null);
		if (cursor.moveToFirst()) {
			do {
				CallLogInfo callLogInfo = new CallLogInfo();

				callLogInfo.setId(cursor.getLong(cursor
						.getColumnIndex(CallLog.Calls._ID)));
				callLogInfo.setName(cursor.getString(cursor
						.getColumnIndex(CallLog.Calls.CACHED_NAME)));
				callLogInfo.setCallType(cursor.getInt(cursor
						.getColumnIndex(CallLog.Calls.TYPE)));
				// callLogInfo.setCallDate(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
				callLogInfo.setCallDate(new Date(cursor.getLong(cursor
						.getColumnIndex(CallLog.Calls.DATE))));
				callLogInfo.setCallDuration(cursor.getInt(cursor
						.getColumnIndex(CallLog.Calls.DURATION)));
				callLogInfo.setPhone(cursor.getString(cursor
						.getColumnIndex(CallLog.Calls.NUMBER)));
				callLogList.add(callLogInfo);
			} while (cursor.moveToNext());
		}
		// 关闭游标
		cursor.close();

		return callLogList;
	}

	/**
	 * 获取一个联系人（准确的说是一个电话号码）的所有通话记录。
	 * 
	 * @param phone
	 * @return
	 */
	public List<CallLogInfo> getCallLogByPhone(String phone){
		 callLogList=new ArrayList<CallLogInfo>();
		// 查询的列  
	        String[] projection = { CallLog.Calls.DATE, // 日期  
	                CallLog.Calls.NUMBER, // 号码  
	                CallLog.Calls.TYPE, // 类型  
	                CallLog.Calls.CACHED_NAME, // 名字  
	                CallLog.Calls._ID, // id
	                CallLog.Calls.DURATION
	        }; 
	        String selection="CallLog.Calls.NUMBER=?";
	        String[] selectionArgs={phone};
	        String sortOrder=null;
		 Cursor cursor=context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
		 do{
			 CallLogInfo callLogInfo=new CallLogInfo();
			 callLogInfo.setId(cursor.getLong(cursor
						.getColumnIndex(CallLog.Calls._ID)));
			 callLogInfo.setName(cursor.getString(cursor
						.getColumnIndex(CallLog.Calls.CACHED_NAME)));
			 callLogInfo.setCallType(cursor.getInt(cursor
					.getColumnIndex(CallLog.Calls.TYPE)));
			 // callLogInfo.setCallDate(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
			 callLogInfo.setCallDate(new Date(cursor.getLong(cursor
					.getColumnIndex(CallLog.Calls.DATE))));
			 callLogInfo.setCallDuration(cursor.getInt(cursor
					.getColumnIndex(CallLog.Calls.DURATION)));
			 callLogInfo.setPhone(cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.NUMBER)));
			 callLogList.add(callLogInfo);
		 }while(cursor.moveToNext());
		return callLogList;
	}

	/**
	 * 获取所有未接来电。
	 * 
	 * @return
	 */
	public List<CallLogInfo> getMissedCallLogList() {

		return callLogList;
	}
}

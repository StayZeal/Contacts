package co.stayzeal.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import co.stayzeal.contact.model.CallLogInfo;

public class CallLogOperation {
	
	private Context context;
	private List<CallLogInfo>  callLogList;
	
	public  CallLogOperation(Context context){
		this.context=context;
	}
	
	public List<CallLogInfo> getCallLogList(){
		
		callLogList=new ArrayList<CallLogInfo>();
		
		Uri uri=CallLog.Calls.CONTENT_URI; 
		// 查询的列  
        String[] projection = { CallLog.Calls.DATE, // 日期  
                CallLog.Calls.NUMBER, // 号码  
                CallLog.Calls.TYPE, // 类型  
                CallLog.Calls.CACHED_NAME, // 名字  
                CallLog.Calls._ID, // id  
        };  
        Cursor cursor=context.getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){
        	do{
        		CallLogInfo callLogInfo=new CallLogInfo();
        		 
        		callLogInfo.setId(cursor.getLong(cursor.getColumnIndex(CallLog.Calls._ID)));
        		callLogInfo.setName(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
        		callLogInfo.setCallType(cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)));
        		//callLogInfo.setCallDate(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
        		callLogInfo.setCallDate(new Date(cursor.getLong(cursor  
                        .getColumnIndex(CallLog.Calls.DATE))));
        		
        		callLogInfo.setPhone(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
        		callLogList.add(callLogInfo);
        	}while(cursor.moveToNext());
        }
        //关闭游标
        cursor.close();
        
		return callLogList;
	}
}

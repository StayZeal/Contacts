package co.stayzeal.contact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.stayzeal.contact.model.SmsInfo;
import co.stayzeal.util.SmsOperation;
import co.stayzea.contact.adapter.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
/**
 * 参考 ：http://blog.csdn.net/wwj_748/article/details/19984941
 * @author ArthorK
 *
 */
public class ShowMsg extends Activity {

	private static final String TAG = "ShowMSg";
	private ListView msgConListView;
	private List<SmsInfo> dataSource;
	private AsyncQueryHandler asyncQuery;  
	private MsgConversationAdapter myAdapter;
	private List<Integer> viewTypeList;
	private String phoneNummber;
	private Button sendBtn;
	private EditText msgContent;
	private SmsOperation smsOperation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_msg);
		
		init();
	}
	
	/**
	 * 进行Activity的初始化
	 */
	public void  init(){
		
		msgConListView=(ListView) findViewById(R.id.msg_conversation_list);
		sendBtn=(Button) findViewById(R.id.show_msg_sent_btn);
		msgContent=(EditText) findViewById(R.id.show_msg_content_edit);
		dataSource=new ArrayList<SmsInfo>();
		viewTypeList=new ArrayList<Integer>();
		asyncQuery=new MsgAsynQueryHandler(getContentResolver());
		Uri uri = Uri.parse("content://sms");  
		Intent intent=getIntent();
		Bundle b=intent.getBundleExtra("bundle");
		String threadId=b.getString("threadId");
		String title=b.getString("title");
		phoneNummber = b.getString("address");
        String[] projection = new String[] { "date", "address", "person","body", "type" }; // 查询的列  
        
        setTitle(title);
        
        asyncQuery.startQuery(0, null, uri, projection,"thread_id = " + threadId, null, "date asc"); 
 
        myAdapter=new MsgConversationAdapter(this, dataSource,viewTypeList,2);
        msgConListView.setAdapter(myAdapter);
        Log.w(TAG, "init dataSource size : "+dataSource.size());
        
        smsOperation = new SmsOperation(this);
        sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String destinationAddress = phoneNummber;
				String smsContent = msgContent.getText().toString().trim();
				smsOperation.sentSms(destinationAddress, smsContent);
				msgContent.clearComposingText();
				myAdapter.notifyDataSetChanged();
			}
		});
 
	}
	/**
	 * 通过dataSource中的type来，设置viewTypeList：因为getItemViewType (int position)
	 * 方法的返回值必须是 从0开始
	 */
	private void getViewTypeList(List<SmsInfo> dataSource){
		for(int i=0;i<dataSource.size();i++){
			switch (dataSource.get(i).getType()) {
			case 1:
				viewTypeList.add(MsgConversationAdapter.MSG_RECEIVE_TYPE);
				break;
			case 2:
				viewTypeList.add(MsgConversationAdapter.MSG_SENT_TYPE);
				break;
			default:
				break;
			}
		}
//		for(Integer i:viewTypeList){
//			System.out.println("getViewTypeList: "+i);
//		}
	}
	
	
	
	
	/**
	 *  异步查询数据库的类 
	 * @author ArthorK
	 *
	 */
    @SuppressLint("HandlerLeak")
	private class MsgAsynQueryHandler extends AsyncQueryHandler {  
  
		public MsgAsynQueryHandler(ContentResolver cr) {  
            super(cr);  
        }  
  
        @Override  
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {  
            if (cursor != null && cursor.getCount() > 0) {  
                cursor.moveToFirst();  
                for (int i = 0; i < cursor.getCount(); i++) {  
                    cursor.moveToPosition(i);  
                    if (cursor.getInt(cursor.getColumnIndex("type")) == 1) {// 接收的信息  
                        SmsInfo d = new SmsInfo();  
                        d.setBody(cursor.getString(cursor.getColumnIndex("body")));
                        d.setDate(new Date(cursor.getLong(cursor  
                            .getColumnIndex("date"))));
                        d.setAddress(cursor.getString(cursor  
                                .getColumnIndex("address")));
                        d.setType(1);
                        dataSource.add(d);  
                    } else { // 发送的信息  
                    	 SmsInfo d = new SmsInfo();  
                         d.setBody(cursor.getString(cursor.getColumnIndex("body")));
                         d.setDate(new Date(cursor.getLong(cursor  
                             .getColumnIndex("date"))));
                         d.setAddress(cursor.getString(cursor  
                                 .getColumnIndex("address")));
                         d.setType(2);  
                        dataSource.add(d);  
                    }  
                }  
 
            }  
 
            getViewTypeList(dataSource);
        }  
    }  
}

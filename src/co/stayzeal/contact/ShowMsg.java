package co.stayzeal.contact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import co.stayzeal.contact.model.SmsInfo;
import co.stayzeal.util.DateFormatUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * http://blog.csdn.net/wwj_748/article/details/19984941
 * @author ArthorK
 *
 */
public class ShowMsg extends Activity {

	private ListView msgConListView;
	private List<SmsInfo> dataSource;
	private AsyncQueryHandler asyncQuery;  
	private MyAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_msg);
		init();
	}
	
	public void  init(){
		msgConListView=(ListView) findViewById(R.id.msg_conversation_list);
		
		Intent intent=getIntent();
		Bundle b=intent.getBundleExtra("bundle");
		String threadId=b.getString("threadId");
		dataSource=new ArrayList<SmsInfo>();
		asyncQuery=new MessageAsynQueryHandler(getContentResolver());
		
		Uri uri = Uri.parse("content://sms");  
        String[] projection = new String[] { "date", "address", "person",  
                "body", "type" }; // 查询的列  
        asyncQuery.startQuery(0, null, uri, projection,  
                "thread_id = " + threadId, null, "date asc"); 
//        for (SmsInfo s : dataSource) {
//			
//			if (s.getType()==1) {
//				System.out.println("收到："+s.getBody());
//			}else  if(s.getType()==2){
//				System.out.println("发出："+s.getBody());
//			}else{
//				System.out.println(s.getType());
//			}
//		}
        myAdapter=new MyAdapter(this, dataSource);
        msgConListView.setAdapter(myAdapter);
        System.out.println(dataSource.size());
for (SmsInfo s : dataSource) {
			System.out.println("test");
			if (s.getType()==1) {
				System.out.println("收到："+s.getBody());
			}else  if(s.getType()==2){
				System.out.println("发出："+s.getBody());
			}else{
				System.out.println(s.getType());
			}
		}
	}
    
	private class MyAdapter extends BaseAdapter{

		private Context context;
		private List<SmsInfo> dataSource;
		
		public MyAdapter(Context context,List<SmsInfo> dataSource) {
			this.context=context;
			this.dataSource=dataSource;
		}
		
		@Override
		public int getCount() {
			return this.dataSource.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView==null){
				viewHolder=new ViewHolder();
				switch (dataSource.get(position).getType()) {
				case 1:
					convertView=LayoutInflater.from(context).inflate(R.layout.msg_conversion_receive_item, null);
					viewHolder.msgBody=(TextView) convertView.findViewById(R.id.msg_receive_body);
					viewHolder.msgDate=(TextView) convertView.findViewById(R.id.msg_receive_row_date);
					break;
				case 2:
					convertView=LayoutInflater.from(context).inflate(R.layout.msg_conversation_sent_item, null);
					viewHolder.msgBody=(TextView) convertView.findViewById(R.id.msg_sent_body);
					viewHolder.msgDate=(TextView) convertView.findViewById(R.id.msg_sent_row_date);
				default:
					break;
				}
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			
			viewHolder.msgBody.setText(dataSource.get(position).getBody());
			viewHolder.msgDate.setText(DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(),"yy-MM-dd"));
			System.out.println(dataSource.size());
			System.out.println(dataSource.get(position).getDate()+": "+DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(),"yy-MM-dd"));
			return convertView;
		}
		
	}
	
	private static class ViewHolder{
		TextView msgBody;
		TextView msgDate;
	}
	
	/**
	 *  异步查询数据库的类 
	 * @author ArthorK
	 *
	 */
    @SuppressLint("HandlerLeak")
	private class MessageAsynQueryHandler extends AsyncQueryHandler {  
  
		public MessageAsynQueryHandler(ContentResolver cr) {  
            super(cr);  
        }  
  
        @Override  
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {  
            if (cursor != null && cursor.getCount() > 0) {  
                cursor.moveToFirst();  
                for (int i = 0; i < cursor.getCount(); i++) {  
                    cursor.moveToPosition(i);  
//                    String date = sdf.format(new Date(cursor.getLong(cursor  
//                            .getColumnIndex("date"))));  
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
//                if (datasource.size() > 0) {  
//                    talkView.setAdapter(new MessageBoxListAdapter(  
//                            MessageBoxList.this, messages));  
//                    talkView.setDivider(null);  
//                    talkView.setSelection(messages.size());  
//                } else {  
//                    Toast.makeText(MessageBoxList.this, "没有短信进行操作",  
//                            Toast.LENGTH_SHORT).show();  
//                }  
            }  
            super.onQueryComplete(token, cookie, cursor);  
        }  
    }  
}

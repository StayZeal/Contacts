package co.stayzeal.contact;

import java.util.List;
import java.util.concurrent.ExecutionException;

import co.stayzeal.contact.model.SmsInfo;
import co.stayzeal.util.LoadSmsTask;
import co.stayzeal.util.SmsOperation;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 短信息Fragment
 * 异步加载短信列表
 * @author ArthorK
 *
 */
public class MessageFragment extends Fragment {

	private static final String TAG="MessageFragment";
	private String TITLE_NAME="短信";
	
	private ListView msgListView;
	private MyAdapter myAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.message_fragment, container, false);
		msgListView=(ListView) view.findViewById(R.id.msg_list);
		//init();
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		 menu.add(1, 1, 1, "写短信");
		 menu.add(2, 1, 2, "设置");
		 //menu.add("Menu 1a").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // menu.add("Menu 1b").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case 1:
			Intent intent = new Intent();
			intent.setClass(getActivity(), SentMsgActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		init();
	}

	public void init(){
		Log.w(TAG, "init() start");
		new SmsAsyncTask().execute(getActivity());
		Log.w(TAG, "init() end");
	}
	
	private  static class ViewHolder{
		ImageView msgIcon;
		TextView nameOrPhone;
		TextView msgShort;   //短信简要
	}
	
	/**
	 * 自定义适配器
	 * @author ArthorK
	 *
	 */
	private class MyAdapter  extends BaseAdapter{

		private Context context;
		private List<SmsInfo> dataSource;
		
		public MyAdapter(Context context,List<SmsInfo> dataSource) {
			this.context=context;
			this.dataSource=dataSource;
		}
		
		@Override
		public int getCount() {
			return dataSource.size();
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
				convertView=LayoutInflater.from(context).inflate(R.layout.msg_list_item, null);
				viewHolder=new ViewHolder();
				viewHolder.msgIcon=(ImageView) convertView.findViewById(R.id.msg_icon);
				viewHolder.nameOrPhone=(TextView) convertView.findViewById(R.id.msg_name_or_phone);
				viewHolder.msgShort=(TextView) convertView.findViewById(R.id.msg_short);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			viewHolder.msgIcon.setBackgroundResource(R.drawable.xiaoxin);
			viewHolder.msgShort.setText(dataSource.get(position).getSnippe());
//System.out.println(dataSource.get(position).getSnippe());
            if(dataSource.get(position).getContactName()!=null){
            	viewHolder.nameOrPhone.setText(dataSource.get(position).getContactName());
            }else{
            	viewHolder.nameOrPhone.setText(dataSource.get(position).getAddress());
            }
			//viewHolder.nameOrPhone.setText(dataSource.get(position).getContactName()+" "+dataSource.get(position).getAddress()+"--"+dataSource.get(position).getMsgCount());
			
			/**
			 * 设置监听器
			 */
			convertView.setOnClickListener(new MsgItemClickListener(dataSource.get(position)));
			
			return convertView;
		}
		
	}
	
	private class MsgItemClickListener implements OnClickListener{
		private SmsInfo smsInfo;
		
		public MsgItemClickListener(SmsInfo smsInfo){
			this.smsInfo=smsInfo;
		}
		
		@Override
		public void onClick(View v) {
			Intent intent =new Intent();
			Bundle bundle=new Bundle();
			if(smsInfo!=null){
				if(smsInfo.getContactName()!=null){
					bundle.putString("title", smsInfo.getContactName());;
				}else{
					bundle.putString("title", smsInfo.getAddress());
				}
				bundle.putString("address", smsInfo.getAddress());
				bundle.putString("threadId", smsInfo.getThreadId());
			}else{
				System.out.println("MsgItemClickListener smsInfo 为Null");
			}
			
			intent.putExtra("bundle", bundle);
			intent.setClass(getActivity(), ShowMsg.class);
			startActivity(intent);
		}
		
	}

	public class SmsAsyncTask extends AsyncTask<Context, Void, List<SmsInfo>> {

		@Override
		protected List<SmsInfo> doInBackground(Context... params) {
			SmsOperation smsOperation = new SmsOperation(params[0]);
			List<SmsInfo> threads = smsOperation.getThreads(0);
			List<SmsInfo> dataSource = smsOperation.getThreadNum(threads);
			return dataSource;
		}

		@Override
		protected void onPostExecute(List<SmsInfo> result) {
			myAdapter=new MyAdapter(getActivity(),result);
			msgListView.setAdapter(myAdapter);
		}

	}
}

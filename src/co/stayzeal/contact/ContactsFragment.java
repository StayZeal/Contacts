package co.stayzeal.contact;

import java.util.ArrayList;
import java.util.List;

import co.stayzeal.contact.model.ContactInfo;
import co.stayzeal.util.ContactDBOperaion;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ContactsFragment extends Fragment {

	private static final String TAG = "ContactsFragment";
	//private Context context;
	private ListView contactList;
	private List<ContactInfo> dataList;
	private MyAdapter myAdapter;
	private int contactsCount=0;
	private ContactDBOperaion con;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.contacts_fragment,container,false);
		contactList=(ListView) view.findViewById(R.id.contactListView);
		init();
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add("添加").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Log.w(TAG, "点击了添加菜单项");
			Intent intent = new Intent();
			Bundle b = new Bundle();
			b.putString("operation", "1");//1代表添加，0代表修改
			b.putString("title", "添加联系人");
			intent.putExtra("b", b);
			intent.setClass(getActivity(), EditContactActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
    
	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.i(TAG, "onHiddenChanged()--->start");
		super.onHiddenChanged(hidden);
		if(isHidden()==false){
			Log.i(TAG, "is show");
			/*dataList.clear();
			getData();
			myAdapter.notifyDataSetChanged();*/
		}
	}

	private void init(){
		Log.w(getClass().getName()+" init(): ", "start");
		//getData();
		dataList =new ArrayList<ContactInfo>();
		con=new ContactDBOperaion(getActivity());
		contactsCount=con.getContactsCount();
		con.getContactsList(dataList, 0, 10);
		myAdapter=new MyAdapter();
		contactList.setAdapter(myAdapter);
		contactList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				
				Log.i("contactList.setOnScrollListener firstVisibleItem",String.valueOf(firstVisibleItem));
				Log.i("contactList.setOnScrollListener visibleItemCount",String.valueOf(visibleItemCount));
				Log.i("contactList.setOnScrollListenerr totalItemCount",String.valueOf(totalItemCount));
				if(totalItemCount<contactsCount){
					if(firstVisibleItem+visibleItemCount==totalItemCount){
						con.getContactsList(dataList, firstVisibleItem, visibleItemCount);
					}
				}
				
			}
		});
		Log.w(getClass().getName()+" init(): ", "end");
	}
	
	/*private void getData(){
		Log.w(this.getClass().getName()+" getData()", "start");
		//dataList=new ArrayList<ContactInfo>();
		//ContactOpreation con=new ContactOpreation(getActivity());
		ContactDBOperaion con=new ContactDBOperaion(getActivity());
		contactsCount=con.getContactsCount();
		dataList=con.getContactsList();
		Log.w(this.getClass().getName()+" getData()", "end");
	}*/
	
	private class MyAdapter extends BaseAdapter{
		
		private LayoutInflater mInflater;
		
		public MyAdapter() {
		    this.mInflater=LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			return dataList.size();
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
				convertView=mInflater.inflate(R.layout.contact_list_item, null);
				viewHolder=new ViewHolder();
				viewHolder.contactIcon=(ImageView) convertView.findViewById(R.id.contactIcon);
				viewHolder.contactName=(TextView) convertView.findViewById(R.id.contactName);
				viewHolder.contactNumber=(TextView) convertView.findViewById(R.id.contactNumber);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			/**
			 * 如果有头像则显示，没有怎么置null
			 */
			if(dataList.get(position).getContactIcon()!=null){
				viewHolder.contactIcon.setImageBitmap(dataList.get(position).getContactIcon());;
			}else{
				viewHolder.contactIcon.setImageResource(R.drawable.contact_avatar_default_nor);;
			}
			viewHolder.contactName.setText((CharSequence) dataList.get(position).getContactName());
			viewHolder.contactNumber.setText((CharSequence) dataList.get(position).getContactNumber());
			
			convertView.setOnClickListener(new ContactListItemClick(position));
			
			return convertView;
		}
		
	}
	
	static class ViewHolder{
		ImageView contactIcon;
		TextView contactName;
		TextView contactNumber;
	}
	
	public class ContactListItemClick implements OnClickListener{
		
		private int position;
		
		public ContactListItemClick(int position) {
			this.position=position;
		}
		@Override
		public void onClick(View v) {
			Intent intent=new Intent();
			Bundle idBundle=new Bundle();
			idBundle.putString("id",String.valueOf(dataList.get(position).getId()));
			idBundle.putString("address", dataList.get(position).getContactNumber());
			idBundle.putString("name", dataList.get(position).getContactName());
			intent.putExtra("idBundle", idBundle);
			//intent.setClass(getActivity(), ContactInfoActivity.class);
			intent.setClass(getActivity(), ShowContactActivity.class);
			startActivity(intent);
			
		}
		
	}
}

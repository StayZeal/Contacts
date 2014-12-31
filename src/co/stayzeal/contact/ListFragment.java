package co.stayzeal.contact;

import java.util.List;

import co.stayzeal.contact.menu.NavigateActivity;
import co.stayzeal.contact.model.ContactInfo;
import co.stayzeal.util.ContactOpreation;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ListFragment extends Fragment {

	//private Context context;
	private ListView contactList;
	private List<ContactInfo> dataList;
	private MyAdapter myAdapter;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.w("onCreate", "Ö´ÐÐ");
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.w("", "onCreateView()Ö´ÐÐ");
		View view=inflater.inflate(R.layout.list_fragment,container,false);
		contactList=(ListView) view.findViewById(R.id.contactListView);
		init();
		return view;
	}
	
	private void init(){
		Log.w(getClass().getName()+" init(): ", "start");
		
		getData();
		myAdapter=new MyAdapter();
		contactList.setAdapter(myAdapter);
		Log.w(getClass().getName()+" init(): ", "end");
	}
	
	private void getData(){
		Log.w(this.getClass().getName()+" getData()", "start");
		//dataList=new ArrayList<ContactInfo>();
		ContactOpreation con=new ContactOpreation(getActivity());
		dataList=con.getContactList();
		Log.w(this.getClass().getName()+" getData()", "end");
	}
	
	private class MyAdapter extends BaseAdapter{
		
		private LayoutInflater mInflater;
		
		public MyAdapter() {
			System.out.println("MyAdapter() start!!!!!!!!!");
		    this.mInflater=LayoutInflater.from(getActivity());
		    System.out.println("MyAdapter() end!!!!!!!!!");
		}

		@Override
		public int getCount() {
			System.out.println("getCount() start!!!!!!!!! "+dataList.size());
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
			Log.w(getClass().getName()+" getView(): ", "start");
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
			if(dataList.get(position).getContactIcon()!=null){
				viewHolder.contactIcon.setImageResource( dataList.get(position).getContactIcon());
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
			idBundle.putString("phoneNumber", dataList.get(position).getContactNumber());
			intent.putExtra("idBundle", idBundle);
			intent.setClass(getActivity(), NavigateActivity.class);	
			startActivity(intent);
			
		}
		
	}
}

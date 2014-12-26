package co.stayzeal.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ListFragment extends Fragment {

	//private Context context;
	private ListView contactList;
	private List<Map<String,Object>> dataList;
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
		dataList=new ArrayList<Map<String,Object>>();
		getData();
		myAdapter=new MyAdapter();
		contactList.setAdapter(myAdapter);
		Log.w(getClass().getName()+" init(): ", "end");
	}
	
	private void getData(){
		Log.w(this.getClass().getName()+" getData()", "start");
		Map<String , Object> map;
		for(int i = 0;i<100;i++ ){
			map=new HashMap<String, Object>();
			map.put("contactName", "name : "+i);
			map.put("contactNumber", "100"+ "i");
			map.put("contactIcon", R.drawable.icon_tab_calllog);
			dataList.add(map);
		}
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
				System.out.println("convert==null");
				convertView=mInflater.inflate(R.layout.contact_list_item,null);
				viewHolder=new ViewHolder();
				viewHolder.contactIcon=(ImageView) convertView.findViewById(R.id.contactIcon);
				viewHolder.contactName=(TextView) convertView.findViewById(R.id.contactName);
				viewHolder.contactNumber=(TextView) convertView.findViewById(R.id.contactNumber);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			
			viewHolder.contactIcon.setImageResource((Integer) dataList.get(position).get("contactIcon"));
			viewHolder.contactName.setText((CharSequence) dataList.get(position).get("contactText"));
			viewHolder.contactNumber.setText((CharSequence) dataList.get(position).get("contactNumber"));
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent();
					intent.setClass(getActivity(), ContactInfoActivity.class);	
					startActivity(intent);
					}
			});
			
			return convertView;
		}
		
	}
	
	static class ViewHolder{
		ImageView contactIcon;
		TextView contactName;
		TextView contactNumber;
	}
}

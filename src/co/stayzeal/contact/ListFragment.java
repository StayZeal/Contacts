package co.stayzeal.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment {

	private Context context;
	private ListView contactList;
	private List<Map<String,Object>> dataList;
	private MyAdapter myAdapter;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.w("onCreate", "Ö´ÐÐ");
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.w("onCreateView", "Ö´ÐÐ");
		return inflater.inflate(R.layout.list_fragment,container);
	}
	
	private void init(){
		context=getActivity();
		contactList=new ListView(context);
		contactList=(ListView) getActivity().findViewById(R.id.contactListView);
		
		dataList=new ArrayList<Map<String,Object>>();
		getData();
		myAdapter=new MyAdapter();
		contactList.setAdapter(myAdapter);
	}
	
	private void getData(){
		Map<String , Object> map;
		for(int i = 0;i<100;i++ ){
			map=new HashMap<String, Object>();
			map.put("contactName", "name : "+i);
			map.put("contactNumber", "100"+ "i");
			map.put("contactIcon", R.drawable.ic_launcher);
			dataList.add(map);
		}
	}
	
	private class MyAdapter extends BaseAdapter{
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder;
			
			if(convertView==null){
				convertView=LayoutInflater.from(getActivity()).inflate(R.layout.contact_list_item, parent);
				viewHolder=new ViewHolder();
				viewHolder.contactIcon=(ImageView) getActivity().findViewById(R.id.contactIcon);
				viewHolder.contactName=(TextView) getActivity().findViewById(R.id.contactName);
				viewHolder.contactNumber=(TextView) getActivity().findViewById(R.id.contactNumber);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			
			viewHolder.contactIcon.setImageResource((Integer) dataList.get(position).get("contactIcon"));
			viewHolder.contactName.setText((CharSequence) dataList.get(position).get("contactText"));
			viewHolder.contactNumber.setText((CharSequence) dataList.get(position).get("contactNumber"));
			
			return convertView;
		}
		
	}
	
	static class ViewHolder{
		ImageView contactIcon;
		TextView contactName;
		TextView contactNumber;
	}
}

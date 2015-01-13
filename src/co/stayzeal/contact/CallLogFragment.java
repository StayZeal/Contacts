package co.stayzeal.contact;

import java.util.List;

import org.w3c.dom.Text;

import co.stayzeal.contact.R;
import co.stayzeal.contact.constant.MyColor;
import co.stayzeal.contact.model.CallLogInfo;
import co.stayzeal.util.CallLogOperation;
import co.stayzeal.util.DateFormatUtil;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CallLogFragment extends Fragment {
	
	private MyAdapter myAdapter;
	private ListView listView;
	private List<CallLogInfo> callList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =inflater.inflate(R.layout.call_log_fragment, container, false);
		listView=(ListView) view.findViewById(R.id.call_log_list);
		init();
		return view;
		
		
		
	}
	
	public void init(){
		
		
		//http://blog.csdn.net/wwj_748/article/details/19970271
		
		CallLogOperation c=new CallLogOperation(getActivity());
	    callList= c.getCallLogList();
	    
		myAdapter=new MyAdapter(getActivity());
		listView.setAdapter(myAdapter);
		
	}
	
	private class MyAdapter extends BaseAdapter{

		private Context context;
		
		public MyAdapter(Context context) {
			this.context=context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return callList.size();
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
				convertView=LayoutInflater.from(context).inflate(R.layout.call_log_list_item, null);
				viewHolder=new ViewHolder();
				viewHolder.nameOrNumber=(TextView) convertView.findViewById(R.id.name_or_number);
				viewHolder.provinceAndCity=(TextView) convertView.findViewById(R.id.province_city);
				viewHolder.callDate=(TextView) convertView.findViewById(R.id.call_log_date);
				//viewHolder.callType=(TextView) convertView.findViewById(R.id.call_type);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			
			if(callList.get(position).getName()!=null){
				viewHolder.nameOrNumber.setText(callList.get(position).getName());
			}else{
				viewHolder.nameOrNumber.setText(callList.get(position).getPhone());
			}
			viewHolder.callDate.setText(DateFormatUtil.toYyMmDd(callList.get(position).getCallDate(), "yy/MM/dd"));
			//viewHolder.callType.setText(callList.get(position).getCallType());
			String callType="";
			switch (callList.get(position).getCallType()) {
			case 1:
				callType="未接";
				viewHolder.nameOrNumber.setTextColor(MyColor.COLOR_CONTACT_PHONE_UN_RECEIVE);
				viewHolder.provinceAndCity.setTextColor(MyColor.COLOR_CONTACT_PHONE_UN_RECEIVE);
				break;
			case 2:
				callType="已接";
				break;
			default:
				break;
			}
			viewHolder.provinceAndCity.setText(callType);
			return convertView;
		}
		
	}
	private static class ViewHolder{
		TextView nameOrNumber;
		TextView provinceAndCity;
		TextView callDate;
		TextView callType;
	}
}

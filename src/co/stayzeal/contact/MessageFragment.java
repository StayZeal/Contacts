package co.stayzeal.contact;

import java.util.List;

import co.stayzeal.contact.model.SmsInfo;
import co.stayzeal.util.SmsOperation;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 短信息Fragment
 * @author ArthorK
 *
 */
public class MessageFragment extends Fragment {

	private SmsOperation smsOperation;
	private List<SmsInfo> smsList;
	private ListView msgListView;
	private MyAdapter myAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.message_fragment, container, false);
		msgListView=(ListView) view.findViewById(R.id.msg_list);
		init();
		return view;
	}
	
	public void init(){
		smsOperation=new SmsOperation(getActivity());
		smsList=smsOperation.getSmsInfoList();
		for (SmsInfo smsInfo : smsList) {
			System.out.println(smsInfo.getAddress());
			System.out.println(smsInfo.getBody());
			System.out.println(smsInfo.getPerson());
		}
		myAdapter=new MyAdapter(getActivity());
		msgListView.setAdapter(myAdapter);
	}
	
	private  static class ViewHolder{
		ImageView msgIcon;
		TextView nameOrPhone;
		TextView msgShort;   //短信简要
	}
	
	private class MyAdapter  extends BaseAdapter{

		private Context context;
		
		public MyAdapter(Context context) {
			this.context=context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return smsList.size();
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
			viewHolder.msgShort.setText(smsList.get(position).getBody());
			viewHolder.nameOrPhone.setText(smsList.get(position).getAddress());
			return convertView;
		}
		
	}
}

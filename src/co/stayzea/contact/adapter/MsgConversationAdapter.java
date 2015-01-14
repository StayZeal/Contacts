package co.stayzea.contact.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import co.stayzeal.contact.R;
import co.stayzeal.contact.model.SmsInfo;
import co.stayzeal.util.DateFormatUtil;

/**
 * 短信对话窗体Adapter
 * <p>一个ListView显示多种item布局的实现
 * 重写getItemViewType(int position)和 getViewTypeCount() 方法
 * @author ArthorK
 *
 */
public class MsgConversationAdapter extends BaseAdapter{

	private Context context;
	private List<SmsInfo> dataSource;
	private List<Integer> viewTypeList;
	private int viewTypeCount;
	
	
	public static final int MSG_RECEIVE_TYPE=0;
	public static final int MSG_SENT_TYPE=1;
	
	/**
	 * 
	 * @param context
	 * @param dataSource 数据源
	 * @param viewTypeList 
	 * @param viewTypeCount
	 */
	public MsgConversationAdapter(Context context,List<SmsInfo> dataSource,List<Integer> viewTypeList,int viewTypeCount) {
		this.context=context;
		this.dataSource=dataSource;
		this.viewTypeCount=viewTypeCount;
		this.viewTypeList=viewTypeList;
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
	public int getItemViewType(int position) {
		//必须从0开始
		return viewTypeList.get(position);
	}

	@Override
	public int getViewTypeCount() {
		return viewTypeCount;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderSend vHolderSend;
		ViewHolderReceive vHolderReceive;
//		View cViewSend=null;
//		View cViewReceive=null;
//		
//		if(cViewReceive==null){
//			vHolderReceive=new ViewHolderReceive();
//			cViewReceive=LayoutInflater.from(context).inflate(R.layout.msg_conversion_receive_item, null);
//			vHolderReceive.msgBody=(TextView) cViewReceive.findViewById(R.id.msg_receive_body);
//			vHolderReceive.msgDate=(TextView) cViewReceive.findViewById(R.id.msg_receive_row_date);
//			cViewReceive.setTag(cViewReceive);
//		}
//		if(cViewSend==null){
//			vHolderSend=new ViewHolderSend();
//			cViewSend=LayoutInflater.from(context).inflate(R.layout.msg_conversation_sent_item, null);
//			vHolderSend.msgBody=(TextView) cViewSend.findViewById(R.id.msg_sent_body);
//			vHolderSend.msgDate=(TextView) cViewSend.findViewById(R.id.msg_sent_row_date);
//			cViewSend.setTag(vHolderSend);
//		}
//		
//		/*
//		 * 根据sms的类型来选择不同的布局
//		 */
//		switch (dataSource.get(position).getType()) {
//		case 1:
//			convertView=cViewReceive;
//			vHolderReceive=(ViewHolderReceive) cViewReceive.getTag();
//			vHolderReceive.msgBody.setText(dataSource.get(position).getBody());
//			vHolderReceive.msgDate.setText(DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(), "yy-MM-dd"));
//			break;
//		case 2:
//			convertView=cViewSend;
//			vHolderSend=(ViewHolderSend) cViewSend.getTag();
//			vHolderSend.msgBody.setText(dataSource.get(position).getBody());
//			vHolderSend.msgDate.setText(DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(), "yy-MM-dd"));
//			
//			break;
//		default:
//			break;
//		}
		if(convertView==null){
			switch (viewTypeList.get(position)) {
			case MSG_SENT_TYPE:
				convertView=LayoutInflater.from(context).inflate(R.layout.msg_conversation_sent_item, null);
				vHolderSend=new ViewHolderSend();
				vHolderSend.msgBody=(TextView) convertView.findViewById(R.id.msg_sent_body);
				vHolderSend.msgDate=(TextView) convertView.findViewById(R.id.msg_sent_row_date);
				
				vHolderSend.msgBody.setText(dataSource.get(position).getBody());
				vHolderSend.msgDate.setText(DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(), "yy-MM-dd"));
				
				convertView.setTag(vHolderSend);
				break;
			case MSG_RECEIVE_TYPE:
				convertView=LayoutInflater.from(context).inflate(R.layout.msg_conversion_receive_item, null);
				vHolderReceive=new ViewHolderReceive();
				vHolderReceive.msgBody=(TextView) convertView.findViewById(R.id.msg_receive_body);
				vHolderReceive.msgDate=(TextView) convertView.findViewById(R.id.msg_receive_row_date);
				
				vHolderReceive.msgBody.setText(dataSource.get(position).getBody());
				vHolderReceive.msgDate.setText(DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(), "yy-MM-dd"));
				
				convertView.setTag(vHolderReceive);
				break;
			default:
				break;
			}
		}else{
			switch (viewTypeList.get(position)) {
			case MSG_SENT_TYPE:
				vHolderSend=(ViewHolderSend) convertView.getTag();
				
				vHolderSend.msgBody.setText(dataSource.get(position).getBody());
				vHolderSend.msgDate.setText(DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(), "yy-MM-dd"));
				break;
			case MSG_RECEIVE_TYPE:
				vHolderReceive=(ViewHolderReceive) convertView.getTag();
				
				vHolderReceive.msgBody.setText(dataSource.get(position).getBody());
				vHolderReceive.msgDate.setText(DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(), "yy-MM-dd"));
				break;
			default:
				break;
			}
		}
System.out.println(dataSource.size());
System.out.println(dataSource.get(position).getDate()+": "+DateFormatUtil.toYyMmDd(dataSource.get(position).getDate(),"yy-MM-dd"));
		return convertView;
	}
	
//	/**
//	 * 通过dataSource中的type来，设置viewTypeList：因为getItemViewType (int position)
//	 * 方法的返回值必须是 从0开始
//	 */
//	private void getViewTypeList(){
//		viewTypeList=new ArrayList<Integer>();
//		for(int i=0;i<dataSource.size();i++){
//			switch (dataSource.get(i).getType()) {
//			case 1:
//				viewTypeList.add(MSG_RECEIVE_TYPE);
//				break;
//			case 2:
//				viewTypeList.add(MSG_SENT_TYPE);
//				break;
//			default:
//				break;
//			}
//		}
//		for(Integer i:viewTypeList){
//			System.out.println("getViewTypeList: "+i);
//		}
//	}
	
	/**
	 * 显示发送短信息的ViewHolder
	 * @author ArthorK
	 *
	 */
	private static class ViewHolderSend{
		TextView msgBody;
		TextView msgDate;
	}
	/**
	 * 显示接受短信息的ViewHolder
	 * @author ArthorK
	 *
	 */
	private class ViewHolderReceive{
		TextView msgBody;
		TextView msgDate;
	}
}



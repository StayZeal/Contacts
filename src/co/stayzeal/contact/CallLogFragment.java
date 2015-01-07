package co.stayzeal.contact;

import java.util.List;

import co.stayzeal.contact.R;
import co.stayzeal.contact.model.CallLogInfo;
import co.stayzeal.util.CallLogOperation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallLogFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		init();
		return inflater.inflate(R.layout.call_log_fragment, container, false);
		
		
		
	}
	
	public void init(){
		//http://blog.csdn.net/wwj_748/article/details/19970271
		CallLogOperation c=new CallLogOperation(getActivity());
		List<CallLogInfo> callList= c.getCallLogList();
		for(CallLogInfo cc:callList){
			System.out.println(cc.getName()+" "+cc.getPhone()+" "+cc.getCallDate());
		}
	}
	
}

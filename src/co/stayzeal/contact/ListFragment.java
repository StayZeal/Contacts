package co.stayzeal.contact;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListFragment extends Fragment {

	private Context context;
	private ListView contactList;
	
	
	
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
		
	}
	
}

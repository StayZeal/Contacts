package co.stayzeal.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DialFragment extends Fragment {

	private String TITLE_NAME="拨号";
	private EditText dialNumber;
	private Button btn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView-->执行");
		View view=inflater.inflate(R.layout.dial_fragment, container,false);
		dial(view);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("onCreate-->执行");
		super.onCreate(savedInstanceState);
	}

	public void dial(View view){
		
		getActivity().setTitle(TITLE_NAME);
		
		dialNumber=(EditText) view.findViewById(R.id.dial_number);
		btn=(Button) view.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uriString=dialNumber.getText().toString().trim();
				Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:" +uriString));
				startActivity(intent);
			}
		});
	}
}

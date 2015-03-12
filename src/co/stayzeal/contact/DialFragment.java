package co.stayzeal.contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class DialFragment extends Fragment {

	private static final String TAG = "DialFragment";
	private String TITLE_NAME="拨号";
	private EditText dialNumber;
	private Button btn;
	
	private ImageButton dial_0;
	private ImageButton dial_1;
	private ImageButton dial_2;
	private ImageButton dial_3;
	private ImageButton dial_4;
	private ImageButton dial_5;
	private ImageButton dial_6;
	private ImageButton dial_7;
	private ImageButton dial_8;
	private ImageButton dial_9;
	private ImageButton dial_asterisk;
	private ImageButton dial_pound;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView-->执行");
		View view=inflater.inflate(R.layout.dial_fragment, container,false);
		dial(view);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate-->执行");
		super.onCreate(savedInstanceState);
	}

	public void dial(View view){
		
		//getActivity().setTitle(TITLE_NAME);
		
		dialNumber=(EditText) view.findViewById(R.id.dial_number);
		
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
        imm.hideSoftInputFromWindow(dialNumber.getWindowToken(),0); 

		dialNumber.setInputType(InputType.TYPE_NULL); //EditText始终不弹出软件键盘 
		//dialNumber.setFocusable(false);//失去焦点
		
		btn=(Button) view.findViewById(R.id.dial_btn_call);
		dial_0=(ImageButton) view.findViewById(R.id.dial_0);
		dial_1=(ImageButton) view.findViewById(R.id.dial_1);
		dial_2=(ImageButton) view.findViewById(R.id.dial_2);
		dial_3=(ImageButton) view.findViewById(R.id.dial_3);
		dial_4=(ImageButton) view.findViewById(R.id.dial_4);
		dial_5=(ImageButton) view.findViewById(R.id.dial_5);
		dial_6=(ImageButton) view.findViewById(R.id.dial_6);
		dial_7=(ImageButton) view.findViewById(R.id.dial_7);
		dial_8=(ImageButton) view.findViewById(R.id.dial_8);
		dial_9=(ImageButton) view.findViewById(R.id.dial_9);
		dial_asterisk=(ImageButton) view.findViewById(R.id.dial_asterisk);
		dial_pound=(ImageButton) view.findViewById(R.id.dial_pound);
		
		dial_0.setOnClickListener(new dialNumberClickListener("0", dialNumber));
		dial_1.setOnClickListener(new dialNumberClickListener("1", dialNumber));
		dial_2.setOnClickListener(new dialNumberClickListener("2", dialNumber));
		dial_3.setOnClickListener(new dialNumberClickListener("3", dialNumber));
		dial_4.setOnClickListener(new dialNumberClickListener("4", dialNumber));
		dial_5.setOnClickListener(new dialNumberClickListener("5", dialNumber));
		dial_6.setOnClickListener(new dialNumberClickListener("6", dialNumber));
		dial_7.setOnClickListener(new dialNumberClickListener("7", dialNumber));
		dial_8.setOnClickListener(new dialNumberClickListener("8", dialNumber));
		dial_9.setOnClickListener(new dialNumberClickListener("9", dialNumber));
		dial_asterisk.setOnClickListener(new dialNumberClickListener("*", dialNumber));
		dial_pound.setOnClickListener(new dialNumberClickListener("#", dialNumber));
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uriString=dialNumber.getText().toString().trim();
				Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:" +uriString));
				startActivity(intent);
			}
		});
		
	}
	
	class dialNumberClickListener implements OnClickListener{

		private String number;
		private EditText dialNumber;
		
		public dialNumberClickListener(String number,EditText dialNumber) {
			this.number=number;
			this.dialNumber=dialNumber;
		}
		@Override
		public void onClick(View v) {
			dialNumber.append(number);
		}
		
	}
}

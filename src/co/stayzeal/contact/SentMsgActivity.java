package co.stayzeal.contact;

import co.stayzeal.util.SmsOperation;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SentMsgActivity extends Activity {

	private EditText msgSendTo;
	private EditText msgContent;
	private Button msgSendBtn;
	SmsOperation smsOperation;
	String destinationAddress  ;
	String smsContent ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sent_msg);
		
		msgSendTo=(EditText) findViewById(R.id.msg_send_to);
		msgContent=(EditText) findViewById(R.id.msg_content);
		msgSendBtn=(Button) findViewById(R.id.msg_send_btn);
	    smsOperation = new SmsOperation(this);
		
		msgSendBtn.setOnClickListener( new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				destinationAddress = msgSendTo.getText().toString().trim();
				
				smsContent = msgContent.getText().toString().trim();
				if(destinationAddress==null||destinationAddress==" "){
					Toast.makeText(SentMsgActivity.this, "请输入联系人..", Toast.LENGTH_SHORT);
				}else{
System.out.println("destinationAddress: "+destinationAddress);
					if(smsContent==null||smsContent==" "){
						Toast.makeText(SentMsgActivity.this, "请输入短信内容..", Toast.LENGTH_SHORT);
					}else{
System.out.println("smsContent ：" + smsContent);
						smsOperation.sentSms(destinationAddress, smsContent);
						msgContent.clearComposingText();
System.out.println("msg is send ....");
					}
				}
			}
		});
	}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sent_msg, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

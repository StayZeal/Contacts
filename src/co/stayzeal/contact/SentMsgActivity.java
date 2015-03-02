package co.stayzeal.contact;

import co.stayzeal.util.SmsOperation;
import android.app.Activity;
import android.os.Bundle;
import android.provider.Telephony.Sms;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
		destinationAddress = msgSendTo.getText().toString().trim();
		smsContent = msgContent.getText().toString().trim();
		msgSendBtn.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				smsOperation.sentSms(destinationAddress, smsContent);
				System.out.println("msg is send ....");
			}
		});
	}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sent_msg, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

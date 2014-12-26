package co.stayzeal.contact;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactInfoActivity extends Activity {
	
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);
		textView=(TextView) findViewById(R.id.contactInfoText);
		Intent intent=getIntent();
		Bundle b=intent.getBundleExtra("idBundle");
		String s=b.getString("id");
		textView.setText(s);
		
	}

	
}

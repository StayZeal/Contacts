package co.stayzeal.contact;


import co.stayzeal.contact.model.ContactInfo;
import co.stayzeal.util.ContactOpreation;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ContactInfoActivity extends Activity {
	
	private EditText editName;
	private EditText editPhone;
	private EditText editEmail;
	
	private ContactOpreation contactOpreation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);
		
		init(getApplicationContext());
	}
	
	private void init(Context context){
		editName=(EditText) findViewById(R.id.contactActivityEditName);
		editPhone=(EditText) findViewById(R.id.contactActivityEditPhone);
		editEmail=(EditText) findViewById(R.id.contactActivityEditEmail);
		
		contactOpreation=new ContactOpreation(context);
		
		Intent intent=getIntent();
		Bundle b=intent.getBundleExtra("idBundle");
	//	String s=b.getString("id");
		String phoneNumber=b.getString("phoneNumber");
		
		ContactInfo c=contactOpreation.getContactByPhone(phoneNumber);
		c.setContactNumber(phoneNumber);
		editName.setText(c.getContactName());
		editPhone.setText(c.getContactNumber());
		editEmail.setText(c.getEamil());
		
	}
	
}

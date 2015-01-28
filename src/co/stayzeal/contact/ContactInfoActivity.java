package co.stayzeal.contact;


import co.stayzeal.contact.model.ContactInfo;
import co.stayzeal.util.ContactOpreation;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ContactInfoActivity extends Activity {
	
	private String TITLE_NAME="联系人详情";
	
	private TextView textName;
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
		
		setTitle(TITLE_NAME);
		
		textName= (TextView) findViewById(R.id.contact_activity_name);
		editPhone=(EditText) findViewById(R.id.contact_activity_eidt_phone_1);
		editEmail=(EditText) findViewById(R.id.contact_activity_edit_email);
		
		contactOpreation=new ContactOpreation(context);
		
		Intent intent=getIntent();
		Bundle b=intent.getBundleExtra("idBundle");
	//	String s=b.getString("id");
		String phoneNumber=b.getString("phoneNumber");
		
		ContactInfo c=contactOpreation.getContactByPhone(phoneNumber);
		c.setContactNumber(phoneNumber);
		textName.setText(c.getContactName());
		editPhone.setText(c.getContactNumber());
		editEmail.setText(c.getEamil());
		
	}
	
}

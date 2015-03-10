package co.stayzeal.contact;

import co.stayzeal.contact.model.ContactInfo;
import co.stayzeal.util.ContactDBOperaion;
import android.app.Activity;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditContactActivity extends Activity {

	private static final String TAG = "EditContactActivity";
	private ContactDBOperaion dbOpreation;
	private ImageView iconImage;
	private EditText nameEdit;
	private EditText phoneEdit;
	private EditText emailEdit;
	private Button saveBtn;
	private Button cancelBtn;
	private String operation;
	private String name ;
	private String phone;
	private String email;
	private ContactInfo contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		init();
	}
	
	private void init(){
		
		iconImage = (ImageView) findViewById(R.id.contact_edit_icon);
		nameEdit = (EditText) findViewById(R.id.contact_edit_name);
		phoneEdit = (EditText) findViewById(R.id.contact_edit_phone);
		emailEdit = (EditText) findViewById(R.id.contact_edit_email);
		saveBtn = (Button) findViewById(R.id.contact_edit_btn_save);
		cancelBtn = (Button) findViewById(R.id.contact_edit_btn_cancel);
		Intent intent = getIntent();
		Bundle b= intent.getBundleExtra("b");
		String title = b.getString("title");
		operation = b.getString("operation");
		
		setTitle(title);

		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				name = nameEdit.getText().toString().trim();
				phone = phoneEdit.getText().toString().trim();
				email= emailEdit.getText().toString().trim();
				if(phone==null||phone.equals("")){
					Toast.makeText(EditContactActivity.this, "电话号码为空", Toast.LENGTH_SHORT).show();
				}else{
					if(operation.equals("1")){//1代表添加联系人
						addContact();
					}else if(operation.equals("0")){//0代表修改联系人
						updateContact();
					}else{
						Log.e(TAG, "联系人编辑出现错误");
					}
				}
	 
				
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Log.w(TAG, "取消保存联系人");
				//finish();
				deleteContact();
			}
		});
		
	}

	private void addContact(){
		dbOpreation = new ContactDBOperaion(this);
		contact = new ContactInfo();
		contact.setAddress(phone);
		contact.setContactName(name);
		contact.setEamil(email);
		try {
			dbOpreation.addContact(contact);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
	}
	
	private void updateContact(){
		
	}
	
	private void deleteContact(){
		dbOpreation = new ContactDBOperaion(this);
	 
			dbOpreation.deleteContacts();
		 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
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

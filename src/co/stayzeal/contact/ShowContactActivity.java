package co.stayzeal.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowContactActivity extends Activity {

	private TextView contactName;
	private TextView phone;
	private TextView email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_contact);
		init();
	}

	public void init(){
		contactName=(TextView) findViewById(R.id.show_contact_name);
		phone=(TextView)findViewById(R.id.show_contact_eidt_phone_1);
		email=(TextView)findViewById(R.id.show_contact_edit_email);
		Intent intent=getIntent();
		Bundle b=intent.getBundleExtra("idBundle");
		contactName.setText(b.getString("name"));
		phone.setText(b.getString("address"));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_settings:
			return true;
		case R.id.contact_edit_menu_item:
			Intent intent = new Intent();
			intent.setClass(this, EditContactActivity.class);
			startActivity(intent);

			break;
		default:
			break;
		}
		 
		return super.onOptionsItemSelected(item);
	}
}

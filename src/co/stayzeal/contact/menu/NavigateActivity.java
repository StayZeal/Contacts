package co.stayzeal.contact.menu;

import co.stayzeal.contact.CallLogFragment;
import co.stayzeal.contact.ContactsFragment;
import co.stayzeal.contact.DialFragment;
import co.stayzeal.contact.MessageFragment;
import co.stayzeal.contact.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigateActivity extends FragmentActivity implements OnClickListener{
	
	private DialFragment dialFrag;
	private CallLogFragment callLogFrag;
	private ContactsFragment contactsFrag;
	private MessageFragment msgFag;
	
	private LinearLayout dialLayout,callLogLayout,contactsLayout,msgLayout;
	private ImageView dialImg,callLogImg,contactsImg,msgImg;
	private TextView dialText,callLogText,contactsText,msgText;
	
	private FragmentManager fragManager;
	private FragmentTransaction fragTranscation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigate);
		init();
		dialClick();
	}
	
	private void init(){
		System.out.println("inin start");
		dialLayout=(LinearLayout) findViewById(R.id.contentDial);
		callLogLayout=(LinearLayout) findViewById(R.id.contentCallLog);
		contactsLayout=(LinearLayout) findViewById(R.id.contentContacts	);
		msgLayout=(LinearLayout) findViewById(R.id.contentMsg);
		System.out.println("inin img start");
		dialImg=(ImageView) findViewById(R.id.iconDial);
		callLogImg=(ImageView) findViewById(R.id.iconCallLog);
		contactsImg=(ImageView) findViewById(R.id.iconContacts);
		msgImg=(ImageView) findViewById(R.id.iconMsg);
		
		dialText=(TextView) findViewById(R.id.titleDial);
		callLogText=(TextView) findViewById(R.id.titleCallLog);
		contactsText=(TextView) findViewById(R.id.titleContacts);
		msgText=(TextView) findViewById(R.id.titleMsg);
		System.out.println("inin click start");
		dialLayout.setOnClickListener(this);
		callLogLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		msgLayout.setOnClickListener(this);
		System.out.println("inin end");
		//fragTranscation=this.getSupportFragmentManager().beginTransaction();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contentDial:
			setTitle("拨号");
			dialClick();
			break;
		case R.id.contentCallLog:
			setTitle("通话记录");
			callLogClick();
			break;
		case R.id.contentContacts:
			setTitle("联系人");
			contactsClick();
			break;
		case R.id.contentMsg:
			setTitle("短信息");
			msgClick();
			break;
		default:
			break;
		}
		
	}
	private void dialClick() {
		
		dialFrag=new DialFragment();
		fragTranscation=getSupportFragmentManager().beginTransaction();
		fragTranscation.replace(R.id.content, dialFrag);
		fragTranscation.commit();
		
		dialImg.setBackgroundResource(R.drawable.icon_tab_dialer_selected);
		callLogLayout.setBackgroundResource(R.drawable.icon_tab_calllog);
		contactsLayout.setBackgroundResource(R.drawable.icon_tab_contacts);
		msgLayout.setBackgroundResource(R.drawable.icon_tab_msg);
		
		
		dialLayout.setSelected(true);
		dialImg.setSelected(true);
		callLogLayout.setSelected(false);
		callLogImg.setSelected(false);
		contactsLayout.setSelected(false);
		contactsImg.setSelected(false);
		msgLayout.setSelected(false);
		msgImg.setSelected(false);
		
	}
	
	private void callLogClick() {
		
		callLogFrag=new CallLogFragment();
		fragTranscation=getSupportFragmentManager().beginTransaction();
		fragTranscation.replace(R.id.content, callLogFrag);
		fragTranscation.commit();
		
		dialImg.setBackgroundResource(R.drawable.icon_tab_dialer);
		callLogLayout.setBackgroundResource(R.drawable.icon_tab_calllog_selected);
		contactsLayout.setBackgroundResource(R.drawable.icon_tab_contacts);
		msgLayout.setBackgroundResource(R.drawable.icon_tab_msg);
		
		
		
		dialLayout.setSelected(false);
		dialImg.setSelected(false);
		callLogLayout.setSelected(true);
		callLogImg.setSelected(true);
		contactsLayout.setSelected(false);
		contactsImg.setSelected(false);
		msgLayout.setSelected(false);
		msgImg.setSelected(false);
		
	}

	private void contactsClick() {
		
		contactsFrag=new ContactsFragment();
		fragTranscation=getSupportFragmentManager().beginTransaction();
	    fragTranscation.replace(R.id.content, contactsFrag);
	    fragTranscation.commit();
	    
	    dialImg.setBackgroundResource(R.drawable.icon_tab_dialer);
		callLogLayout.setBackgroundResource(R.drawable.icon_tab_calllog);
		contactsLayout.setBackgroundResource(R.drawable.icon_tab_contacts_selected);
		msgLayout.setBackgroundResource(R.drawable.icon_tab_msg);
		
		dialLayout.setSelected(false);
		dialImg.setSelected(false);
		callLogLayout.setSelected(false);
		callLogImg.setSelected(false);
		contactsLayout.setSelected(true);
		contactsImg.setSelected(true);
		msgLayout.setSelected(false);
		msgImg.setSelected(false);
		
	}
	private void msgClick() {
		
		msgFag=new MessageFragment();
		fragTranscation=getSupportFragmentManager().beginTransaction();
		fragTranscation.replace(R.id.content, msgFag);
		fragTranscation.commit();
		
		dialImg.setBackgroundResource(R.drawable.icon_tab_dialer);
		callLogLayout.setBackgroundResource(R.drawable.icon_tab_calllog);
		contactsLayout.setBackgroundResource(R.drawable.icon_tab_contacts);
		msgLayout.setBackgroundResource(R.drawable.icon_tab_msg_selected);

		
		dialLayout.setSelected(false);
		dialImg.setSelected(false);
		callLogLayout.setSelected(false);
		callLogImg.setSelected(false);
		contactsLayout.setSelected(false);
		contactsImg.setSelected(false);
		msgLayout.setSelected(true);
		msgImg.setSelected(true);
		
	}
}

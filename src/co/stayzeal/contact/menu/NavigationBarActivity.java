package co.stayzeal.contact.menu;
 
import co.stayzeal.contact.CallLogFragment;
import co.stayzeal.contact.ContactsFragment;
import co.stayzeal.contact.DialFragment;
import co.stayzeal.contact.MessageFragment;
import co.stayzeal.contact.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigationBarActivity extends FragmentActivity  implements OnClickListener{

	private DialFragment dialFrag;
	private CallLogFragment callLogFrag;
	private ContactsFragment contactsFrag;
	private MessageFragment msgFag;
	
	private View dialLayout,callLogLayout,contactsLayout,msgLayout;
	private ImageView dialImg,callLogImg,contactsImg,msgImg;
	private TextView dialText,callLogText,contactsText,msgText;
	
	private FragmentManager fragManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_bar);
		init();
		fragManager=getSupportFragmentManager();
		setSelectedTab(0);
	}
	
	private void init(){
		dialLayout=  findViewById(R.id.dialLayout);
		callLogLayout=(LinearLayout) findViewById(R.id.callLogLayout);
		contactsLayout=(LinearLayout) findViewById(R.id.contactsLayout);
		msgLayout=(LinearLayout) findViewById(R.id.msgLayout);
		dialImg=(ImageView) findViewById(R.id.dialImg);
		callLogImg=(ImageView) findViewById(R.id.callLogImg);
		contactsImg=(ImageView) findViewById(R.id.contactsImg);
		msgImg=(ImageView) findViewById(R.id.msgImg);
		
		dialText=(TextView) findViewById(R.id.dialText );
		callLogText=(TextView) findViewById(R.id.callLogText);
		contactsText=(TextView) findViewById(R.id.contactsText);
		msgText=(TextView) findViewById(R.id.msgText);
		
		dialLayout.setOnClickListener(this);
		callLogLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		msgLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialLayout:
			setSelectedTab(0);
			break;
		case R.id.callLogLayout:
			setSelectedTab(1);
			break;
		case R.id.contactsLayout:
			setSelectedTab(2);
			break;
		case R.id.msgLayout:
			setSelectedTab(3);
			break;
		default:
			break;
		}
		
	}

	@SuppressLint("CommitTransaction")
	private void setSelectedTab(int index) {
		
		clearSelection();
		
		FragmentTransaction transaction=fragManager.beginTransaction();
		hideAllFrag(transaction);
		
		switch (index) {
		case 0:
			dialImg.setBackgroundResource(R.drawable.icon_tab_dialer_selected);
			if(dialFrag==null){
				dialFrag=new DialFragment();
				transaction.add(R.id.content, dialFrag);
			}else{
				transaction.show(dialFrag);
				dialLayout.setSelected(true);
			}
			break;
		case 1:
			callLogImg.setBackgroundResource(R.drawable.icon_tab_calllog_selected);
			if(callLogFrag==null){
				callLogFrag=new CallLogFragment();
				transaction.add(R.id.content, callLogFrag);
			}else{
				transaction.show(callLogFrag);
				callLogLayout.setSelected(true);
			}
			break;
		case 2:
			contactsImg.setBackgroundResource(R.drawable.icon_tab_contacts_selected);
			if(contactsFrag==null){
				contactsFrag=new ContactsFragment();
				transaction.add(R.id.content, contactsFrag);
			}else{
				transaction.show(contactsFrag);
				contactsLayout.setSelected(true);
			}
			break;
		case 3:
			msgImg.setBackgroundResource(R.drawable.icon_tab_msg_selected);
			if(msgFag==null){
				msgFag=new MessageFragment();	
				transaction.add(R.id.content, msgFag);
			}else{
				transaction.show(msgFag);
				contactsLayout.setSelected(true);
			}
			break;
		default:
			break;
		}
		
		transaction.commit();
	}
	
	/**
	 * 隐藏所有的fragment
	 * @param transaction
	 */
	private void hideAllFrag(FragmentTransaction transaction) {
		
		if(dialFrag!=null){
			transaction.hide(dialFrag);
			dialLayout.setSelected(false);
		}
		if(callLogFrag!=null){
			transaction.hide(callLogFrag);
			callLogLayout.setSelected(true);
		}
		if(contactsFrag!=null){
			transaction.hide(contactsFrag);
			contactsLayout.setSelected(true);
		}
		if(msgFag!=null){
			transaction.hide(msgFag);
			msgLayout.setSelected(true);
		}
	}
	
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		dialImg.setBackgroundResource(R.drawable.icon_tab_dialer);
		callLogImg.setBackgroundResource(R.drawable.icon_tab_calllog);
		contactsImg.setBackgroundResource(R.drawable.icon_tab_contacts);
		msgImg.setBackgroundResource(R.drawable.icon_tab_msg);
	}
	
}

package co.stayzeal.util;

import co.stayzea.contact.adapter.MsgConversationAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Object[] pdus = (Object[]) bundle.get("pdus");
	    SmsMessage[] messages = new SmsMessage[pdus.length];
	    for(int i = 0 ; i<messages.length ;i++){
	    	messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
	    }
	    String address = messages[0].getOriginatingAddress(); //获取发送方电话号码 
	    String msgContent = "";
	    for(int i = 0 ; i  < messages.length ; i++){
	    	msgContent+=messages[i].getMessageBody();
	    }
		
	}

}

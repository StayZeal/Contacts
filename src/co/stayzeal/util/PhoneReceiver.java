package co.stayzeal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * 
  * @ClassName: PhoneReceiver 
  * @Description: 通过监听器来实现
  * @author ArthorK
  * @date 2015年2月28日 下午3:19:03
 */
public class PhoneReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		//TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		String action = intent.getAction();
		
		if(action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
			doReceivePhone(context,intent);
		}
	}
	
	/**
	 * 处理接电话
	 * @param context
	 * @param intent
	 */
	public void doReceivePhone(Context context, Intent intent){
		//String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		//int state = telephonyManager.getCallState();
		telephonyManager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
		
	}
	
	class MyPhoneStateListener extends PhoneStateListener{

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://未接通的电话（正在响铃）
				
				break;
			case TelephonyManager.CALL_STATE_IDLE://挂断的电话
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK://接通的电话
				break;
			default:
				break;
			}
			
		}
		
	}

}

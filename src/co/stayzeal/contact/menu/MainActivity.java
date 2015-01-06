package co.stayzeal.contact.menu;

import co.stayzeal.contact.R;
import co.stayzeal.contact.menu.FragmentIndicator.OnIndicateListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;


public class MainActivity extends FragmentActivity  {

	 
	private static Fragment[] mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("main activity :", "oncreate function ִ��");
        setFragmentIndicator(0);
    }
    
    private void setFragmentIndicator(int whichIsDefault) {  
        mFragments = new Fragment[4];  
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.dialFragement);  
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.callLogFragement);  
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.contactsFragment); 
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.messageFragement); 

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();  
  
        FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.bottomMenu);  
        FragmentIndicator.setIndicator(whichIsDefault);  
        mIndicator.setOnIndicateListener(new OnIndicateListener() {  
            @Override  
            public void onIndicate(View v, int which) {  
            	Log.w("main activity: OnIndicateListener()", "�󶨼�����:��ʾIndex�� "+which);
                getSupportFragmentManager().beginTransaction()  
                        .hide(mFragments[0]).hide(mFragments[1])  
                        .hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();  
            }  
        });  
    }  
    
}

package co.stayzeal.contact;

import co.stayzeal.contact.menu.FragmentIndicator;
import co.stayzeal.contact.menu.FragmentIndicator.OnIndicateListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;


public class MainActivity extends FragmentActivity  {

	/**
	 * 动态添加Fragment主要分为4步：
	 * 1.获取到FragmentManager，在Activity中可以直接通过getFragmentManager得到。
	 * 2.开启一个事务，通过调用beginTransaction方法开启。
	 * 3.向容器内加入Fragment，一般使用replace方法实现，需要传入容器的id和Fragment的实例。
	 * 4.提交事务，调用commit方法提交。
	 * getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commit();
	 */
	/*private FragmentManager fragmentManger=getFragmentManager();
	private DialFragment dialFragment=new DialFragment();
	private CallLogFragment callLogFragment=new CallLogFragment();
	private MessageFragment messageFragment=new MessageFragment();
	private ListFragent listFragment=new ListFragent(); */
	
	private static Fragment[] mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("main activity :", "oncreate function 执行");
        setFragmentIndicator(0);
    }
    
    private void setFragmentIndicator(int whichIsDefault) {  
        mFragments = new Fragment[4];  
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.dialFragement);  
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.callLogFragement);  
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.listFragment); 
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.messageFragement); 
        Log.w("main activity :", "main activity获取fragment完毕");
        Log.w("menu数目：",String.valueOf(mFragments.length));
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();  
  
        FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.bottomMenu);  
        FragmentIndicator.setIndicator(whichIsDefault);  
        mIndicator.setOnIndicateListener(new OnIndicateListener() {  
            @Override  
            public void onIndicate(View v, int which) {  
            	Log.w("main activity: OnIndicateListener()", "绑定监听器:显示Index： "+which);
                getSupportFragmentManager().beginTransaction()  
                        .hide(mFragments[0]).hide(mFragments[1])  
                        .hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();  
            }  
        });  
    }  
    
}

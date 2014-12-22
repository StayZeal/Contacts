package co.stayzeal.contact;

import co.stayzeal.contacts.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	/**
	 * 动态添加Fragment主要分为4步：
	 * 1.获取到FragmentManager，在Activity中可以直接通过getFragmentManager得到。
	 * 2.开启一个事务，通过调用beginTransaction方法开启。
	 * 3.向容器内加入Fragment，一般使用replace方法实现，需要传入容器的id和Fragment的实例。
	 * 4.提交事务，调用commit方法提交。
	 * getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commit();
	 */
	private FragmentManager fragmentManger=getFragmentManager();
	private Fragment dialFragment=new Fragment();
	private Fragment historyFragment=new Fragment();
	private Fragment messageFragment=new Fragment();
	private Fragment listFragment=new Fragment(); 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

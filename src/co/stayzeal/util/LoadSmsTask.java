package co.stayzeal.util;

import java.util.List;

import co.stayzeal.contact.model.SmsInfo;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 弃用
 * @author ArthorK
 *
 */
public class LoadSmsTask extends AsyncTask<Context, Void, List<SmsInfo>> {

	private static final String TAG="LoadSmsTask";
	@Override
	protected List<SmsInfo> doInBackground(Context... params) {
		Long start = System.currentTimeMillis();
		SmsOperation smsOperation = new SmsOperation(params[0]);
		List<SmsInfo> threads = smsOperation.getThreads(0);
		List<SmsInfo> dataSource = smsOperation.getThreadNum(threads);
		Long end = System.currentTimeMillis();
		Long d= end-start;
		Log.i(TAG, "用时： "+d);
		return dataSource;
	}

	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}



	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}



	@Override
	protected void onPostExecute(List<SmsInfo> result) {
		super.onPostExecute(result);
	}

	
}

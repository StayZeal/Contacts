package co.stayzeal.util;

import java.util.List;

import co.stayzeal.contact.model.SmsInfo;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 弃用
 * @author ArthorK
 *
 */
public class LoadSmsTask extends AsyncTask<Context, Void, List<SmsInfo>> {

	@Override
	protected List<SmsInfo> doInBackground(Context... params) {
		SmsOperation smsOperation = new SmsOperation(params[0]);
		List<SmsInfo> threads = smsOperation.getThreads(0);
		List<SmsInfo> dataSource = smsOperation.getThreadNum(threads);
		return dataSource;
	}

	@Override
	protected void onPostExecute(List<SmsInfo> result) {
		super.onPostExecute(result);
	}

}

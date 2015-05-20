package co.stayzeal.contact.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * 自定义拨号界面的EditText控件。
 * @author Arthor
 *
 */
public class DialEditText extends EditText {

	private static final String TAG = "DialEditText";
	private Context mContext;
	private View delBtn;
	private View addBtn;
	public DialEditText(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	 
	

	public DialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		init();
	}

	public DialEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	private void init(){
		//this.delBtn = mContext.get
		setView();
		addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				setView();
			}
		});
	}
	
	private void setView(){
		if(length()>1){
			delBtn.setVisibility(View.VISIBLE);
			//addBtn.setVisibility(View.VISIBLE);
		}else{
			delBtn.setVisibility(View.INVISIBLE);
			addBtn.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(delBtn.getVisibility() == View.VISIBLE &&event.getAction() == MotionEvent.ACTION_UP){
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawX();
			Log.i(TAG, "eventX : "+eventX+" eventY : "+eventY);
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right -50;
			if(rect.contains(eventX, eventY)){
				setText("");
			}
		}
		return super.onTouchEvent(event);
	}
}

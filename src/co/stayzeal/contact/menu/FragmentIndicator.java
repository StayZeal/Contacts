package co.stayzeal.contact.menu;

import co.stayzeal.contact.R;
import co.stayzeal.contact.constant.MyColor;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义底部 菜单栏
 * 
 * @author YOUNG
 *
 */
public class FragmentIndicator extends LinearLayout implements OnClickListener {

	private int mDefaultIndicator = 0;

	private static int mCurrentIndicator;

	private static View[] mIndicators;

	private OnIndicateListener mOnIndicateListener;

	private static final String MENU_ICON_0 = "menu_icon_0";
	private static final String MENU_ICON_1 = "menu_icon_1";
	private static final String MENU_ICON_2 = "menu_icon_2";
	private static final String MENU_ICON_3 = "menu_icon_3";

	private static final String MENU_TEXT_0 = "menu_text_0";
	private static final String MENU_TEXT_1 = "menu_text_1";
	private static final String MENU_TEXT_2 = "menu_text_2";
	private static final String MENU_TEXT_3 = "menu_text_3";

	public FragmentIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCurrentIndicator = mDefaultIndicator;
		setOrientation(LinearLayout.HORIZONTAL);
		init();
	}

	@SuppressLint("NewApi")
	public FragmentIndicator(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * 
	 * @param iconResID
	 *            图标
	 * @param strResID
	 *            标题
	 * @param strColor
	 *            标题颜色
	 * @param iconIndex
	 *            图标索引
	 * @param textIndex
	 *            标题索引
	 * @return
	 */
	private View createIndicator(int iconResID, int strResID, int strColor,
			String iconIndex, String textIndex) {

		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		ImageView iconView = new ImageView(getContext());
		iconView.setTag(iconIndex);
		iconView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		iconView.setImageResource(iconResID);

		TextView textView = new TextView(getContext());
		textView.setTag(textIndex);
		textView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		textView.setTextColor(strColor);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		textView.setText(strResID);

		linearLayout.addView(iconView);
		linearLayout.addView(textView);

		return linearLayout;
	}

	private void init() {
		Log.w("FragmentIndicator : init() ", "初始化导航");
		mIndicators = new View[4];
		
		mIndicators[0] = createIndicator(R.drawable.icon_tab_dialer,
				R.string.dialTitle, MyColor.COLOR_SELECT, MENU_ICON_0,
				MENU_TEXT_0);
	    mIndicators[0].setBackgroundResource(R.drawable.bottombar_bg);
		mIndicators[0].setTag(Integer.valueOf(0));
		mIndicators[0].setOnClickListener(this);
		mIndicators[0].setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		addView(mIndicators[0]);
		
		mIndicators[1] = createIndicator(R.drawable.icon_tab_calllog,
				R.string.callLogTitle, MyColor.COLOR_SELECT, MENU_ICON_1,
				MENU_TEXT_1);
		mIndicators[1].setBackgroundColor(Color.alpha(0));
		mIndicators[1].setTag(Integer.valueOf(1));
		mIndicators[1].setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		mIndicators[1].setOnClickListener(this);
		addView(mIndicators[1]);
		
		mIndicators[2] = createIndicator(R.drawable.icon_tab_contacts,
				R.string.contactsTitle, MyColor.COLOR_SELECT, MENU_ICON_2,
				MENU_TEXT_2);
		mIndicators[2].setBackgroundColor(Color.alpha(0));
		mIndicators[2].setTag(Integer.valueOf(2));
		mIndicators[2].setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		mIndicators[2].setOnClickListener(this);
		addView(mIndicators[2]);
		
		mIndicators[3] = createIndicator(R.drawable.icon_tab_msg,
				R.string.msgTitle, MyColor.COLOR_SELECT, MENU_ICON_3,
				MENU_TEXT_3);
		mIndicators[3].setBackgroundColor(Color.alpha(0));
		mIndicators[3].setTag(Integer.valueOf(3));
		mIndicators[3].setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		mIndicators[3].setOnClickListener(this);
		addView(mIndicators[3]);
	}

	public static void setIndicator(int which) {
		Log.w("FragmentIndicator : init() ", "设置导航");
		// clear previous status.
		mIndicators[mCurrentIndicator].setBackgroundColor(Color.alpha(0));
		ImageView prevIcon;
		TextView prevText;
		switch (mCurrentIndicator) {
		case 0:
			prevIcon = (ImageView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_ICON_0);
			prevIcon.setImageResource(R.drawable.icon_tab_dialer);
			prevText = (TextView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_TEXT_0);
			prevText.setTextColor(MyColor.COLOR_UNSELECT);
			break;
		case 1:
			prevIcon = (ImageView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_ICON_1);
			prevIcon.setImageResource(R.drawable.icon_tab_calllog);
			prevText = (TextView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_TEXT_1);
			prevText.setTextColor(MyColor.COLOR_UNSELECT);
			break;
		case 2:
			prevIcon = (ImageView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_ICON_2);
			prevIcon.setImageResource(R.drawable.icon_tab_contacts);
			prevText = (TextView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_TEXT_2);
			prevText.setTextColor(MyColor.COLOR_UNSELECT);
			break;
		case 3:
			prevIcon = (ImageView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_ICON_3);
			prevIcon.setImageResource(R.drawable.icon_tab_msg);
			prevText = (TextView) mIndicators[mCurrentIndicator]
					.findViewWithTag(MENU_TEXT_3);
			prevText.setTextColor(MyColor.COLOR_UNSELECT);
			break;
		}

		// update current status.
		/* mIndicators[which].setBackgroundResource(R.drawable.indic_select); */
		ImageView currIcon;
		TextView currText;
		switch (which) {
		case 0:
			currIcon = (ImageView) mIndicators[which]
					.findViewWithTag(MENU_ICON_0);
			currIcon.setImageResource(R.drawable.icon_tab_dialer_selected);
			currText = (TextView) mIndicators[which]
					.findViewWithTag(MENU_TEXT_0);
			currText.setTextColor(MyColor.COLOR_SELECT);
			break;
		case 1:
			currIcon = (ImageView) mIndicators[which]
					.findViewWithTag(MENU_ICON_1);
			currIcon.setImageResource(R.drawable.icon_tab_calllog_selected);
			currText = (TextView) mIndicators[which]
					.findViewWithTag(MENU_TEXT_1);
			currText.setTextColor(MyColor.COLOR_SELECT);
			break;
		case 2:
			currIcon = (ImageView) mIndicators[which]
					.findViewWithTag(MENU_ICON_2);
			currIcon.setImageResource(R.drawable.icon_tab_contacts_selected);
			currText = (TextView) mIndicators[which]
					.findViewWithTag(MENU_TEXT_2);
			currText.setTextColor(MyColor.COLOR_SELECT);
			break;
		case 3:
			currIcon = (ImageView) mIndicators[which]
					.findViewWithTag(MENU_ICON_3);
			currIcon.setImageResource(R.drawable.icon_tab_msg_selected);
			currText = (TextView) mIndicators[which]
					.findViewWithTag(MENU_TEXT_3);
			currText.setTextColor(MyColor.COLOR_SELECT);
			break;
		}

		mCurrentIndicator = which;
	}

	public interface OnIndicateListener {
		public void onIndicate(View v, int which);
	}

	public void setOnIndicateListener(OnIndicateListener listener) {
		mOnIndicateListener = listener;
	}

	@Override
	public void onClick(View v) {
		if (mOnIndicateListener != null) {
			int tag = (Integer) v.getTag();
			switch (tag) {
			case 0:
				if (mCurrentIndicator != 0) {
					mOnIndicateListener.onIndicate(v, 0);
					setIndicator(0);
				}
				break;
			case 1:
				if (mCurrentIndicator != 1) {
					mOnIndicateListener.onIndicate(v, 1);
					setIndicator(1);
				}
				break;
			case 2:
				if (mCurrentIndicator != 2) {
					mOnIndicateListener.onIndicate(v, 2);
					setIndicator(2);
				}
				break;
			case 3:
				if (mCurrentIndicator != 3) {
					mOnIndicateListener.onIndicate(v, 3);
					setIndicator(3);
				}
				break;
			default:
				break;
			}
		}
	}
}

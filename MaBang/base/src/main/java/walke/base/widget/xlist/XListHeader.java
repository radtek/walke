/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package walke.base.widget.xlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import walke.base.R;


public class XListHeader extends LinearLayout {
	private static final String TAG = "XListViewHeader";
	private LinearLayout mContainer;
	private DrawRing mDrawRing;
	private int mState = STATE_NORMAL;
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	private TextView mTextView;
	private boolean isNormal=true;

	public XListHeader(Context context) {
		super(context);
		initView(context);
	}

	public XListHeader(Context context, boolean isNormal) {
		super(context);
		this.isNormal=isNormal;
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public DrawRing getDrawRing() {
		return mDrawRing;
	}

	public void setDrawRing(DrawRing drawRing) {
		mDrawRing = drawRing;
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0);
		if (isNormal) {
			mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlist_header, null);
			mDrawRing = (DrawRing)mContainer.findViewById(R.id.xlist_header_progressbar);
			mTextView = (TextView)mContainer.findViewById(R.id.xlist_header_text);
		}else {
			mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlist_header2, null);
			mDrawRing = (DrawRing)mContainer.findViewById(R.id.xlist_header_progressbar2);
			mTextView = (TextView)mContainer.findViewById(R.id.xlist_header_text2);
		}
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

	}

	public void setState(int state) {
		if (state == mState) return ;

		/*if (state == STATE_REFRESHING) {// 显示进度
			mTextView.setText(R.string.xlistview_header_hint_loading);
			mDrawRing.setVisibility(View.VISIBLE);
		} else {	// 显示箭头图片
			mDrawRing.setVisibility(View.VISIBLE);
		}*/

		switch(state){
		case STATE_NORMAL://下拉刷新
			mTextView.setText("下拉刷新");
			mDrawRing.setProgress(0);
			mDrawRing.stopRotate();
			break;
		case STATE_READY:
			if (mState != STATE_READY) {//---松开刷新
				//mDrawRing.startRotate(5);
				mTextView.setText("松开刷新");
			}
			break;
		case STATE_REFRESHING://正在加载...
			mDrawRing.startRotate(12);
			mTextView.setText("正在刷新");
			break;
			default:
		}

		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}
	public void setTopMargin(int height) {
		if (height < 0) return ;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContainer.getLayoutParams();
		lp.bottomMargin = -height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}

/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package walke.base.widget.xlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import walke.base.R;


public class XListFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;

	private View mContentView;
	private DrawRing mProgressBar;
	private TextView mHintView;
	
	public XListFooter(Context context) {
		super(context);
		initView(context);
	}
	
	public XListFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}


	/** 在XList中的拖动状态 设置状态底布局状态
	 * @param state
	 */
	public void setState(int state) {

		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.VISIBLE);
		if (state == STATE_READY) {
			mHintView.setText("松开载入更多");
		} else if (state == STATE_LOADING) {
			mProgressBar.startRotate(11);
			mHintView.setText("正在加载...");
		} else {
			mProgressBar.stopRotate();
			mProgressBar.setProgress(185);
			mHintView.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.GONE);
		}

	}
	
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = height;
		mContentView.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContentView.getHeight();
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}


	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	private void initView(Context context) {
		mContext = context;
		RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.xlist_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.xlist_footer_content);
		mProgressBar = (DrawRing) moreView.findViewById(R.id.xlist_footer_progressbar);
		mHintView = (TextView)moreView.findViewById(R.id.xlist_footer_hint_textview);
		mProgressBar.setProgress(185);
	}
	
	
}

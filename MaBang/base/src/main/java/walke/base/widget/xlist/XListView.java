/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (picture1) Pull down to refresh, (picture2) Pull up to load more.
 * Implement XListRefreshOrLoadMoreListener, and see stopRefresh() / stopLoadMore().
 */
package walke.base.widget.xlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import walke.base.R;


public class XListView extends ListView implements OnScrollListener {

    private static final String TAG = "XListView";
    private boolean isNormal = true;
    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private XListRefreshOrLoadMoreListener mListViewListener;

    // -- header view
    private XListHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mEnableLoadMore = true;
    private boolean mPullRefreshing = false; // is refreashing.

    // -- footer view
    private View mFooterView;


    /**
     * total list items, used to detect is at the bottom of listview.
     * 总列表项,用于检测列表视图的底部。
     */
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    /**
     * scroll back duration 滑回时间
     */
    private final static int SCROLL_DURATION = 400;
    /**
     * when pull up >= 50px 在底部上拉大于50px
     * at bottom, trigger:触发
     * load more.加载更多
     */
    private final static int PULL_LOAD_MORE_DELTA = 100;
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull

    private DrawRing mDrawRing;
    private DrawRing drFooterBar;//底部进度条
    private TextView tvFooterText;//底部文本控件
    /**
     * @param context
     */
    public XListView(Context context) {
        this(context, null);
    }

    public XListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.XList, defStyle, 0);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.XList_normalSelect) {
                isNormal = a.getBoolean(index, true);
            }

        }
        a.recycle();

        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as picture1 proxy).//proxy:代理人
        super.setOnScrollListener(this);

        // init header view
        mHeaderView = new XListHeader(context, isNormal);
        if (isNormal) {
            mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlist_header_content);
        } else {
            mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlist_header_content2);
        }
        addHeaderView(mHeaderView);
        mDrawRing = mHeaderView.getDrawRing();

        // init footer view
        mFooterView = View.inflate(getContext(), R.layout.xlistview_loadmore, null);
        drFooterBar = ((DrawRing) mFooterView.findViewById(R.id.xlv_loadMoreProgressBar));
        tvFooterText = ((TextView) mFooterView.findViewById(R.id.xlv_loadMoreHint));
        setFooterViewStatus(false);
        addFooterView(mFooterView);
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadMore();
            }
        });
        // init header height
        mHeaderViewHeight = getViewHeight(mHeaderViewContent);

    }

    private void startLoadMore() {
        if (mListViewListener!=null){
            mListViewListener.onAutoLoadMore();
            setFooterViewStatus(true);
        }
    }

    public void endLoadMore() {
        setFooterViewStatus(false);
        isNoMoreData=true;
    }
    
    private void setFooterViewStatus(boolean isLoadMoreing) {
        if (mEnableLoadMore)
        if (isLoadMoreing){
            drFooterBar.setVisibility(View.VISIBLE);
            drFooterBar.setProgress(185);
            drFooterBar.startRotate(12);
            tvFooterText.setText("正在加载...");
        }else {
            tvFooterText.setText("没有更多数据");//默认文本
            drFooterBar.setVisibility(View.GONE);//默认：没有更多数据、进度条不显示
        }
    }

    public boolean isPullRefreshing() {
        return mPullRefreshing;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        autoBottomLoad();
    }

    /**
     * 首次显示不足全屏数据时自动加载更多
     */
    private void autoBottomLoad() {
        if (mEnableLoadMore)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPullRefreshing()) return;
                int childCount = getChildCount();
                int lastVisiblePosition = getLastVisiblePosition();//最后显示的一个
                if (lastVisiblePosition == childCount - 1) {
                    startLoadMore();
                }
            }
        }, 600);
    }

    /**
     * enable or disable pull down refresh feature.
     * 启用或禁用下拉刷新功能。
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content  禁用，则隐藏内容
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull down refresh feature.
     * 启用或禁用下拉刷新功能。
     *
     * @param enable
     */
    public void setLoadMoreEnable(boolean enable) {
        mEnableLoadMore = enable;
        if (!mEnableLoadMore) { // disable, hide the content  禁用，则隐藏内容
            mFooterView.setVisibility(View.INVISIBLE);
        } else {
            mFooterView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * stop refresh, reset header view.
     * 停止刷新，重置头布局
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
            autoBottomLoad();//
        }
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
        if (mHeaderView.getVisiableHeight() > 30 && mHeaderView.getVisiableHeight() < 215) {
            mDrawRing.setProgress((mHeaderView.getVisiableHeight() - 30) * 2f);
        }
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {//
                mHeaderView.setState(XListHeader.STATE_READY);
            } else {
                mHeaderView.setState(XListHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time 每次滚动到顶部
    }

    /**
     * reset header view's height.
     * 重置 头布局的高度
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible. 不可见的
            return;
        // refreshing and header isn't shown fully. do nothing.
        // 更新和标题不显示完全。什么也不做。
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }

        boolean b = mDrawRing.rotate;
        if (!b) {
            mDrawRing.setProgress(182);//++
        }

        int finalHeight = 0; // default: scroll back to dismiss header. 默认值:滚动回头。
        // is refreshing, just scroll back to show all the header.正在刷新,滚动显示所有标题。
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        // trigger computeScroll 触发计算滚动(重绘)
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                System.out.println("数据监测：" + getFirstVisiblePosition() + "---->" + getLastVisiblePosition());
                if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    // 当第一项是显示时，头布局显示或下拉
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                }
                break;
            default:// TODO: 2017/2/16 松开时
                //mDrawRing.setProgress(0);
                mLastY = -1; // reset 重置
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh 唤起刷新
                    if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(XListHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    private boolean isBottom;//是否到底部
    private boolean isNoMoreData=false;//是否已经没有更多数据，true不在回调

    public void setNoMoreData(boolean noMoreData) {
        isNoMoreData = noMoreData;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
        //正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
        //回调顺序如下
        //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
        //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
        //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
        //当滚到最后一行且停止滚动时，执行加载
        if (mEnableLoadMore&&!isNoMoreData && !isPullRefreshing() && isBottom && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            //加载元素^^^^
            Log.i("walke", "onScrollStateChanged: ---------------------------------回调onAutoLoadMore");
            startLoadMore();

        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // send to user's listener 发送到用户的监听器
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
        isBottom = (firstVisibleItem + visibleItemCount) == totalItemCount;

    }

    /**
     * 获取View的高度
     *
     * @param view
     * @return
     */
    public int getViewHeight(View view) {
        int layout_width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int layout_height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(layout_width, layout_height);
        int measuredHeight = view.getMeasuredHeight();
        return measuredHeight;
    }


    @Override
    public void setEmptyView(View emptyView) {
        super.setEmptyView(emptyView);
    }

    public void setXListRefreshOrLoadMoreListener(XListRefreshOrLoadMoreListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     * 你可以监听listview的OnScrollistenner 或者这个，当头/底布局回滚到原位，这会调用onXScrolling
     */
    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface XListRefreshOrLoadMoreListener {
        void onRefresh();

        void onAutoLoadMore();
    }


}

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
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import walke.base.R;


public class XList extends ListView implements OnScrollListener {

    private static final String TAG = "XListView";
    private boolean isNormal=true;
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
    private boolean mPullRefreshing = false; // is refreashing.

    // -- footer view
    private XListFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;//
    private boolean mIsFooterReady = false;

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
    /**when pull up >= 50px 在底部上拉大于50px
     * at bottom, trigger:触发
     * load more.加载更多
     */
    private final static int PULL_LOAD_MORE_DELTA = 100;
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    private DrawRing mDrawRing;


    /**
     * @param context
     */
    public XList(Context context) {
        this(context,null);
    }

    public XList(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public XList(Context context, AttributeSet attrs, int defStyle) {
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
        mHeaderView = new XListHeader(context,isNormal);
        if (isNormal) {
            mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlist_header_content);
        }else {
            mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlist_header_content2);
        }
        addHeaderView(mHeaderView);
        mDrawRing = mHeaderView.getDrawRing();

        // init footer view
        mFooterView = new XListFooter(context);

        // init header height
        mHeaderViewHeight = getViewHeight(mHeaderViewContent);

    }

    public boolean isPullRefreshing() {
        return mPullRefreshing;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only addChange once.
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
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
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterView.setState(XListFooter.STATE_NORMAL);
            mFooterView.setOnClickListener(null);
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
        }
    }

    /**
     * stop load more, reset footer view.
     * 停止加载更多，重置底布局
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListFooter.STATE_NORMAL);
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
            mDrawRing.setProgress((mHeaderView.getVisiableHeight() - 30)*2f);
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

    /**
     * @param delta 更新底布局的高度
     */
    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            //能加载更多，且不是正在加载（上拉的）
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load more. 高度足以调用加载更多。
                mFooterView.setState(XListFooter.STATE_READY);
                mFooterView.show();//++
            } else {
                mFooterView.setState(XListFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

        this.setPadding(0,-height,0,0); //++
    }

    /**
     * 重置 底布局的高度
     */
    private void resetFooterHeight() {
        this.setPadding(0,0,0,0); //++

        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            mFooterView.setBottomMargin(0);//++
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListFooter.STATE_LOADING);
        if (loadMoreListener!=null)
            loadMoreListener.onLoadMore();
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
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
                } else if (getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    // 最后一项显示时，已经停在了或者想拉起。
                    //Log.picture9(TAG, "ACTION_MOVE: 333333333333--->deltaY="+deltaY+"                mLastY="+mLastY);
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
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
                        if (refreshListener != null)
                            refreshListener.onRefresh();
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                }
                if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more. 调用加载更多
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
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

            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
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

    }

    /**
     * 获取View的高度
     *
     * @param view
     * @return
     */
    public int getViewHeight(View view) {
        int layout_width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int layout_height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
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

        void onLoadMore();
    }
    public interface XListRefreshListener {
        void onRefresh();
    }
    private XListRefreshListener refreshListener;

    public void setRefreshListener(XListRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public interface XListLoadMoreListener {
        void onLoadMore();
    }
    private XListLoadMoreListener loadMoreListener;

    public void setLoadMoreListener(XListLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}

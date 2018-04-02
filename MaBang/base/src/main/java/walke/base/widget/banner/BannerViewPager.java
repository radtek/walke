package walke.base.widget.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Field;

import walke.base.tool.LogUtil;

/**
 * Created by walke.Z on 2017/8/11.
 */

public class BannerViewPager extends ViewPager {

    private static final String TAG = "BannerViewPager";
    private Context mContext;
    private BannerBaseAdapter mAdapter;
    // 2.实现自动轮播 - 发送消息的msgWhat
    private final int SCROLL_MSG = 0x0011;

    // 2.实现自动轮播 - 页面切换间隔时间
    private int mChangeTime = 2500;

    public void setChangeTime(int changeTime) {
        mChangeTime = changeTime;
    }

    // 2.实现自动轮播 - 发送消息Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 每隔*s后切换到下一页
            setCurrentItem(getCurrentItem() + 1);
            // 不断循环执行
            startRoll();
        }
    };

    // 3.改变ViewPager切换的速率 - 自定义的页面切换的Scroller
    private BannerScroller mScroller;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        try {
            // 3.改变ViewPager切换的速率
            // 3.1 duration 持续的时间  局部变量
            // 3.2.改变 mScroller private 通过反射设置
            Field field = ViewPager.class.getDeclaredField("mScroller");
            // 设置参数  第一个object当前属性在哪个类  第二个参数代表要设置的值
            mScroller = new BannerScroller(context);
            // 设置为强制改变private
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            PagerAdapter adapter = getAdapter();
            int count = adapter.getCount();
            int maxValue = Integer.MAX_VALUE;
            BannerBaseAdapter bannerBaseAdapter = (BannerBaseAdapter) getAdapter();
            int dataSize = bannerBaseAdapter.getDataSize();
            int bannerCount = bannerBaseAdapter.getCount();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("walke", "onTouchEvent: ACTION_DOWN---------------------------------BannerViewPager");
                    if (bannerBaseAdapter !=null&& dataSize >1)
                        stopRoll();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case  MotionEvent.ACTION_UP:// TODO: 2017/2/16 松开时
                    Log.i("walke", "onTouchEvent: ACTION_UP---------------------------------BannerViewPager");
                    if (bannerBaseAdapter !=null&& dataSize >1)
                        startRoll();

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.onTouchEvent(ev);
        }
        return super.onTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:// TODO: 2017/2/16 松开时

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 3.设置切换页面动画持续的时间
     */
    public void setScrollerDuration(int scrollerDuration) {
        mScroller.setScrollerDuration(scrollerDuration);
    }


    /**
     * 2.实现自动轮播
     */
    public void startRoll() {
        // 清除消息
        mHandler.removeMessages(SCROLL_MSG);
        // 消息  延迟时间  让用户自定义  有一个默认  3500
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mChangeTime);

        LogUtil.i(this, "startRoll");
    }

    /**
     * 2.停止轮播
     */
    public void stopRoll() {
        // 清除消息
        mHandler.removeMessages(SCROLL_MSG);
        LogUtil.i(this, "startRoll");
    }

    /**
     * 2.销毁Handler停止发送  解决内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
    }

}

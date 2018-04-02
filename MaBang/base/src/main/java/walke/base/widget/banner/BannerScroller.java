package walke.base.widget.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by walke.Z on 2017/8/16.
 * 切换速率
 */

public class BannerScroller extends Scroller {

//    private int mScrollerDuration=2000;
//    private int mScrollerDuration=0;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    /**
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
//        mScrollerDuration = scrollerDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
//        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
        super.startScroll(startX, startY, dx, dy, duration);
    }
}

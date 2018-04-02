package walke.base.tool;

/**
 * Created by walke.Z on 2017/7/31.
 */

public class TimeUtil extends Util {

    private static long lastClickTime = 0;

    /**
     * 是否满足间隔时间 默认(最小)为0.5s
     *
     * @param intervalTime
     * @return
     */
    public synchronized static boolean isMeetIntervalTime(int intervalTime) {
        long time = System.currentTimeMillis();
        if (intervalTime < 500)
            intervalTime = 500;
        if (time - lastClickTime < intervalTime) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 是否满足间隔时间 默认为0.5s
     *
     * @param intervalTime
     * @return
     */
    public synchronized static boolean intervalTime(int intervalTime) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < intervalTime) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

}

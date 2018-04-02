package walke.base;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Walke.Z
 * on 2017/11/28. 03.
 * email：1126648815@qq.com
 */
public class ExitApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();

    private static ExitApplication instance;

    private ExitApplication() {
    }

    //单例模式中获取唯一的ExitApplication 实例
    public static ExitApplication getInstance() {
        if (null == instance) {
            instance = new ExitApplication();
        }
        return instance;

    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    //添加Activity 到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    //遍历所有Activity 并finish

    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }

        System.exit(0);

    }
}

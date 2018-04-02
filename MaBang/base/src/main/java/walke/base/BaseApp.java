package walke.base;

import android.app.Application;

/**
 * Created by walke.Z on 2017/11/17.
 */

public class BaseApp extends Application {


    private int countdownTime;//记录验证码请求时间，(默认60秒内不能再次请求)


    public int getCountdownTime() {
        return countdownTime;
    }

    public void setCountdownTime(int countdownTime) {
        this.countdownTime = countdownTime;
    }

    private int uid=1;//酒瓶项目临时使用

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


}

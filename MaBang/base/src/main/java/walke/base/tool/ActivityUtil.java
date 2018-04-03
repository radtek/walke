package walke.base.tool;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by walke.Z on 2018/4/3.
 */

public class ActivityUtil {

    public static boolean isValidContext (Context context){

        Activity a = (Activity)context;

        if (a.isFinishing())
            return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (a.isDestroyed())
                return false;
//            Log.i("YXH", "Activity is invalid." + " isDestoryed-->" + a.isDestroyed() + " isFinishing-->" + a.isFinishing());
        }
        return true;
    }

    public static boolean isValidContext (AppCompatActivity activity){

        if (activity.isFinishing())
            return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed() )
                return false;
        }
        return true;
    }
}

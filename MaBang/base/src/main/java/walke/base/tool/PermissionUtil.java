package walke.base.tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Walke.Z on 2017/4/21.
 * 在某些机型权限要独立处理、批量处理无效
 */
public class PermissionUtil {

    public static final int CODE_REQUEST_PERMISSION = 123;

    /**
     * 检查系统权限，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
     *
     * @param context
     * @param permission
     * @return
     */
    private static boolean isLackPermission(Context context, String permission) {
        boolean hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;//denied--拒绝
        return hasPermission;
    }


    /**
     * 检查权限时，判断该app的权限集合,是否有缺少的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermissionSetLack(Context context, String... permissions) {
        for (String permission : permissions) {
            if (isLackPermission(context, permission)) {//是否添加完全部权限集合
                return true;//有缺少的权限
            }
        }
        return false;//表示全部权限已有齐
    }

    /**
     * 检查权限时，判断该app的权限集合,是否有缺少的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean isFitPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (isLackPermission(context, permission)) {//是否添加完全部权限集合
                Log.i("walke", "isFitPermissions:-------------- permission="+permission);
                return false;//有缺少的权限
            }
        }
        return true;//表示全部权限已有齐
    }

    /**
     * Requests permissions.
     * @param activity
     */
    public static void requestPermissions(final Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean b = checkPermissionSetLack(activity, permissions);
            if (b) {
                activity.requestPermissions(permissions, CODE_REQUEST_PERMISSION);
            }
        }
    }

    /**  Lack 缺乏
     * 检查系统权限，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
     *
     * @param activity
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void checkAndRequestPermission(AppCompatActivity activity, String permission) {
        if (isLackPermission(activity, permission)){
            activity.requestPermissions(new String[]{permission}, CODE_REQUEST_PERMISSION);
        }
    }
    public static boolean isGetAllPermission(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                //只要存在一个权限未授权 则返回false
                return false;
            }
        }
        return true;
    }

}

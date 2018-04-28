package walke.base.tool;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 动画帮忙类
 * 
 * @author A-Rui(xiongrui0320@yahoo.com.cn)
 * @version 2013-6-17 上午11:32:32
 */
public class AnimationHelper {
	/**
	 * 检查当前SDK是否支持动画效果
	 * 
	 * @return
	 */
	public static boolean checkAnimation() {
		// 如果2.0以下版本不支持动画,即1.6或以下不支持
		// true 为支持,false不支持
		// Log.v("VERSION", android.os.Build.VERSION.SDK_INT + "");
		return (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.DONUT);
	}

	/**
	 * 动画效果(适合2.0以上版本)
	 * 
	 * @param object
	 * @param enterAnim
	 * @param exitAnim
	 */
	public static void animation(Activity activity, int enterAnim, int exitAnim) {
		// 不支持动画则返回
		if (!checkAnimation())
			return;
		try {
			new Object() {
				public void overridePendingTransition(Activity activity, int enterAnim, int exitAnim) {
					activity.overridePendingTransition(enterAnim, exitAnim);
				}
			}.overridePendingTransition(activity, enterAnim, exitAnim);
			// Method method = object.getClass().getMethod("overridePendingTransition", new java.lang.Class[] { Integer.class, Integer.class });
			// method.invoke(object, new Object[] { enterAnim, exitAnim });
		} catch (Exception e) {
			Log.e("e", e.getMessage());
		}
		return;
	}

	/**
	 * 添加动画效果(适合1.6版本)
	 * 
	 * @param activity
	 * @param enterAnim
	 */
	public static void animation(Activity activity, int enterAnim) {
		if (!checkAnimation()) {
			Animation animation = AnimationUtils.loadAnimation(activity, enterAnim);
			View view = ((ViewGroup) activity.getWindow().getDecorView()).getChildAt(0);
			view.startAnimation(animation);
		}
		return;
	}

	/**
	 * 关闭动画
	 * @param activity
	 */
	public static void closeAnimation(Activity activity) {
		activity.overridePendingTransition(0, 0);
		return;
	}
}

package walke.base.widget;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Window;
import android.view.WindowManager;

import walke.base.R;

/**
 * Created by walke on 2018/2/26.
 * email:1032458982@qq.com
 */

public class DialogCustom extends Dialog{


    public DialogCustom(@NonNull Context context) {
        this(context,0);
    }

    public DialogCustom(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.dialog_custom);
        this.setTranslucentStatus(true);
    }

    /** 沉浸式状态栏
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        return;
    }
}

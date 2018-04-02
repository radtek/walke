package walke.base.tool;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import walke.base.R;


/**
 * @author View
 * @date 2016/12/16 8:35
 * <p>
 * 发现
 * 快速连续创建2个dialog，会产生指引异常，
 * 第二次创建时：确实会新建对象(dialog+内部控件)，但使用这些对象时会指向第一次创建的对象
 * 故需要设置为全局变量
 */
public class LoadingDialog {

    private static Dialog progressDialog;

    public static Dialog createDialog(Context context, String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.dialog_loading_msg);
        msg.setText(message);
        return progressDialog;
    }

    public static void dialogDismiss(AppCompatActivity activity){
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog=null;
            // 设置背景颜色变暗
            final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1f;
            activity.getWindow().setAttributes(lp);
        }
    }


}

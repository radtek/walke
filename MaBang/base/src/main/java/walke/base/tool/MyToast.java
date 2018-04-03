package walke.base.tool;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import walke.base.R;


/**
 * Created by walke.Z on 2018/3/30.
 * 参考：https://blog.csdn.net/u012846783/article/details/50204687
 */

public class MyToast {

    private static MyToast result;

    private Context mContext;
    public static final int LENGTH_SHORT = 1500;
    public static final int LENGTH_LONG = 3000;
    private int mDuration;
    private static Dialog mDialog;
    private TextView tvMessage;

    private MyToast(Context context) {
        mContext = context.getApplicationContext();
    }

    public static MyToast getInstance(Context context){
        if (result==null)
            result = new MyToast(context);
        return result;
    }

    public MyToast makeText(Context context, String text, int duration) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDuration=duration;
        mDialog = new Dialog(context, R.style.dialog_custom);
        int wWidth = WindowUtil.getWindowWidth(context);
        int dWidth = (int) (wWidth * 0.95);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null);
        tvMessage = ((TextView) inflate.findViewById(R.id.dt_tvMessage));
        tvMessage.setText(text+"");
        mDialog.setContentView(inflate, new ViewGroup.LayoutParams(dWidth, (int) (dWidth * 1.32)));
        mDialog.setCancelable(false);//点击其他区域无响应
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return result;
    }

    public void show(final Context context) {
        if (mDialog!=null&&!mDialog.isShowing()){
            mDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogDismiss(context);
//                    dialogDismiss();
                }
            }, mDuration);//mDuration
        }

    }

    public void show(final AppCompatActivity activity) {
        if (mDialog!=null&&!mDialog.isShowing()){
            mDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogDismiss(activity);
//                    dialogDismiss();
                }
            }, mDuration);//mDuration
        }

    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static void dialogDismiss(Context context){

        if (mDialog!=null&&mDialog.isShowing()&&ActivityUtil.isValidContext(context)){
            mDialog.dismiss();
            mDialog=null;
        }
    }


    public static void dialogDismiss(AppCompatActivity activity){

        if (mDialog!=null&&mDialog.isShowing()&&!activity.isFinishing()){
            mDialog.dismiss();
            mDialog=null;
        }
    }

    public static void dialogDismiss(){

        if (mDialog!=null&&mDialog.isShowing()){//会报not attached to window manager异常
            mDialog.dismiss();
            mDialog=null;
        }
    }



}

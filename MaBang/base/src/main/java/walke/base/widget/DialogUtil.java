package walke.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import walke.base.BaseActivity;
import walke.base.R;
import walke.base.tool.WindowUtil;


/**
 * Created by walke.Z on 2018/2/27.
 */

public class DialogUtil {

    private static Dialog mDialog;

    /**
     * 显示更换头像提示框
     */
    public static void showChangeHeaderTips(final Context context, View view, final PWTwoButtonClickListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_change_header_tips, null);
        TextView photos = (TextView) inflate.findViewById(R.id.cht_photos);
        TextView camera = (TextView) inflate.findViewById(R.id.cht_camera);
        final PopupWindow pw = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pw.setAnimationStyle(R.style.pwAnimationBottom);
        // popup window点击窗口外区域消失的解决方法 --顺序前后问题
        pw.setTouchable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());//必须设置背景
        pw.setFocusable(true);// 设置让pw获得焦点
        //设置pw出现方式
        pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);// ViewUtil.dipTopx(context, 45)
       /* //popup window点击窗口外区域不消失的解决方法
        pw.setTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);*/
        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
        lp.alpha = 0.7f;
        ((BaseActivity) context).getWindow().setAttributes(lp);

        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((BaseActivity) context).getWindow().setAttributes(lp);
            }
        });

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                if (listener != null)
                    listener.OnTopClick();

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                if (listener != null)
                    listener.OnBottomClick();
            }
        });
    }


    public interface TwoButtonClickListener {
        void leftOnClick();

        void rightOnClick();
    }


    public interface OneButtonClickListener {
        void buttonClick();
    }

    public interface PWTwoButtonClickListener {
        void OnTopClick();

        void OnBottomClick();
    }


    /**
     * @return 创建三行(WebView)提示框，有左右选项
     * 提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog tipsOneButton(final AppCompatActivity activity, String btText, @NonNull String describeText, final OneButtonClickListener listener) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog.cancel();
        }
        // 设置背景颜色变暗
        View inflate = LayoutInflater.from(activity).inflate(R.layout.base_dialog_tips, null);
        TextView tvDescribe = (TextView) inflate.findViewById(R.id.bdt_tipsText);
        TextView btLeft = (TextView) inflate.findViewById(R.id.bdt_leftButton);
        final TextView btRight = (TextView) inflate.findViewById(R.id.bdt_rightButton);
        View vLine = inflate.findViewById(R.id.bdt_line);
        if (describeText.contains("\\n"))
            describeText = describeText.replace("\\n", "\n");//
        tvDescribe.setText(describeText);
        vLine.setVisibility(View.GONE);
        btLeft.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(btText))
            btRight.setText(btText);
        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss(activity);
                if (listener != null)
                    listener.buttonClick();
            }
        });
        btRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btRight.setTextColor(Color.parseColor("#880073ff"));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case  MotionEvent.ACTION_UP:// TODO: 2017/2/16 松开时
                        btRight.setTextColor(Color.parseColor("#ff0073ff"));
                        break;
                }
                return false;
            }
        });

        mDialog = new DialogCustom(activity);
        mDialog.setContentView(inflate,new ViewGroup.LayoutParams(WindowUtil.getWindowWidth(activity),WindowUtil.getWindowHeight(activity)));
        mDialog.setCancelable(false);//点击其他区域无响应
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.show();
        return mDialog;
    }

    /**
     * @return 创建三行(WebView)提示框，有左右选项
     * 提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog tipsTwoButton(final AppCompatActivity activity, String leftText, String rightText, String describeText, final TwoButtonClickListener listener) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog.cancel();
        }
        View inflate = LayoutInflater.from(activity).inflate(R.layout.base_dialog_tips, null);
        TextView tvDescribe = (TextView) inflate.findViewById(R.id.bdt_tipsText);
        final TextView btLeft = (TextView) inflate.findViewById(R.id.bdt_leftButton);
        final TextView btRight = (TextView) inflate.findViewById(R.id.bdt_rightButton);
        if (describeText.contains("\\n"))
            describeText = describeText.replace("\\n", "\n");//
        tvDescribe.setText(describeText);
        btLeft.setText(leftText);
        btRight.setText(rightText);
        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss();
                if (listener != null)
                    listener.leftOnClick();
            }
        });
        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss();
                if (listener != null)
                    listener.rightOnClick();

            }
        });
        btLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btLeft.setTextColor(Color.parseColor("#880073ff"));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case  MotionEvent.ACTION_UP:// TODO: 2017/2/16 松开时
                        btLeft.setTextColor(Color.parseColor("#ff0073ff"));
                        break;
                }
                return false;
            }
        });

        btRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btRight.setTextColor(Color.parseColor("#880073ff"));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case  MotionEvent.ACTION_UP:// TODO: 2017/2/16 松开时
                        btRight.setTextColor(Color.parseColor("#ff0073ff"));
                        break;
                }
                return false;
            }
        });

        mDialog = new DialogCustom(activity);
        mDialog.setContentView(inflate,new ViewGroup.LayoutParams(WindowUtil.getWindowWidth(activity),WindowUtil.getWindowHeight(activity)));
        mDialog.setCancelable(false);//点击其他区域无响应
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.show();
        return mDialog;
    }

    /**
     * @param activity 使用 WindowManager.LayoutParams  lp.alpha = 1f;
     */
    public static void dialogDismiss(AppCompatActivity activity) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
            // 设置背景颜色变暗
            final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1f;
            activity.getWindow().setAttributes(lp);
        }
    }

    /**
     *  不使用 WindowManager.LayoutParams  lp.alpha = 1f;
     */
    public static void dialogDismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}

package com.mabang.android.utils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.activity.user.UserLoginActivity;
import com.mabang.android.activity.worker.WorkerLoginActivity;
import com.mabang.android.config.Api;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.entity.vo.VoBase;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.widget.MyPopupWindow;

import walke.base.tool.LogUtil;
import walke.base.tool.ToastUtil;
import walke.base.tool.ViewUtil;

/**
 * Created by walke on 2018/2/14.
 * email:1032458982@qq.com
 */

public class PopupWindowManager {

    /**
     * 显示退出提示框
     */
    public static void showExitTips(final AppActivity activity, View view, final String apiType) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.exit_tips_new, null);
        final TextView exitYes = (TextView) inflate.findViewById(R.id.exit_tips_yes);
        final TextView exitNo = (TextView) inflate.findViewById(R.id.exit_tips_no);
        View vOther = inflate.findViewById(R.id.exit_tips_other);
//        final PopupWindow pw = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final PopupWindow pw = new MyPopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        pw.setAnimationStyle(R.style.pwAnimationBottom);
        // popup window点击窗口外区域消失的解决方法 --顺序前后问题
        pw.setTouchable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());//必须设置背景
        pw.setFocusable(true);// 设置让pw获得焦点
        //设置pw出现方式
        pw.showAtLocation(view, Gravity.BOTTOM, 0, ViewUtil.dpToPx(activity, 45));
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);

        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });

        exitYes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        exitYes.setTextColor(Color.parseColor("#889d2325"));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case  MotionEvent.ACTION_UP:// TODO: 2017/2/16 松开时
                        exitYes.setTextColor(Color.parseColor("#ff9d2325"));
                        break;
                }
                return false;
            }
        });

        exitNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        exitNo.setTextColor(Color.parseColor("#880073ff"));//78aecdf3  880073ff
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case  MotionEvent.ACTION_UP:// TODO: 2017/2/16 松开时
                        exitNo.setTextColor(Color.parseColor("#ff0073ff"));
                        break;
                }
                return false;
            }
        });

        exitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**退出请求*/
                VoBase voBase = new VoBase();
                voBase.setAccount(activity.getMemberInfo().getAccount());
                voBase.setToken(activity.getMemberInfo().getToken());
                new HttpReuqest(activity).sendMessage(apiType, voBase, true, new HttpReuqest.CallBack<VoBase>() {
                    @Override
                    public void onSuccess(Message message, VoBase result) {
                        if (result.getCode() == Api.OK) {
                            localExit(activity, apiType);
                        }
                        ToastUtil.showToast(activity, result.getText());
                    }

                    @Override
                    public void onError(Exception exc) {
                        LogUtil.e("PopupWindowManager", "onError: Exception:" + exc);
                    }

                    @Override
                    public void onFinish() {
                        localExit(activity, apiType);
                        if (pw != null && pw.isShowing())
                            pw.dismiss();
                    }
                });
            }
        });

        exitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
        vOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }

    private static void localExit(AppActivity appActivity, String apiType) {
        appActivity.setMemberInfo(null);
        if (apiType.startsWith("20"))
            appActivity.startActivity(new Intent(appActivity, WorkerLoginActivity.class));
        else
            appActivity.startActivity(new Intent(appActivity, UserLoginActivity.class));
        appActivity.finish();
//        appActivity.appExit();
    }

}

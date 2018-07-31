package com.grandsea.ticketvendingapplication.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.view.ReturnButton;

/**
 * Created by Grandsea09 on 2017/10/7.
 */

public class DialogManager {

    /**
     * @return 提示框，有左右选项
     *      提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog dialogOneButton(final Context context, String describeText,String btText, final OneButtonClickListener listener) {

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_one_button, null);
        int width = ViewUtil.getWindowWidth(context);
        int height = ViewUtil.getWindowHeight(context);
        final Dialog dialog = new Dialog(context, R.style.bind_dialog);
        dialog.setContentView(inflate, new ViewGroup.LayoutParams((int) (width * 0.4), (int) (height * 0.45)));
        dialog.setCancelable(false);//点击其他区域无响应
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvDesc = (TextView) inflate.findViewById(R.id.dob_desc);

        tvDesc.setText(describeText);
        ReturnButton rbBack = (ReturnButton) inflate.findViewById(R.id.dob_rbBack);
        if (!TextUtils.isEmpty(btText))
            rbBack.getTvRight().setText(btText);
        rbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.alpha = 1f;
                ((AppCompatActivity)context).getWindow().setAttributes(lp);
                dialog.dismiss();
                if (listener != null)
                    listener.click(lp, dialog);

            }
        });
        if (!((BaseActivity) context).isFinishing())
            dialog.show();
        return dialog;
    }

    /**
     * @return 提示框，2按钮
     *      提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog dialogTwoButton(final Context context, String descText,String btTextLeft,String btTextRight, final DialogTwoButtonClickListener listener) {

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_two_button, null);
        int width = ViewUtil.getWindowWidth(context);
        int height = ViewUtil.getWindowHeight(context);
        final Dialog dialog = new Dialog(context, R.style.bind_dialog);
        dialog.setContentView(inflate, new ViewGroup.LayoutParams((int) (width * 0.5), (int) (height * 0.5)));
        dialog.setCancelable(false);//点击其他区域无响应
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvDesc = (TextView) inflate.findViewById(R.id.dtb_desc);
        tvDesc.setText(descText);
        ReturnButton rbLeft = (ReturnButton) inflate.findViewById(R.id.dtb_rbLeft);
        ReturnButton rbRight = (ReturnButton) inflate.findViewById(R.id.dtb_rbRight);
        rbLeft.getTvRight().setText(btTextLeft);
        rbRight.getTvRight().setText(btTextRight);
        rbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.leftOnClick(lp, dialog);
                dialog.dismiss();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        rbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.rightOnClick(lp, dialog);
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        if (!((BaseActivity) context).isFinishing())
            dialog.show();
        return dialog;
    }

    /**
     * @return 提示框，2按钮
     *      提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog createDialogTwoButton(final Context context, String titleText,String descText,String askText,final DialogTwoButtonClickListener listener) {

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_change_station, null);
        int width = ViewUtil.getWindowWidth(context);
        int height = ViewUtil.getWindowHeight(context);
        final Dialog dialog = new Dialog(context, R.style.bind_dialog);
        dialog.setContentView(inflate, new ViewGroup.LayoutParams((int) (width * 0.5), (int) (height * 0.5)));
        dialog.setCancelable(false);//点击其他区域无响应
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvTitle = (TextView) inflate.findViewById(R.id.dcs_title);
        TextView tvDesc = (TextView) inflate.findViewById(R.id.dcs_desc);
        TextView tvAsk = (TextView) inflate.findViewById(R.id.dcs_ask);
        tvTitle.setText(titleText);
        tvDesc.setText(descText);
        tvAsk.setText(askText);
        ReturnButton rbLeft = (ReturnButton) inflate.findViewById(R.id.dcs_rbLeft);
        ReturnButton rbRight = (ReturnButton) inflate.findViewById(R.id.dcs_rbRight);

        rbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.leftOnClick(lp, dialog);
                dialog.dismiss();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        rbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.rightOnClick(lp, dialog);
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        if (!((BaseActivity) context).isFinishing())
            dialog.show();
        return dialog;
    }

    /**
     * @return 提示框，2按钮
     *      提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog dialogGetIdInfo(final Context context, final DialogTwoButtonClickListener listener) {

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_get_idinfo, null);
        int width = ViewUtil.getWindowWidth(context);
        int height = ViewUtil.getWindowHeight(context);
        final Dialog dialog = new Dialog(context, R.style.bind_dialog);
        dialog.setContentView(inflate, new ViewGroup.LayoutParams((int) (width * 0.45), ViewGroup.LayoutParams.WRAP_CONTENT ));//(int) (height * 0.6)
        dialog.setCancelable(false);//点击其他区域无响应
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ReturnButton leftButton = (ReturnButton) inflate.findViewById(R.id.dgi_rbLeft);
        ReturnButton rightButton = (ReturnButton) inflate.findViewById(R.id.dgi_rbRight);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.leftOnClick(lp, dialog);
                dialog.dismiss();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.rightOnClick(lp, dialog);
                lp.alpha = 1f;
                //((Activity) context).getWindow().setAttributes(lp);
            }
        });
        if (!((BaseActivity) context).isFinishing())
            dialog.show();
        return dialog;
    }

    /**
     * @return 提示框，1按钮
     *      提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog dialogPageTurn(final Context context, String describeText, final OneButtonClickListener listener) {

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_page_turn, null);
        int width = ViewUtil.getWindowWidth(context);
        int height = ViewUtil.getWindowHeight(context);
        final Dialog dialog = new Dialog(context, R.style.bind_dialog);
        dialog.setContentView(inflate, new ViewGroup.LayoutParams((int) (width * 0.4), (int) (height * 0.45)));
        dialog.setCancelable(false);//点击其他区域无响应
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvDesc = (TextView) inflate.findViewById(R.id.dpt_desc);

        tvDesc.setText(describeText);
        ReturnButton rbBack = (ReturnButton) inflate.findViewById(R.id.dpt_rbBack);

        rbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.alpha = 1f;
                ((AppCompatActivity)context).getWindow().setAttributes(lp);
                dialog.dismiss();
                if (listener != null)
                    listener.click(lp, dialog);

            }
        });
        if (!((BaseActivity) context).isFinishing())
            dialog.show();
        return dialog;
    }
    /**
     * @return 提示框，有左右选项
     *      提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog dialogNoStation(final Context context, String describeText, final OneButtonClickListener listener) {

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_no_station, null);
        int width = ViewUtil.getWindowWidth(context);
        int height = ViewUtil.getWindowHeight(context);
        final Dialog dialog = new Dialog(context, R.style.bind_dialog);
        dialog.setContentView(inflate, new ViewGroup.LayoutParams((int) (width * 0.4), (int) (height * 0.45)));
        dialog.setCancelable(false);//点击其他区域无响应
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvDesc = (TextView) inflate.findViewById(R.id.dns_desc);

        tvDesc.setText(describeText);
        ReturnButton rbBack = (ReturnButton) inflate.findViewById(R.id.dns_rbBack);

        rbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.alpha = 1f;
                ((AppCompatActivity)context).getWindow().setAttributes(lp);
                dialog.dismiss();
                if (listener != null)
                    listener.click(lp, dialog);

            }
        });
        if (!((BaseActivity) context).isFinishing())
            dialog.show();
        return dialog;
    }
    /**
     * @return 提示框，有左右选项
     *      提示
     * （js返回的信息）
     * （按钮1）   （按钮2）
     */
    public static Dialog dialogPayTips(final Context context,String title, String desc,String descTips,int iconId, final OneButtonClickListener listener) {

        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_pay_tips, null);
        int width = ViewUtil.getWindowWidth(context);
        int height = ViewUtil.getWindowHeight(context);
        final Dialog dialog = new Dialog(context, R.style.bind_dialog);
        dialog.setContentView(inflate, new ViewGroup.LayoutParams((int) (width * 0.5), (int) (height * 0.5)));
        dialog.setCancelable(false);//点击其他区域无响应
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvTitle = (TextView) inflate.findViewById(R.id.dPay_title);
        TextView tvDesc = (TextView) inflate.findViewById(R.id.dPay_desc);
        TextView tvDescTips = (TextView) inflate.findViewById(R.id.dPay_descTips);
        ImageView ivDesc = (ImageView) inflate.findViewById(R.id.dPay_ivDesc);
        ReturnButton rbBack = (ReturnButton) inflate.findViewById(R.id.dPay__rbBack);
        tvTitle.setText(""+title);
        tvDesc.setText(""+desc);
        tvDescTips.setText(""+descTips);
        ivDesc.setImageResource(iconId);
        rbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.alpha = 1f;
                ((AppCompatActivity)context).getWindow().setAttributes(lp);
                dialog.dismiss();
                if (listener != null)
                    listener.click(lp, dialog);

            }
        });
        if (!((BaseActivity) context).isFinishing())
            dialog.show();
        return dialog;
    }



    public interface DialogTwoButtonClickListener {
        void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog);

        void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog);
    }

    public interface OneButtonClickListener {
        void click(WindowManager.LayoutParams lp, Dialog dialog);
    }

}

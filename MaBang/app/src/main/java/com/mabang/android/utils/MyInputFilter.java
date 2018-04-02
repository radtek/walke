package com.mabang.android.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/3/6.
 */
public class MyInputFilter implements InputFilter {

    private static final String number = "0123456789-";

    /**
     *  @param source 新输入的字符串
     *  @param start  新输入的字符串起始下标，一般为0
     *  @param end    新输入的字符串终点下标，一般为source长度-1
     *  @param dest   输入之前文本框内容
     *  @param dstart 原内容起始坐标，一般为0
     *  @param dend   原内容终点坐标，一般为dest长度-1
     *  @icon_return       输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }
        if (destText.length()>=12){
            return "";
        }
        //验证前17位是数字
        if (destText.length()<12){
            if (!number.contains(source)) {
                return "";
            }
        }
        return dest.subSequence(dstart, dend) + sourceText;
    }
}

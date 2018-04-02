package walke.base.tool;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by walke on 2018/2/24.
 * email:1032458982@qq.com
 */

public class SpannableUtil {

    /** SpannableStringBuilder的使用,TextView部分字体变颜色
     * @param front
     * @param tag
     * @param after
     * @param color
     */
    public static void spannableOneColor(TextView tv,String front, String tag, String after, int color) {
        String strAll = front + tag + after;
        int winStart = strAll.indexOf(tag);
        int winEnd = winStart + tag.length();
        SpannableStringBuilder style = new SpannableStringBuilder(strAll);
        style.setSpan(new ForegroundColorSpan(color), winStart, winEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);
    }

    /** SpannableStringBuilder的使用,TextView部分字体变颜色，bug:当tag1，tag2内容接近则会异常，比如：tag1=“122234”，tag2=“122”
     * @param front
     * @param tag1
     * @param after1
     * @param color
     */
    public static void spannableTwoColor(TextView tv,String front, String tag1, String after1, String tag2, String after2,int color) {
        String strAll = front + tag1 + after1+tag2+after2;
        int winStart1 = strAll.indexOf(tag1);
        int winEnd1 = winStart1 + tag1.length();
        int winStart2 = strAll.indexOf(tag2);
        int winEnd2 = winStart2 + tag2.length();
        SpannableStringBuilder style = new SpannableStringBuilder(strAll);
        style.setSpan(new ForegroundColorSpan(color), winStart1, winEnd1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color), winStart2, winEnd2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);
    }
    /** SpannableStringBuilder的使用,TextView部分字体变颜色
     * @param front
     * @param tag1
     * @param after1
     * @param color
     */
    public static void spannableTwoColorAndSize(TextView tv,String front, String tag1, String after1, String tag2,int color) {
        String strAll = front + tag1 + after1+tag2;
        int winStart1 = strAll.indexOf(tag1);
        int winEnd1 = winStart1 + tag1.length();
        int winStart2 = strAll.indexOf(tag2);
        int winEnd2 = winStart2 + tag2.length();
        SpannableStringBuilder style = new SpannableStringBuilder(strAll);
        style.setSpan(new ForegroundColorSpan(color), winStart1, winEnd1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(ViewUtil.dpToPx(tv.getContext(),13)), winStart1, winEnd1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color), winStart2, winEnd2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(ViewUtil.dpToPx(tv.getContext(),13)), winStart2, winEnd2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        tv.setText(style);
    }


    /** SpannableStringBuilder的使用,TextView部分字体变颜色
     * @param tv
     * @param text 全文本
     * @param tag1 变色字体1
     * @param tag2 变色字体2
     * @param color
     */
    public void tvSpannable2(TextView tv, String text, String tag1,String tag2,int color) {
//        String str = "请携带此张彩票和身份证\n前往体彩中心兑奖";
        int bstart = text.indexOf(tag1);
        int bend = bstart + tag1.length();
        int fstart = text.indexOf(tag2);
        int fend = fstart + tag2.length();
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(color), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);
    }

}

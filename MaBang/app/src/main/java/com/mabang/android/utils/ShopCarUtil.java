package com.mabang.android.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mabang.android.R;

import walke.base.tool.BitmapUtil;
import walke.base.tool.LayoutParamsUtil;
import walke.base.tool.ViewUtil;



/**
 * Created by walke.Z on 2018/1/31.
 */

public class ShopCarUtil {

    public static void setAnim(AppCompatActivity compatActivity, final View startView, View endView, int scaleTime, int flyTime) {

        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        startView.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        int hValue = ViewUtil.dpToPx(compatActivity, startView.getHeight());
        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(ViewUtil.dpToPx(compatActivity, startView.getWidth()), hValue);
        final ImageView moveView  = new ImageView(compatActivity);
        moveView.setLayoutParams(lp);
        moveView.setImageResource(R.mipmap.scan_buy_2x108);
//        moveView.setScaleType(ImageView.ScaleType.FIT_XY);

        final ViewGroup animLayout = createAnimLayout(compatActivity);//动画层
        animLayout.addView(moveView);//把动画小球添加到动画层
        addViewToAnimLayout(moveView, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        endView.getLocationInWindow(endLocation);
        float x = endView.getX();
        float y = endView.getY();
        float pivotX = endView.getPivotX();
        float pivotY = endView.getPivotY();
        // 计算位移
//        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endX = endLocation[0] - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1]- ((int) (hValue * 0.3));// 动画位移的y坐标  0.3与缩小的幅度对应

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,endX, 0, 0);
//        translateAnimationX.setInterpolator(new DecelerateInterpolator());
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new DecelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        final AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(flyTime);// 动画的执行时间
//        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                moveView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                moveView.setVisibility(View.GONE);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(moveView, "scaleX", 1f, 0.3f);
        scaleXAnimator.setDuration(scaleTime);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(moveView, "scaleY", 1f, 0.3f);
        scaleYAnimator.setDuration(scaleTime);
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                moveView.startAnimation(set);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public static void setAnim(AppCompatActivity compatActivity, final View startView,int scaleTime) {

        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        startView.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        int hValue = ViewUtil.dpToPx(compatActivity, startView.getHeight());
        //lp 给覆盖图(动画层的动画控件)设置同样大小属性
        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(ViewUtil.dpToPx(compatActivity, startView.getWidth()), hValue);
        final ImageView tagView  = new ImageView(compatActivity);
        tagView.setLayoutParams(lp);
        tagView.setImageResource(R.mipmap.icon_overlay_blue);
//        Bitmap viewBitmap = ImgBitmapUtil.getViewBitmap(startView);
//        tagView.setImageBitmap(viewBitmap);

        final ViewGroup animLayout = createAnimLayout(compatActivity);//动画层
        animLayout.addView(tagView);//把动画小球添加到动画层
        addViewToAnimLayout(tagView, startLocation);//

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(tagView, "scaleX", 1f, 0.3f);
        scaleXAnimator.setDuration(scaleTime);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(tagView, "scaleY", 1f, 0.3f);
        scaleYAnimator.setDuration(scaleTime);
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

    public static void setAnim2(AppCompatActivity compatActivity, final View startView,int scaleTime) {

        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        startView.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        int viewHeight = ViewUtil.getViewHeight(startView);
        int viewWidth = ViewUtil.getViewWidth(startView);
        int hValue = ViewUtil.dpToPx(compatActivity, startView.getHeight());
        //lp 给覆盖图(动画层的动画控件)设置同样大小属性
//        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(ViewUtil.dpToPx(compatActivity, startView.getWidth()), hValue);
//        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(viewWidth, viewHeight);
        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(ViewUtil.dpToPx(compatActivity,(viewWidth-8)), ViewUtil.dpToPx(compatActivity, (viewHeight-8)));
        final ImageView tagView  = new ImageView(compatActivity);
        tagView.setLayoutParams(lp);
        Bitmap viewBitmap = BitmapUtil.getBitmapByView(startView);
        tagView.setImageBitmap(viewBitmap);

        final ViewGroup animLayout = createAnimLayout(compatActivity);//动画层
        animLayout.addView(tagView);//把动画小球添加到动画层
        addViewToAnimLayout(tagView, startLocation);//

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(tagView, "scaleX", 1f, 1.3f,1f);
        scaleXAnimator.setDuration(scaleTime);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(tagView, "scaleY", 1f, 1.3f,1f);
        scaleYAnimator.setDuration(scaleTime);
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                animLayout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

    public static void setAnim3(AppCompatActivity compatActivity, final View startView, Point point, int scaleTime) {

        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        int viewHeight = ViewUtil.getViewHeight(startView);
        int viewWidth = ViewUtil.getViewWidth(startView);
//        startLocation[0]=point.x-viewWidth;
//        startLocation[1]=point.y;
//        startLocation[0]=point.x;
//        startLocation[1]=point.y+viewHeight;

        startLocation[0]=point.x-(viewWidth/2);
        startLocation[1]=point.y+(viewHeight/2)-ViewUtil.dpToPx(compatActivity,14);//icon 56dp宽高
        int hValue = ViewUtil.dpToPx(compatActivity, startView.getHeight());
        //lp 给覆盖图(动画层的动画控件)设置同样大小属性
//        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(ViewUtil.dpToPx(compatActivity,(viewWidth-8)), ViewUtil.dpToPx(compatActivity, (viewHeight-8)));
        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(viewWidth, viewHeight);
        final ImageView tagView  = new ImageView(compatActivity);
        tagView.setLayoutParams(lp);
        Bitmap viewBitmap = BitmapUtil.getBitmapByView(startView);
        tagView.setImageBitmap(viewBitmap);

        final ViewGroup animLayout = createAnimLayout(compatActivity);//动画层
        animLayout.addView(tagView);//把动画小球添加到动画层
        addViewToAnimLayout(tagView, startLocation);//

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(tagView, "scaleX", 1f, 1.5f,1f);
        scaleXAnimator.setDuration(scaleTime);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(tagView, "scaleY", 1f, 1.5f,1f);
        scaleYAnimator.setDuration(scaleTime);
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                animLayout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

    public static void setAnim(AppCompatActivity compatActivity, final View startView, Point point, int scaleTime, final AnimListener animListener) {

        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        int viewHeight = ViewUtil.getViewHeight(startView);
        int viewWidth = ViewUtil.getViewWidth(startView);

        startLocation[0]=point.x-(viewWidth/2);
        startLocation[1]=point.y+(viewHeight/2)-ViewUtil.dpToPx(compatActivity,14);//icon 56dp宽高 //icon 52dp宽高 -- 8
        int hValue = ViewUtil.dpToPx(compatActivity, startView.getHeight());
        //lp 给覆盖图(动画层的动画控件)设置同样大小属性
        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(viewWidth, viewHeight);
        final ImageView tagView  = new ImageView(compatActivity);
        tagView.setLayoutParams(lp);
        Bitmap viewBitmap = BitmapUtil.getBitmapByView(startView);
        tagView.setImageBitmap(viewBitmap);

        final ViewGroup animLayout = createAnimLayout(compatActivity);//动画层
        animLayout.addView(tagView);//把动画小球添加到动画层
        addViewToAnimLayout(tagView, startLocation);//

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(tagView, "scaleX", 1f, 1.5f,1f);
        scaleXAnimator.setDuration(scaleTime);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(tagView, "scaleY", 1f, 1.5f,1f);
        scaleYAnimator.setDuration(scaleTime);
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                animLayout.setVisibility(View.GONE);
                if (animListener!=null)
                    animListener.end();
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

    public interface AnimListener{
        void end();
    }

    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private static ViewGroup createAnimLayout(AppCompatActivity compatActivity) {

        ViewGroup rootView = (ViewGroup) compatActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(compatActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }


    private static void addViewToAnimLayout(final ImageView view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
    }

}

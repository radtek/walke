package com.mabang.android.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabang.android.R;

/**
 * Created by walke on 2018/3/14.
 * email:1032458982@qq.com
 */

public class MapOverlayView extends FrameLayout {
    private ImageView ivOverlay;
    private TextView tvNumber;

    public MapOverlayView(@NonNull Context context, String number, int iconId) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_to_bitmap,this);
        tvNumber = ((TextView) findViewById(R.id.vtb_tvNumber));
        ivOverlay = ((ImageView) findViewById(R.id.vtb_ivOverlay));
        ivOverlay.setScaleType(ImageView.ScaleType.FIT_CENTER);
        tvNumber.setText(number+"");
        ivOverlay.setImageResource(iconId);
    }

    public MapOverlayView(@NonNull Context context) {
        this(context,null);
    }

    public MapOverlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MapOverlayView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_to_bitmap,this);
        tvNumber = ((TextView) findViewById(R.id.vtb_tvNumber));
        ivOverlay = ((ImageView) findViewById(R.id.vtb_ivOverlay));
    }



    public TextView getTvNumber() {
        return tvNumber;
    }

    public void setNumber(@NonNull String number){
        tvNumber.setText(number);

    }

}

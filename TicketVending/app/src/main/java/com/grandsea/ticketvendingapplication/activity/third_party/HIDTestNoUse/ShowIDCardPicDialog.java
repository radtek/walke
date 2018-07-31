package com.grandsea.ticketvendingapplication.activity.third_party.HIDTestNoUse;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.grandsea.ticketvendingapplication.R;


public class ShowIDCardPicDialog extends Dialog implements OnShowListener, OnDismissListener{

    private Bitmap mBmpIDFront, mBmpIDBack;
    private ImageView mImgFront, mImgBack;

    public ShowIDCardPicDialog(Context context) {
        super(context);
        setTitle("身份证正反面");
        setContentView(R.layout.third_idcard_show_id_pic_dialog);
        setOnShowListener(this);
        setOnDismissListener(this);
        mImgFront = (ImageView)findViewById(R.id.imgFrontPic);
        mImgBack = (ImageView)findViewById(R.id.imgBackPic);
    }

    public void setBitmap(Bitmap front, Bitmap back){
        mBmpIDFront = front;
        mBmpIDBack = back;
    }

    public void onShow(DialogInterface dialog) {
        mImgFront.setImageBitmap(mBmpIDFront);
        mImgBack.setImageBitmap(mBmpIDBack);
    }

    public void onDismiss(DialogInterface dialog) {

    }
}

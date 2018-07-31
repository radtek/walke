package com.grandsea.ticketvendingapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.util.ToastUtil;

/**
 * Created by Grandsea09 on 2017/10/9.
 */

public class SelectTicketViewOld extends LinearLayout implements View.OnClickListener {
    private ImageView ivIcon,ivMinus,ivAdd;
    private TextView tvTicketsInfo;
//    private EditText etNumber;
    private TextView etNumber;
    private int ticketMax=0;
    private int number;

    public SelectTicketViewOld(Context context) {
        this(context,null);
    }

    public SelectTicketViewOld(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SelectTicketViewOld(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_select_tickets, this);
        ivIcon = ((ImageView) findViewById(R.id.vst_icon));
        ivMinus = ((ImageView) findViewById(R.id.vst_ivMinus));
        ivAdd = ((ImageView) findViewById(R.id.vst_ivAdd));
        tvTicketsInfo = ((TextView) findViewById(R.id.vst_tvTicketsInfo));
//        etNumber = ((EditText) findViewById(R.id.vst_number));
        etNumber = ((TextView) findViewById(R.id.vst_number));
        ivMinus.setOnClickListener(this);
        ivAdd.setOnClickListener(this);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SelectTicketViewOld, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SelectTicketViewOld_img:
                    int resourceId = a.getResourceId(attr, -1);
                    if (-1!=resourceId)
                        ivIcon.setImageResource(resourceId);
                    break;
                case R.styleable.SelectTicketViewOld_textContent:
                    String text = a.getString(attr);
                    if (!TextUtils.isEmpty(text))
                        tvTicketsInfo.setText(text);
                    break;
            }
        }
        a.recycle();
    }



    public int getNumber() {
        return number;
    }

    public void setTicketMax(int ticketMax) {
        this.ticketMax = ticketMax;
        if (number>ticketMax){
            number=ticketMax;
            etNumber.setText(number+"");
        }
    }

    public void setSelectEnable(boolean selectEnable){

        ivAdd.setEnabled(selectEnable);
        ivAdd.setImageResource(R.mipmap.shift_add_gray);
        ivMinus.setEnabled(selectEnable);
        ivMinus.setImageResource(R.mipmap.shift_minus_gray);
        etNumber.setTextColor(getContext().getResources().getColor(R.color.color_bfbfbf));

    }

    @Override
    public void onClick(View v) {
        String s = etNumber.getText().toString().trim();
        number = Integer.parseInt(s);
        if (v==ivMinus){
            if (number >0) {
                number--;
            }else {
                ToastUtil.showToast(getContext(),"不能小于0");
                return;
            }
            etNumber.setText(number+"");
            if (null!= selectTicketListenerOld) {
                selectTicketListenerOld.changed(number);
            }
        }else if (v==ivAdd){
            if (number <ticketMax){
                number++;
            }else {
//                ToastUtil.showToast(getContext(),"剩余："+ticketMax+" 张");//此票只
//                ToastUtil.showToast(getContext(),"已达购票上限(注:一次最多购买6张)");//此票只
                return;
            }
            etNumber.setText(number+"");
            if (null!= selectTicketListenerOld) {
                selectTicketListenerOld.changed(number);
            }
        }
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }

    public ImageView getIvMinus() {
        return ivMinus;
    }

    public ImageView getIvAdd() {
        return ivAdd;
    }

    public TextView getTvTicketsInfo() {
        return tvTicketsInfo;
    }

    public TextView getEtNumber() {
        return etNumber;
    }

    public interface SelectTicketListenerOld {
        void changed(int day);
    }

    private SelectTicketListenerOld selectTicketListenerOld;

    public SelectTicketListenerOld getSelectTicketListenerOld() {
        return selectTicketListenerOld;
    }

    public void setSelectTicketListenerOld(SelectTicketListenerOld selectTicketListenerOld) {
        this.selectTicketListenerOld = selectTicketListenerOld;
    }

}

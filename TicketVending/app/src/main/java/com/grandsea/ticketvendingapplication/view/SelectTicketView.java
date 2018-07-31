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

public class SelectTicketView extends LinearLayout implements View.OnClickListener {
    private ImageView ivIcon,ivMinus,ivAdd;
    private TextView ticketsInfo;
//    private EditText etNumber;
    private TextView etNumber;
    private int number;

    public SelectTicketView(Context context) {
        this(context,null);
    }

    public SelectTicketView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SelectTicketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_select_tickets, this);
        ivIcon = ((ImageView) findViewById(R.id.vst_icon));
        ivMinus = ((ImageView) findViewById(R.id.vst_ivMinus));
        ivAdd = ((ImageView) findViewById(R.id.vst_ivAdd));
        ticketsInfo = ((TextView) findViewById(R.id.vst_tvTicketsInfo));
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
                        ticketsInfo.setText(text);
                    break;
            }
        }
        a.recycle();
    }

    public int getNumber() {
        return number;
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
            if (null!=selectTicketListener) {
                selectTicketListener.minus();
            }
        }else if (v==ivAdd){

            if (null!=selectTicketListener) {
                selectTicketListener.add();
            }
        }
    }


    public interface SelectTicketListener{
        void add();
        void minus();
    }

    private SelectTicketListener selectTicketListener;

    public SelectTicketListener getSelectTicketListener() {
        return selectTicketListener;
    }

    public void setSelectTicketListener(SelectTicketListener selectTicketListener) {
        this.selectTicketListener = selectTicketListener;
    }

}

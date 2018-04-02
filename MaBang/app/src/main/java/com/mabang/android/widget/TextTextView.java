package com.mabang.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mabang.android.R;


/**
 * Created by hui on 2017/10/28.
 */

public class TextTextView extends LinearLayout {

   private TextView tvTitle,tvDesc;

   public TextTextView(Context context) {
      this(context,null);
   }

   public TextTextView(Context context, @Nullable AttributeSet attrs) {
      this(context, attrs,0);
   }

   public TextTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      LayoutInflater.from(context).inflate(R.layout.view_text_text,this);
      tvTitle = ((TextView) findViewById(R.id.vtt_tvTitle));
      tvDesc = ((TextView) findViewById(R.id.vtt_tvDesc));
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextTextView, defStyleAttr, 0);
      int n = a.getIndexCount();
      for (int i = 0; i < n; i++) {
         int attr = a.getIndex(i);
         if (attr == R.styleable.TextTextView_leftText) {
            String str = a.getString(attr);
            if (!TextUtils.isEmpty(str))
               tvTitle.setText(str);
         } else if (attr == R.styleable.TextTextView_rightText) {
            String str = a.getString(attr);
            if (!TextUtils.isEmpty(str))
               tvDesc.setText(str);
         }else if (attr == R.styleable.TextTextView_size) {
            int size = a.getInt(attr,14);
            tvTitle.setTextSize(size);
            tvDesc.setTextSize(size);
         }
      }
      a.recycle();

   }

   public TextView getTvTitle() {
      return tvTitle;
   }

   public TextView getTvDesc() {
      return tvDesc;
   }
}

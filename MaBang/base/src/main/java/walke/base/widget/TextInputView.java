package walke.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import walke.base.R;

/**
 * Created by hui on 2017/10/28.
 */

public class TextInputView extends LinearLayout {

   private TextView tvTips;
   private EditText etInput;
   private final ImageView ivStar;
   private final View vLine;

   public TextInputView(Context context) {
      this(context,null);
   }

   public TextInputView(Context context, @Nullable AttributeSet attrs) {
      this(context, attrs,0);
   }

   public TextInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      LayoutInflater.from(context).inflate(R.layout.base_view_text_input,this);
      tvTips = ((TextView) findViewById(R.id.textInput_tvTips));
      etInput = ((EditText) findViewById(R.id.textInput_etInput));
      ivStar = ((ImageView) findViewById(R.id.textInput_ivStar));
      vLine = findViewById(R.id.textInput_vLine);
      ivStar.setVisibility(GONE);
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextInputView, defStyleAttr, 0);
      int n = a.getIndexCount();
      for (int i = 0; i < n; i++) {
         int attr = a.getIndex(i);
         if (attr == R.styleable.TextInputView_iconRight) {
            int resourceId = a.getResourceId(attr, -1);
            if (resourceId!=-1) {
               ivStar.setVisibility(VISIBLE);
               ivStar.setImageResource(resourceId);
            }
         }else if (attr == R.styleable.TextInputView_tipsText) {
            String str = a.getString(attr);
            if (!TextUtils.isEmpty(str))
               tvTips.setText(str);
         } else if (attr == R.styleable.TextInputView_hintText) {
            String str = a.getString(attr);
            if (!TextUtils.isEmpty(str))
               etInput.setHint(str);
         }
      }
      a.recycle();

   }

   public EditText getEtInput() {
      return etInput;
   }

   public TextView getTvTips() {
      return tvTips;
   }

   public ImageView getIvStar() {
      return ivStar;
   }

   public View getvLine() {
      return vLine;
   }
}

package walke.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import walke.base.R;
import walke.base.tool.ToastUtil;
import walke.base.tool.Util;

/**
 * Created by hui on 2017/10/28.
 */

public class LoginInputView extends LinearLayout {

   private final static int PHONE =1;
   private final static int PHONE_AUTH_CODE =2;
   private final static int PASSWORD =3;
   private final static int OTHER =4;
   private ImageView ivIcon;
   private EditText etInput;
   private LoginCountdownView mCountdownView;

   public LoginInputView(Context context) {
      this(context,null);
   }

   public LoginInputView(Context context, @Nullable AttributeSet attrs) {
      this(context, attrs,0);
   }

   public LoginInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      LayoutInflater.from(context).inflate(R.layout.base_view_login_input,this);
      ivIcon = ((ImageView) findViewById(R.id.vli_icon));
      etInput = ((EditText) findViewById(R.id.vli_input));
      mCountdownView = ((LoginCountdownView) findViewById(R.id.vli_loginCountdownView));
      mCountdownView.setVisibility(GONE);
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoginInputView, defStyleAttr, 0);
      int n = a.getIndexCount();
      for (int i = 0; i < n; i++) {
         int attr = a.getIndex(i);
         if (attr == R.styleable.LoginInputView_inputIcon) {
            int resourceId = a.getResourceId(attr, R.mipmap.ic_launcher);
            ivIcon.setImageResource(resourceId);
         } else if (attr == R.styleable.LoginInputView_inputHint) {
            String str = a.getString(attr);
            if (!TextUtils.isEmpty(str))
               etInput.setHint(str);
         } else if (attr == R.styleable.LoginInputView_inputLength) {
            int integer = a.getInteger(attr, -1);
            if (integer!=-1)
               etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(integer)});

         }else if (attr == R.styleable.LoginInputView_inputType) {
            int etType = a.getInt(attr, -1);
            if (etType== PHONE) {
               etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
               etInput.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            }else if (etType== PHONE_AUTH_CODE){
               etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
               etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
               mCountdownView.setVisibility(VISIBLE);
            }else if (etType== PASSWORD){
               etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
               etInput.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);//
               etInput.setTransformationMethod(PasswordTransformationMethod.getInstance());//默认不显示
            }else if (etType== OTHER){
               etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
               etInput.setInputType(InputType.TYPE_CLASS_TEXT);
            }
         }
      }
      a.recycle();

   }


   public ImageView getIvIcon() {
      return ivIcon;
   }

   public EditText getEtInput() {
      return etInput;
   }

   public LoginCountdownView getCountdownView() {
      return mCountdownView;
   }

   public boolean isFitPhone() {
      String inputText = etInput.getText().toString().trim();
      if (TextUtils.isEmpty(inputText)) {
         ToastUtil.showToast(getContext(),"请输入手机号");
         return false;
      }else if (!Util.isMobile(inputText)){
         ToastUtil.showToast(getContext(),"请输入正确的手机号码");
         return false;
      }
      return true;
   }

   public boolean isFitPassword() {
      String inputText = etInput.getText().toString().trim();
      if (TextUtils.isEmpty(inputText)) {
         ToastUtil.showToast(getContext(),"请输入密码");
         return false;
      }else if (inputText.length()<6||inputText.length()>15){
         ToastUtil.showToast(getContext(),"密码格式有误，请重新输入(6~15位)");
         return false;
      }
      return true;
   }

   public boolean isFitPhoneAuthCode() {
      String inputText = etInput.getText().toString().trim();
      if (TextUtils.isEmpty(inputText)) {
         ToastUtil.showToast(getContext(),"请输入验证码");
         return false;
      }else if (inputText.length() >4){
         ToastUtil.showToast(getContext(),"验证码格式有误，请重新输入");
         return false;
      }
      return true;
   }
   public boolean isFitImageAuthCode() {
      String inputText = etInput.getText().toString().trim();
      if (TextUtils.isEmpty(inputText)) {
         ToastUtil.showToast(getContext(),"请输入验证码");
         return false;
      }else if (inputText.length() >6){
         ToastUtil.showToast(getContext(),"图形验证码格式有误，请重新输入");
         return false;
      }
      return true;
   }
   public boolean isFitSellCode() {
      String inputText = etInput.getText().toString().trim();
      if (TextUtils.isEmpty(inputText)) {
         ToastUtil.showToast(getContext(),"请输入验证码");
         return false;
      }else if (inputText.length() >10){
         ToastUtil.showToast(getContext(),"验证码格式有误，请重新输入");
         return false;
      }
      return true;
   }
}

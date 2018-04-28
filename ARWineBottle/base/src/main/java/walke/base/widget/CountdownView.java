package walke.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import walke.base.R;
import walke.base.BaseApp;
import walke.base.tool.SharepreUtil;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/2/9.
 */
@SuppressLint("AppCompatCustomView")
public class CountdownView extends TextView {

    public static final int MAX_TIME =60;

    private Context context;
    private Handler mHandler=new Handler();
    private boolean isTimeDowning=false;
    private int time= MAX_TIME;
    private Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            if (time>=0) {
                timeDown();//先减1，再赋值//startTime--:先赋值，再减1
                isTimeDowning=true;
            }else {
                isTimeDowning=false;
                setViewEnable("获取验证码", R.drawable.bg_enable);
                mHandler.removeCallbacks(mRunnable);
            }
        }
    };

    private void timeDown() {
        CountdownView.this.setText("重新发送(" + time + "s)");
        CountdownView.this.setEnabled(false);//不可用
        CountdownView.this.setBackgroundResource(R.drawable.bg_unable);
//        ((BaseActivity) context).getGuaGuayiApplication().setIdentifyCodeTime(time--);//先赋值再减1
        ((BaseApp) context.getApplicationContext()).setCountdownTime(time--);
        mHandler.postDelayed(mRunnable,1000);
    }

    public CountdownView(Context context) {
        this(context,null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public boolean isTimeDowning() {
        return isTimeDowning;
    }

    public void startTimeDown(int startTime) {
        time=startTime;
        time--;
        setViewUnable(startTime, R.drawable.bg_unable);
        mHandler.postDelayed(mRunnable,1000);
    }

    public void startTimeDown(int startTime, final EditText etCode) {
        time=startTime;
        time--;
        setViewUnable(startTime, R.drawable.bg_unable);
        mHandler.postDelayed(mRunnable,1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editTextGetFocus(etCode);
            }
        },200);
    }

    /** 不可点击时的显示
     * @param startTime 倒计时间
     * @param code_btn_unable 背景
     */
    public void setViewUnable(int startTime, int code_btn_unable) {
        CountdownView.this.setEnabled(false);//不可用
        CountdownView.this.setText("重新发送(" + startTime + "s)");
        CountdownView.this.setBackgroundResource(code_btn_unable);
    }

    /** 可点击时的显示
     * @param text 文本
     * @param code_btn_enable 背景
     */
    public void setViewEnable( String text, int code_btn_enable) {
        CountdownView.this.setEnabled(true);//可用
        CountdownView.this.setText(text);
        CountdownView.this.setBackgroundResource(code_btn_enable);
    }

    public void pauseCountdownTime(){
        setViewEnable("获取验证码", R.drawable.bg_enable);
        mHandler.removeCallbacks(mRunnable);
    }
    public void stopCountdownTime(){
//        ((BaseActivity) context).getGuaGuayiApplication().setIdentifyCodeTime(0);
        ((BaseApp) context.getApplicationContext()).setCountdownTime(0);
        setViewEnable("获取验证码", R.drawable.bg_enable);
        mHandler.removeCallbacks(mRunnable);
    }
    public void removeRunable(){
        mHandler.removeCallbacks(mRunnable);
    }



    public void init(EditText etPhone, final EditText etCode) {
        int time = ((BaseApp) context.getApplicationContext()).getCountdownTime();
        if (time>0&&time< CountdownView.MAX_TIME){
            startTimeDown(time);//time
            String lastPhone = SharepreUtil.getString(getContext(), Config.LAST_INPUT_PHONE);//获取手机验证码最近的手机号
            if (!TextUtils.isEmpty(lastPhone)){
                etPhone.setText(lastPhone);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editTextGetFocus(etCode);
                    }
                },200);
            }
        }
    }

    /**
     * @param editText 获取焦点
     */
    public void editTextGetFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        /*boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开 
        if (!isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }*/
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void savePhone(String phone) {
        SharepreUtil.putString(getContext(), Config.LAST_INPUT_PHONE,phone);//保存获取手机验证码最近的手机号
    }
}

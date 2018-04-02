package walke.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import walke.base.BaseApp;
import walke.base.tool.SharepreUtil;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/2/9.
 */
@SuppressLint("AppCompatCustomView")
public class LoginCountdownView extends TextView {

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
            }else {
                setViewEnable("获取验证码");
                mHandler.removeCallbacks(mRunnable);
            }
        }
    };

    private void timeDown() {
        isTimeDowning=true;
        LoginCountdownView.this.setText("重新发送(" + time + "s)");
        LoginCountdownView.this.setEnabled(false);//不可用
        LoginCountdownView.this.setTextColor(Color.parseColor("#ffbfbfbf"));
        ((BaseApp) context.getApplicationContext()).setCountdownTime(time--);//先赋值再减1
        mHandler.postDelayed(mRunnable,1000);
    }

    public LoginCountdownView(Context context) {
        this(context,null);
    }

    public LoginCountdownView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginCountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        setGravity(Gravity.CENTER);
    }

    public boolean isTimeDowning() {
        return isTimeDowning;
    }

    public void startTimeDown(int startTime) {
        time=startTime;
        time--;
        setViewUnable(startTime);
        mHandler.postDelayed(mRunnable,1000);
    }

    public void startTimeDown(int startTime, final EditText etCode) {
        time=startTime;
        time--;
        setViewUnable(startTime);
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
     */
    public void setViewUnable(int startTime) {
        LoginCountdownView.this.setEnabled(false);//不可用
        LoginCountdownView.this.setText("重新发送(" + startTime + "s)");
        LoginCountdownView.this.setTextColor(Color.parseColor("#ffbfbfbf"));
    }

    /** 可点击时的显示
     * @param text 文本
     */
    public void setViewEnable( String text) {
        isTimeDowning=false;
        LoginCountdownView.this.setEnabled(true);//可用
        LoginCountdownView.this.setText(text);
        LoginCountdownView.this.setTextColor(Color.BLACK);
    }

    public void pauseCountdownTime(){
        setViewEnable("获取验证码");
        mHandler.removeCallbacks(mRunnable);
    }
    public void stopCountdownTime(){
        ((BaseApp) context.getApplicationContext()).setCountdownTime(0);
        setViewEnable("获取验证码");
        mHandler.removeCallbacks(mRunnable);
    }
    public void removeRunable(){
        mHandler.removeCallbacks(mRunnable);
    }



    public void init(EditText etPhone, final EditText etCode) {
        int time = ((BaseApp) context.getApplicationContext()).getCountdownTime();
        if (time>0&&time< LoginCountdownView.MAX_TIME){
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

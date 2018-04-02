package com.mabang.android.activity;

import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.utils.MyInputFilter;
import com.mabang.android.utils.ShopCarUtil;

import static walke.base.tool.Util.IsMobile;
import static walke.base.tool.Util.IsPhone;

/**
 * Created by walke on 2018/2/11.
 * email:1032458982@qq.com
 */

public class TestActivity extends AppActivity {

    private ImageView iv1,iv2;
    private EditText etTest;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        iv1 = ((ImageView) findViewById(R.id.test_iv1));
        iv2 = ((ImageView) findViewById(R.id.test_iv2));
        etTest = ((EditText) findViewById(R.id.test_etTest));
//        etTest.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});//长度：11
//        etTest.setInputType(InputType.TYPE_CLASS_NUMBER);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);


        String digists = "0123456789-";
        etTest.setKeyListener(DigitsKeyListener.getInstance(digists));
        InputFilter[] inputFilters = {new MyInputFilter()};
        etTest.setFilters(inputFilters);



    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        etTest.setText("020-32495715");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.test_iv1:
                ShopCarUtil.setAnim(this,v,700);
                break;
            case R.id.test_iv2:
                ShopCarUtil.setAnim2(this,v,1500);
                break;
        }

    }

    public void check(View v){
        String tel = etTest.getText().toString().trim();
        if(!(IsPhone(tel) || IsMobile(tel)))
            toast("不合法");
        else
            toast("合法");

//        String phone = etTest.getText().toString().trim();
//        if (!Util.IsMobile(phone)&&!Util.IsPhone(phone)){
//            ToastUtil.showToast(this,"联系方式格式错误");
//        }else if (Util.IsMobile(phone)){
//            toast("手机号码："+phone);
//        }else if (Util.IsPhone(phone)){
//            toast("固话："+phone);
//        }
    }
}

package com.grandsea.ticketvendingapplication.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.constant.IntentKey;
import com.grandsea.ticketvendingapplication.constant.UrlConstant;
import com.grandsea.ticketvendingapplication.model.common.GsonObject;
import com.grandsea.ticketvendingapplication.util.BasicUtil;
import com.grandsea.ticketvendingapplication.util.ToastUtil;
import com.grandsea.ticketvendingapplication.view.ReturnButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.grandsea.ticketvendingapplication.adapter.GsonAdapter.gson;
import static com.grandsea.ticketvendingapplication.util.ToastUtil.context;

//设置
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ReturnButton btSure, btBack;
    private EditText etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        btSure = ((ReturnButton) findViewById(R.id.as_btSure));
        btBack = ((ReturnButton) findViewById(R.id.as_btBack));
        etUsername = ((EditText) findViewById(R.id.as_etUsername));
        etPassword = ((EditText) findViewById(R.id.as_etPassword));

        btSure.setOnClickListener(this);
        btBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v== btBack){
            finish();
        }else if (v==btSure){
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username)){
                toast("用户名不能为空！");
                return;
            }
            if (TextUtils.isEmpty(password)){
                toast("密码不能为空！");
                return;
            }

            loginRequest(username, password);

        }
    }

    private void loginRequest(String username, String password) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("account", username);
        paramMap.put("pwd", password);//
        paramMap.put("snCode",getApp().getSnCode());//++
        String requestContent = new Gson().toJson(BasicUtil.buildPostData( this,paramMap));
        OkHttpUtils
                .postString()
                .url(UrlConstant.AIRPORT_AUTHORIZE)
//                .url("http://192.168.2.23:9091/user-account/ticker_vendor/login")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(requestContent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        toast("网络异常，请重新刷新");
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(String s, final int i) {
                        GsonObject dataGson = new GsonObject(gson.fromJson(s, JsonObject.class)); //对数据转换成json后进行对象封装
                        //{"result":{"func":"ticker_vendor_login","status":1},"uid":"SMZF_zzspj_00001","status":1}
                        int status = dataGson.getInt("status", 0);
                        if (status==1){
                            //授权成功后跳转到选择出发站点界面
                            String uid = dataGson.getString("uid");
                            getApp().setDefaultUid(uid);
                            Intent intent = new Intent(SettingActivity.this, ChangeDepartStationActivity.class);
                            intent.putExtra(IntentKey.CHANGE_DEPART_STATION,"请选择出发站点：");
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showToast(context,"授权失败，请重试");//刷新
                        }

                    }
                });
    }
}

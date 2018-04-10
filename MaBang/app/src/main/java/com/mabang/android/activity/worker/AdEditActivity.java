package com.mabang.android.activity.worker;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.config.Constants;
import com.mabang.android.config.IntentCode;
import com.mabang.android.entity.vo.BillboardInfo;

import walke.base.widget.DialogUtil;

/**
 * Created by walke on 2018/2/2.
 * email:1032458982@qq.com
 */

public class AdEditActivity extends AppActivity {

    private Button btSave;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_ad_edit;
    }

    @Override
    protected void initView() {
        initTitleViewById(R.id.aae_TitleView);
        btSave = ((Button) findViewById(R.id.aae_btSave));


        btSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.aae_btSave:// 保存

                DialogUtil.tipsTwoButton(this, "取消", "确定", "保存当前修改？", new DialogUtil.TwoButtonClickListener() {
                    @Override
                    public void leftOnClick() {
                    }

                    @Override
                    public void rightOnClick() {
                        toast("模拟保存成功");
                        // TODO: 2018/4/10 a.做一个提交请求，b.成功后返回工人首页，并且带着参数
                        BillboardInfo billboardInfo = new BillboardInfo();
                        Intent data = new Intent(AdEditActivity.this, WorkerHomeActivity.class);
                        data.putExtra(Constants.BILLBOARD_INFO,billboardInfo);
                        setResult(IntentCode.WORKER_TO_EDIT_AD, data);
                        finish();
                    }
                });


                break;

        }
    }





}

package com.grandsea.ticketvendingapplication.activity.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.grandsea.ticketvendingapplication.R;

import butterknife.ButterKnife;

/**
 * Created by Grandsea09 on 2017/9/30.
 * 身份信息：
 */

public class IdInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_info);
        ButterKnife.bind(this);


    }

}

package com.grandsea.ticketvendingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.activity.device.IdInfoActivity;
import com.grandsea.ticketvendingapplication.activity.device.QRCodeActivity;
import com.grandsea.ticketvendingapplication.activity.third_party.IdCardActivity;
import com.grandsea.ticketvendingapplication.activity.third_party.printer.PrinterActivityTest;
import com.grandsea.ticketvendingapplication.base.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void idInfo(View view) {
        startActivity(new Intent(this, IdCardActivity.class));
    }
    public void idInfo2(View view) {
        startActivity(new Intent(this, IdInfoActivity.class));
    }
    public void qrCode(View view) {
        startActivity(new Intent(this, QRCodeActivity.class));
    }
    public void print(View view) {
        startActivity(new Intent(this, PrinterActivityTest.class));
    }
    public void print2(View view) {
        startActivity(new Intent(this, PrintfActivity.class));
    }
}

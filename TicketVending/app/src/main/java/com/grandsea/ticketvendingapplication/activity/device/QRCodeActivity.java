package com.grandsea.ticketvendingapplication.activity.device;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.other.BeepManager;
import com.grandsea.ticketvendingapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Grandsea09 on 2017/9/30.
 */

public class QRCodeActivity extends AppCompatActivity {
    @BindView(R.id.qrcode_tvResult)
    TextView qrcodeTvResult;
    public static final int REQUEST_CODE=0x124;
    private BeepManager mBeepManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        mBeepManager = new BeepManager(this, R.raw.beep);

    }

    public void start(View view) {
        Toast.makeText(QRCodeActivity.this, "click", Toast.LENGTH_LONG).show();
        if (checkPackage("com.telpo.tps550.api")) {
            Intent intent = new Intent();
            intent.setClassName("com.telpo.tps550.api", "com.telpo.tps550.api.barcode.Capture");
            try {
                startActivityForResult(intent, REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(QRCodeActivity.this, getResources().getString(R.string.identify_fail), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(QRCodeActivity.this, getResources().getString(R.string.identify_fail), Toast.LENGTH_LONG).show();
        }
    }


    private boolean checkPackage(String packageName) {
        PackageManager manager = this.getPackageManager();
        Intent intent = new Intent().setPackage(packageName);
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
        if (infos == null || infos.size() < 1) {
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x124) {
            if (resultCode == 0) {
                if (data != null) {
                    mBeepManager.playBeepSoundAndVibrate();
                    String qrcode = data.getStringExtra("qrCode");
                    Toast.makeText(QRCodeActivity.this, "Scan result:" + qrcode, Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                Toast.makeText(QRCodeActivity.this, "Scan Failed", Toast.LENGTH_LONG).show();
            }
        }

    }


}

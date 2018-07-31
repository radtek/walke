package com.aipiao.bkpkold;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aipiao.bkpkold.base.BaseActivity;
import com.aipiao.bkpkold.bean.Root;
import com.aipiao.bkpkold.fragment.FindFragment;
import com.aipiao.bkpkold.fragment.MeFragment;
import com.aipiao.bkpkold.fragment.OpenFragment;
import com.aipiao.bkpkold.fragment.newshome.MyNewHomeFragment;


public class HomeActivity extends BaseActivity {
    private FrameLayout mainFrameLayout;
    private RadioGroup radioGroup1;
    private RadioButton radio1, radio2, radio3, radio4;
    private Root appPush;

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected void initData() {
        initPermission(PERMISSIONS, 123);
//        BmobQuery<UpdateApp> updateAppBmobQuery = new BmobQuery<UpdateApp>();
//        updateAppBmobQuery.setLimit(1);
//        updateAppBmobQuery.findObjects(new FindListener<UpdateApp>() {
//            @Override
//            public void done(List<UpdateApp> list, BmobException e) {
//                if (list != null && list.size() > 0) {
//                    UpdateApp updateApp = list.get(0);
//                    if (TextUtils.isEmpty(updateApp.getUrl())) {
//                        return;
//                    }
//
//                }
//            }
//        });


        radio1.setChecked(true);
        radio1.setTextColor(getResources().getColor(R.color.red));
        radio2.setTextColor(getResources().getColor(R.color.black3));
        radio3.setTextColor(getResources().getColor(R.color.black3));
        radio4.setTextColor(getResources().getColor(R.color.black3));


        radio1.setChecked(true);
        radio1.setTextColor(getResources().getColor(R.color.red));
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.mainFrameLayout, MyNewHomeFragment.newInstance()).commit();
//

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        fm.beginTransaction().replace(R.id.mainFrameLayout, MyNewHomeFragment.newInstance()).commit();
                        radio1.setTextColor(getResources().getColor(R.color.red));
                        radio2.setTextColor(getResources().getColor(R.color.black3));
                        radio3.setTextColor(getResources().getColor(R.color.black3));
                        radio4.setTextColor(getResources().getColor(R.color.black3));
                        break;
                    case R.id.radio2:
                        fm.beginTransaction().replace(R.id.mainFrameLayout, OpenFragment.newInstance()).commit();
                        radio2.setTextColor(getResources().getColor(R.color.red));
                        radio1.setTextColor(getResources().getColor(R.color.black3));
                        radio3.setTextColor(getResources().getColor(R.color.black3));
                        radio4.setTextColor(getResources().getColor(R.color.black3));
                        break;
                    case R.id.radio3:
                        fm.beginTransaction().replace(R.id.mainFrameLayout, FindFragment.newInstance()).commit();
                        radio3.setTextColor(getResources().getColor(R.color.red));
                        radio2.setTextColor(getResources().getColor(R.color.black3));
                        radio1.setTextColor(getResources().getColor(R.color.black3));
                        radio4.setTextColor(getResources().getColor(R.color.black3));
                        break;
                    case R.id.radio4:

                        fm.beginTransaction().replace(R.id.mainFrameLayout, MeFragment.newInstance()).commit();
                        radio4.setTextColor(getResources().getColor(R.color.red));
                        radio1.setTextColor(getResources().getColor(R.color.black3));
                        radio2.setTextColor(getResources().getColor(R.color.black3));
                        radio3.setTextColor(getResources().getColor(R.color.black3));
                        break;

                }

            }
        });
    }

    @Override
    protected void initView() {
        mainFrameLayout = (FrameLayout) findViewById(R.id.mainFrameLayout);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
    }

    @Override
    protected int getLayout() {
        return R.layout.home_activity;
    }
}

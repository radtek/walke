package com.mabang.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.mabang.android.R;
import com.mabang.android.activity.user.AdPreselectActivity;
import com.mabang.android.activity.user.UserLoginActivity;
import com.mabang.android.activity.worker.PictureUploadingActivity;
import com.mabang.android.activity.worker.WorkerHomeActivity;
import com.mabang.android.mapdemo.BaseMapDemo;
import com.mabang.android.mapdemo.LocationDemo;
import com.mabang.android.mapdemo.OverlayDemo;
import com.mabang.android.utils.ShopCarUtil;

import java.util.ArrayList;
import java.util.List;

import walke.base.BaseActivity;
import walke.base.tool.BitmapUtil;

public class LaunchActivity extends BaseActivity {

    private ListView mListView;
    private List<String> mList=new ArrayList<>();
    private List<Activity> mActivities;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mListView = ((ListView) findViewById(R.id.launch_lv));
        mImageView = ((ImageView) findViewById(R.id.imageView2));
        mList.add("用户登录界面");
        mList.add("地图基础功能");
        mList.add("地图定位功能");
        mList.add("地图覆盖物功能");
        mList.add("WorkerHomeActivity");
        mList.add("AdPreselectActivity");
        mList.add("PictureUploadingActivity");
        mList.add("TestActivity");
        initActivitys();
//        SimpleAdapter adapter=new SimpleAdapter(this,mList,android.R.layout.simple_list_item_1,android.R.id.);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(view.getContext(), mActivities.get(position).getClass()));
            }
        });

        Bitmap viewBitmap = BitmapUtil.getViewBitmap(this, R.layout.view_to_bitmap);
        mImageView.setImageBitmap(viewBitmap);

    }

    private void initActivitys() {
        mActivities = new ArrayList<>();
        mActivities.add(new UserLoginActivity());
        mActivities.add(new BaseMapDemo());
        mActivities.add(new LocationDemo());
        mActivities.add(new OverlayDemo());
        mActivities.add(new WorkerHomeActivity());
        mActivities.add(new AdPreselectActivity());
        mActivities.add(new PictureUploadingActivity());
        mActivities.add(new TestActivity());

    }

//    public static String saveDir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/mb/1/2/3/img";
    public static String saveDir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/mb/img";

    public void test(View view) {
//        boolean b = ImgFileUtils.initFile(new File(saveDir, "test.txt"));
//        logI(" ImgFileUtils.initFile  b = "+b);
        ShopCarUtil.setAnim(this,mImageView,700);
    }
}

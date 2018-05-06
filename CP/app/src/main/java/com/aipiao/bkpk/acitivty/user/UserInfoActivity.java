package com.aipiao.bkpk.acitivty.user;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.pedaily.yc.ycdialoglib.selectDialog.CustomSelectDialog;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.bmob.User;
import com.aipiao.bkpk.utils.SharepreUtil;
import com.aipiao.bkpk.views.TopView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by caihui on 2018/3/26.
 */

public class UserInfoActivity extends BaseActivity {

    private PullToRefreshScrollView mePullToRefreshScrollView;
    private TopView userinfoTopView;
    private LinearLayout cgtLlMeUserInfo;
    private CircleImageView cgtIvMeUserPic;
    private TextView cgtTvMeUserName;
    private TextView cgtTvMeWeixinNum;
    private LinearLayout cgtLlMePhoto;
    private LinearLayout cgtLlMeCollect;
    final List<String> names = new ArrayList<>();
    private LinearLayout sexLinearLayout;
    private User user;
    private TextView phoneTextView, sexTextView;


    @Override
    protected void onResume() {
        super.onResume();
        String userString = SharepreUtil.getString(UserInfoActivity.this, "user");
        if (!TextUtils.isEmpty(userString)) {
            user = gson.fromJson(userString, User.class);
            if (user != null) {
                if (!TextUtils.isEmpty(user.getNickname())) {
                    cgtTvMeUserName.setText("昵称: " + user.getNickname());
                }
                if (!TextUtils.isEmpty(user.getMoon())) {
                    cgtTvMeWeixinNum.setText("心情: " + user.getMoon());
                }
                if (!TextUtils.isEmpty(user.getSex())) {
                    sexTextView.setText(user.getSex() + "");
                }
                phoneTextView.setText(user.getPhone() + "");
            }

        }
    }

    @Override
    protected void initData() {
        userinfoTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userinfoTopView.getTopTitleTextView().setText("个人信息");
        names.add("拍照");
        names.add("相册");
        mePullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                BmobQuery<User> query = new BmobQuery<User>();
                query.getObject(user.getObjectId(), new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            mePullToRefreshScrollView.onRefreshComplete();
                            SharepreUtil.putString(UserInfoActivity.this, "objectId", user.getObjectId());
                            SharepreUtil.putString(UserInfoActivity.this, "user", gson.toJson(user));
                            if (user != null) {
                                if (!TextUtils.isEmpty(user.getNickname())) {
                                    cgtTvMeUserName.setText("昵称: " + user.getNickname());
                                }
                                if (!TextUtils.isEmpty(user.getMoon())) {
                                    cgtTvMeWeixinNum.setText("心情: " + user.getMoon());
                                }
                                if (!TextUtils.isEmpty(user.getSex())) {
                                    sexTextView.setText(user.getSex() + "");
                                }
                                phoneTextView.setText(user.getPhone() + "");
                            }
                        }
                    }
                });
            }
        });


    }

    @Override
    protected void initView() {
        mePullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.mePullToRefreshScrollView);
        sexTextView = (TextView) findViewById(R.id.sexTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        userinfoTopView = (TopView) findViewById(R.id.userinfoTopView);
        cgtLlMeUserInfo = (LinearLayout) findViewById(R.id.cgt_ll_me_userInfo);
        cgtIvMeUserPic = (CircleImageView) findViewById(R.id.cgt_iv_me_userPic);
        cgtTvMeUserName = (TextView) findViewById(R.id.cgt_tv_me_userName);
        cgtTvMeWeixinNum = (TextView) findViewById(R.id.cgt_tv_me_weixinNum);
//        cgtLlMePhoto = (LinearLayout) findViewById(R.id.cgt_ll_me_photo);
        cgtLlMeCollect = (LinearLayout) findViewById(R.id.cgt_ll_me_collect);
        sexLinearLayout = (LinearLayout) findViewById(R.id.sexLinearLayout);

        cgtTvMeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UpdateUserInfoActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("name", "修改昵称");
                startActivity(intent);
            }
        });
        cgtTvMeWeixinNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UpdateUserInfoActivity.class);
                intent.putExtra("type", "3");
                intent.putExtra("name", "修改心情");
                startActivity(intent);
            }
        });

        sexLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UpdateUserInfoActivity.class);
                intent.putExtra("type", "1");
                intent.putExtra("name", "修改性别");
                startActivity(intent);
            }
        });


        cgtLlMeCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("手机号码暂时不能修改哦");
            }
        });

        cgtIvMeUserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//                } else {
//                    showCustomSelectDialog(new CustomSelectDialog.SelectDialogListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            switch (position) {
//                                case 0:
//                                    takePicCamcra();
//                                    break;
//                                case 1:
//                                    takePicKu();
//                                    break;
//                            }
//                        }
//                    }, names);
//                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCustomSelectDialog(new CustomSelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                takePicCamcra();
                                break;
                            case 1:
                                takePicKu();
                                break;
                        }
                    }
                }, names);
            } else {
                toast("请在应用管理中打开“相机”访问权限！");
                finish();
            }
        }
    }

    private void takePicKu() {
        Intent intent2 = new Intent(Intent.ACTION_PICK, null);
        intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent2, 100);
    }

    private Bitmap image;

    private void takePicCamcra() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    String imagePath = "";
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    if (cursor != null) {
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                            imagePath = cursor.getString(column_index);
//                            Toast.makeText(InformationActivity.this, imagePath + "", 1).show();
                        }
                    }
                    InputStream inputStream = cr.openInputStream(uri);
                    String imageBase = decodeBase64String(inputStream);
                    //请求
                    File file = new File(imagePath);
                    uploadImagee(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == 101) {
            Uri uri = data.getData();
            if (uri != null) {
                image = BitmapFactory.decodeFile(uri.getPath());
            }
            if (image == null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    image = (Bitmap) bundle.get("data");
                } else {
                    Toast.makeText(UserInfoActivity.this, "拍照失败", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            FileOutputStream fileOutputStream = null;
            try {
                // 获取 SD 卡根目录
                String saveDir = getSDPath() + "/meitian_photos";
                // 新建目录
                File dir = new File(saveDir);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                // 生成文件名
                SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
                String filename = "MT" + (t.format(new Date())) + ".jpg";
                // 新建文件
                File file = new File(saveDir, filename);
                // 打开文件输出流
                fileOutputStream = new FileOutputStream(file);
                // 生成图片文件
                image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                // 相片的完整路径
                String picPath = file.getPath();
                File files = new File(picPath);
                uploadImagee(files);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void uploadImagee(File files) {
        final BmobFile bmobFile = new BmobFile(files);
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("上传文件成功:" + bmobFile.getFileUrl());
                } else {
                    toast("上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                Log.d("---------", value + "");
            }
        });
    }

    private String decodeBase64String(InputStream inputStream) {
        // 1.从drawable-hdpi得到一个图片
        Bitmap sourceBitmap = BitmapFactory.decodeStream(inputStream);
        // 2.转换成byte[]
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] sourceBitmapByteArr = out.toByteArray();
        // 3.base64-->string
        String bitmapString = Base64.encodeToString(sourceBitmapByteArr, Base64.DEFAULT);
//       L.log("选择选择 base64：" + bitmapString);
        return bitmapString;
    }

    public String saveImage(Bitmap photo, String name) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(
                    new FileOutputStream(Environment.getExternalStorageDirectory() + name, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bos.toString();
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_userinfo;
    }
}

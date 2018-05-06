package com.aipiao.bkpk.acitivty.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aipiao.bkpk.views.ClearEditText;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.base.BaseActivity;
import com.aipiao.bkpk.bean.bmob.User;
import com.aipiao.bkpk.utils.SharepreUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by caihui on 2018/3/26.
 */

public class UpdateUserInfoActivity extends BaseActivity {

    private View layoutTitleBar;
    private TextView buttonBack;
    private TextView title;
    private Button buttonRight;
    private ClearEditText etNickname;
    private Button btSave;

    /**
     * 1 性别  2 昵称  3：心情
     */
    private String type;
    private String name;

    private User user;
    private String objectId;

    @Override
    protected void initData() {
        user = new User();
        objectId = SharepreUtil.getString(UpdateUserInfoActivity.this, "objectId");
        user.setObjectId(objectId);
        Intent intent = getIntent();
        if (intent != null) {
            name = (String) intent.getExtras().get("name");
            type = (String) intent.getExtras().get("type");
            title.setText(name + "");
        }

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateUserInfoActivity.this, KefuViewlActivity.class));
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etNickname.getText().toString();
                if (type.equals("1")) {
                    etNickname.setMaxLines(2);
                    if (TextUtils.isEmpty(data)) {
                        toast("修改的资料不能为空哦");
                        return;
                    }
                    user.setSex(data);
                } else if (type.equals("2")) {
                    etNickname.setMaxLines(6);
                    user.setNickname(data);
                } else if (type.equals("3")) {
                    etNickname.setMaxLines(30);
                    user.setMoon(data);
                }
                showLoading();
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        hideLoading();
                        if (e == null) {
                            BmobQuery<User> query = new BmobQuery<User>();
                            query.getObject(user.getObjectId(), new QueryListener<User>() {
                                @Override
                                public void done(User object, BmobException e) {
                                    if (e == null) {
                                        SharepreUtil.putString(UpdateUserInfoActivity.this, "user", gson.toJson(object));
                                        finish();
                                    }
                                }
                            });
                        } else {
                            toast("修改资料失败,请重试");
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void initView() {
        layoutTitleBar = (View) findViewById(R.id.layout_title_bar);
        buttonBack = (TextView) findViewById(R.id.button_back);
        title = (TextView) findViewById(R.id.title);
        buttonRight = (Button) findViewById(R.id.button_right);
        etNickname = (ClearEditText) findViewById(R.id.et_nickname);
        btSave = (Button) findViewById(R.id.bt_save);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_updateinfo;
    }
}

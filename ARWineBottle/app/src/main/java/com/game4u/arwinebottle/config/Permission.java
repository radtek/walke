package com.game4u.arwinebottle.config;

import android.Manifest;

/**
 * Created by walke.Z on 2017/11/2.
 */

public class Permission {

    public static final int REQUEST_CODE=30;
    /**
     * 配置app需要的所有权限集
     */
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
    };



}

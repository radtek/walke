package com.mabang.android.config;

import android.Manifest;

/**
 * Created by walke on 2018/2/22.
 * email:1032458982@qq.com
 */

public class MBPermissions {

    public static final int PERMISSION_CODE = 50;//系统授权摄像头管理页面时的结果参数
    /**
     * 配置app需要的权限集
     */
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

            Manifest.permission.LOCATION_HARDWARE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.INSTALL_LOCATION_PROVIDER,
            Manifest.permission.CONTROL_LOCATION_UPDATES,

            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.CAMERA,

            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
    };
    /**
     * 配置app需要的权限集
     */
    public static final String[] PERMISSIONS2 = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
}

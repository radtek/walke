package com.mabang.android.config;

import android.os.Environment;

/**
 * Created by walke on 2018/2/9.
 * email:1032458982@qq.com
 * 常量
 */

public class Constants {

    public static String color_textGray ="#ffbfbfbf";

    public static final String MEMBER_ACCOUNT = "member_account";
    public static final String MEMBER_TOKEN = "member_token";
    public static final String MEMBER_API_TYPE = "member_api_type";

    public static final String WORKER_ACCOUNT = "worker_account";
    public static final String WORKER_TOKEN = "worker_token";

    public static final String PICTURE_UPLOAD_TYPE = "picture_upload_type";
    public static final String ADS_PICTURE_UPLOAD = "广告图片上传";
    public static final String DOWN_PICTURE_UPLOAD = "验收图片上传";

    public static final String ERROR_NO_NET = "网络不给力";//没有网络\n请检查手机的网络状态
    public static final String ERROR_SEVER_NET = "与服务器连接异常\n请检查您手机的网络状态";
    public static final String ERROR_SERVICE_BACK = "服务器返回异常";
    public static final String ERROR_SERVICE = "服务器异常";
    public static final String ERROR_SERVICE_BACK_DATA = "返回数据异常";
    public static final String ERROR_SERVICE_BACK_DATA_CAST = "返回数据类型转换异常";

    public static final String WEBVIEW_COOKIE = "cookie";
    public static final String IS_FIRST_VERSION_ERROR = "isFirstVersionError";//是否是第一次进来检测

    public static final String ADDRESS_INFO = "AddressInfo";
    public static final String APP_LOCATION = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MaBang";
    public static String BILLBOARD_INFO ="billboardInfo";
    public static String adPreselect_list_info="adPreselect_list_info";

    public static String FILE_PROVIDER_PATHS="MaBang";//与MaBang中的配置一致
    public static String FIRST_OPEN="first_open";
    public static String LAST_USER_LOGIN_NAME ="last_user_login_name";
    public static String LAST_WORKER_LOGIN_NAME="last_worker_login_name";
    public static String LAST_LOGIN_TYPE ="LAST_LOGIN_TYPE";
    public static String LOGIN_USER ="login_user";
    public static String LOGIN_WORKER ="login_worker";
    public static String LOGIN_WORKER_PASSWORD="login_worker_password";
    public static String MARKER_INFO="marker_info";
}

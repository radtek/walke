package com.mabang.android.config;

/**
 * Created by Xiongrui on 2016/12/14.
 */
public class Api {
    /**
     * api版本
     */
    public final static String VERSION = "1.0";
    /**
     * 安卓客户端KEY
     */
    public final static String KEY = "mabang_for_android";
    /**
     * 客户端对应密钥
     */
    public final static String SECRET_KEY = "A2FE3F3FA7A294D3C5ECB819D0CF0D82";
    /**
     * 加盐码-客户端
     */
    public final static String CLIENT_SALT = "MABANG_APP_API";
    /**
     * 客户端接口参数-key
     */
    public final static String PARAMS_KEY = "key";
    /**
     * 客户端接口参数-message
     */
    public final static String PARAM_MESSAGE = "message";
    /**
     * 成功
     */
    public final static int OK = 1;
    /**
     * 失败
     */
    public final static int ERROR = 0;

    /**
     * 版本异常
     */
    public final static int VERSION_ERROR = 3333;

    /**
     * 未登录
     */
    public final static int NO_LOGIN = 5555;

    // 接口指令 ============================ begin ============================
    //user
    /**
     * 登录
     */
    public final static String User_Login = "1000";
    /**
     * 退出
     */
    public final static String User_Exit = "1001";
    /**
     * 发送验证码
     */
    public final static String User_validateCode = "1002";
    /**
     * 联动地址信息
     */
    public final static String User_address = "1003";
    /**
     * 查询信息
     */
    public final static String User_search = "1004";
    /**
     * 检查预约信息
     */
    public final static String User_checkAdvance = "1005";
    /**
     * 提交预约信息
     */
    public final static String User_advanceOrder = "1006";
    /**
     * 广告位信息
     */
    public final static String User_billboard = "1007";
    /**
     * 首页，用于鉴别是否还需要登录
     */
    public final static String User_checkLogin = "1008";

    // 接口指令 ============================ worker ============================

    /**
     * 登录
     */
    public final static String Worker_Login = "2000";
    /**
     * 退出
     */
    public final static String Worker_Exit = "2001";
    /**
     * 查询广告位信息，直接管理码查询
     */
    public final static String Worker_billboard = "2002";
    /**
     * 更新广告位位置坐标
     */
    public final static String Worker_updateCoordinates = "2003";
    /**
     * 上传广告位图片
     */
    public final static String Worker_uploadImage = "2004";
    /**
     * 首页，用于鉴别是否还需要登录
     */
    public final static String Worker_checkLogin = "2005";

    /**
     * 查询信息
     */
    public final static String Worker_search = "2006";

}

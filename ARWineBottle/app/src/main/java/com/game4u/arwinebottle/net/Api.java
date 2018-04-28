package com.game4u.arwinebottle.net;

/**
 * Created by walke.Z on 2017/11/7.
 */

public class Api {
    //http://39.108.229.146:9888/wineAR-client/sms

//    public static final String BASE_URL = "http://39.108.229.146:9888/wineAR-client/";
    public static final String BASE_URL = "http://sp.dandelion.group/wineAR-client/";

    public static final int SUCCESS=1;//(1：成功，0：失败)
    public static final int FAILURE=0;

    /**
     * 手机验证码
     */
    public static final String SMS = "sms";

    /**
     * 注册
     */
    public static final String REGISTER = "register";

    /**
     * 登录
     */
    public static final String LOGIN = "login";

    /**
     * 忘记密码
     */
    public static final String RESET_PASSWORD = "updatePassword";

    /**
     * 图形验证吗
     */
    public static final String IMAGE_CODE = "validate_code";

    /**
     * 通信key APP端必须，由接口提供方提供 xxxxxxxx
     */
    public static final String KEY = "delion#wine$ar*";
    /**
     * 私钥由接口提供方提供: yyyyyyyy
     */
    public static final String PRIVATE_KEY = "3^vxRvgo1MZ!uy0G";
}

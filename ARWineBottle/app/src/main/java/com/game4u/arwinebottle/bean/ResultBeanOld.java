package com.game4u.arwinebottle.bean;

/**
 * Created by walke.Z on 2017/11/11.
 */

public class ResultBeanOld {
    /**
     * //服务器处理结果  仅当status=1时客户端才解析数据。
     * status=1:处理成功；status=-1:服务器处理出错；status=-2:业务处理异常；
     */
    private int status;

    /**
     *  调用方法名
     */
    private String func;

    /**
     * 服务器当前时间 用于做时间校对
     */
    private String servertime;
    /**
     * 错误码
     */
    private int errorcode;
    /**
     *错误信息 例：msg
     */
    private String  msg;



}

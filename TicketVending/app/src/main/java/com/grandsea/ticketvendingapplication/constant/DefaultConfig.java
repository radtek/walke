package com.grandsea.ticketvendingapplication.constant;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DefaultConfig {
    public static final int DEPART_CITY_ID = 1; //出发城市 --> 珠海测试1  正式也为1
    //出发站点 --> 珠海机场 -->大站点id --》根据大站点id获取不同车队下的班次
    public static final int ZHU_HAI_JI_CHANG_ID = 73; //测服:73   --  正服：3
//    public static final int GET_ON_ID = 3; //正服  //值=-1时，表示获取上车点   值=0时，表示获取所有车次   值=1时，表示获取对应下车点
    public static final int GET_ON_ID = 73; //测服 //值=-1时，表示获取上车点   值=0时，表示获取所有车次   值=具体值时，表示获取对应下车点
    public static final String GET_ON_STATION_NAME = "珠海机场"; //测服 //值=-1时，表示获取上车点   值=0时，表示获取所有车次   值=1时，表示获取对应下车点

    //乘车最大的人数限制
    public static final int MAX_PASSENGER_COUNT = 5;//

    public static final String DEPART_CITY_NAME = "珠海";//测试

//    public static final String TEMINAL_ID = "17058000800061";//机器SN

    //本地测试用   58ff4eddda0e83185baa7eb7cc4fc5d1（从康）   587f33c9e4b06012ca6752cce8fd00e0 ()
    public static final String DEFAULT_UID="SMZF_zzsp_00001";//TODO 自助售票机的id
    //SMZF_zzsp_00001、SMZF_zzsp_00002、SMZF_zzsp_00003、SMZF_zzsp_00004、SMZF_zzsp_00005

}

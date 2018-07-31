package com.grandsea.ticketvendingapplication.constant;

/**
 * Created by Administrator on 2017/9/6.
 */
public class UrlConstant {
    //private static final String BASE_UEL = "https://www.51sfbus.com/";//正式
    private static final String BASE_UEL = "https://www.grandsea.com.cn/test/";
//    private static final String BASE_UEL = "http://zhongda.imwork.net/";

    // ************机场快线的****************************

    //机器登录授权
//    public static final String AIRPORT_AUTHORIZE = BASE_UEL + "airport_cities";
//    public static final String AIRPORT_AUTHORIZE = "http://www.grandsea.com.cn/cwxt-base-cms/ticket_vending_app/permit";
    public static final String AIRPORT_AUTHORIZE = "http://zhongda.imwork.net/user-account/ticker_vendor/login";

    //抓取机场快线城市的数据
    public static final String AIRPORT_CITIES = BASE_UEL + "sfc-search/airport_cities";

    //获取当前城市机场
    public static final String AIRPORT = BASE_UEL + "sfc-search/airport";

    //获取机场列表
    public static final String AIRPORT_LIST = BASE_UEL + "sfc-search/airport_list";

    //***********顺风庐的*****************************

    //上/下车站点列表数据
    public static final String STATION_LIST = BASE_UEL + "sfc-search/station_list";

    //获取班次列表数据
    public static final String SHIFT_LIST = BASE_UEL + "sfc-search/shift_list";

    //核对班次信息
    public static final String CHK_LIST = BASE_UEL + "sfc-search/chk_shift";


    //*********订单相关的*****************************

    //提交订单
    public static final String ORDER_TICKET = BASE_UEL + "sfc-order/order_ticket";

    //取消订单
    public static final String CANCEL_ORDER_TICKET = BASE_UEL + "sfc-order/order/status";

    //获取电子订单
    public static final String ELECTR_ORDER = BASE_UEL + "sfc-order/order/elec_tickets";
    //微信支付 http://zhongda.imwork.net/sfc-order/scan_code
    public static final String PAY_WECHAT = BASE_UEL + "sfc-order/scan_code";

}
/*public class UrlConstant {
    //测服
    //省市区：http://zhongda.imwork.net/ticket-service/area/list
    //站点查询：http://zhongda.imwork.net/ticket-service/station/list
    //...
    //正服
    //省市区：https://www.51sfbus.com/ticket-service/area/list
    //站点查询：https://www.51sfbus.com/ticket-service/station/list

    //private static final String BASE_UEL = "https://www.51sfbus.com/ticket-service/";//正式
    private static final String BASE_UEL = "http://zhongda.imwork.net/ticket-service/";//测服

    // ************机场快线的****************************
    //机器登录授权
    public static final String AIRPORT_AUTHORIZE = "http://www.grandsea.com.cn/cwxt-base-cms/ticket_vending_app/permit";

    //抓取机场快线城市的数据  sfc-search/airport_cities
    public static final String AIRPORT_CITIES = BASE_UEL + "area/list";//  省、市、区查询

    //获取当前城市机场
    public static final String AIRPORT = BASE_UEL + "sfc-search/airport";

    //获取机场列表
    public static final String AIRPORT_LIST = BASE_UEL + "sfc-search/airport_list";

    /*//************顺风庐的*****************************
 //上/下车站点列表数据  sfc-search/station_list
 public static final String STATION_LIST = BASE_UEL + "station/list";// 站点查询

 //获取班次列表数据  sfc-search/shift_list
 public static final String SHIFT_LIST = BASE_UEL + "realtime-shift/list";//实时班次查询

 //核对班次信息
 public static final String CHK_LIST = BASE_UEL + "sfc-search/chk_shift";

 /*//**********订单相关的*****************************
 //提交订单  sfc-order/order_ticket
 public static final String ORDER_TICKET = BASE_UEL + "/tickets/lock";//锁票

 //取消订单 sfc-order/order/status
 public static final String CANCEL_ORDER_TICKET = BASE_UEL + "tickets/unlock";//取消锁票

 //获取电子订单 sfc-order/order/elec_tickets
 public static final String ELECTR_ORDER = BASE_UEL + "";
 //微信支付 sfc-order/scan_code
 public static final String PAY_WECHAT = BASE_UEL + "";

 }*/





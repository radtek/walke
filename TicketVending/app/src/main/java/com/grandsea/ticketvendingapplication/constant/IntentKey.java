package com.grandsea.ticketvendingapplication.constant;

/**
 * Created by Administrator on 2017/9/18.
 */

public class IntentKey {
    //DestCityActivity.class 下面的
    public static final String INTENT_DEPART_CITY_ID = "depart_city_id";
    public static final String INTENT_DEST_CITY  = "dest_city";
    public static final String INTENT_DEST_CITY_BUNDLE = "dest_city_name";

    //站点--> 班次相关
    public static final String SHIFT_OBJ = "shift_obj";

    //班次--> 乘客
    public static final String EXTRA_SHIFT_TO_PASSENGER_OBJ = "order_info_obj";



    //乘客 --> 下单
    public static final String ORDER_INFO = "order_info";

    //
    public static final String TICKET_DATE = "ticket_date";


    public static final String ORDER_ID = "order_Id";
    public static final String PERSON_INFO = "person_info";
    public static final String TOTAL_TICKETS = "total_tickets";
    public static final String CHANGE_DEPART_STATION = "change_dpart_station";
}

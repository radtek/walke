package com.mabang.android;

/**
 * Created by walke on 2018/4/7.
 * email:1032458982@qq.com
 */

public class TestJava {

    public static void main(String[] args) {
        String address = "广东省广州市白云区永平街道东平大街";
        String address2 = "广东省广州市白云区永平街道区东平大街";
        String address3 = "广东省广州市区白云区永平街道东平大街";

        int indexOf = address.indexOf("区");
        address = address.substring(indexOf + 1, address.length());
        System.out.println("main: ----------------- address = " + address);

        int indexOf2 = address2.indexOf("区");
        address2 = address2.substring(indexOf2 + 1, address2.length());
        System.out.println( "main: ----------------- address2 = " + address2);

        int indexOf3 = address3.indexOf("区");
        address3 = address3.substring(indexOf3 + 1, address3.length());
//        Log.i("walke", "main: ----------------- address3 = " + address3);
        System.out.println("main: ----------------- address3 = " + address3);

    }
}

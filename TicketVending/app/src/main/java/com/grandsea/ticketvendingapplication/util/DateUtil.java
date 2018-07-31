package com.grandsea.ticketvendingapplication.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.joda.time.LocalDate;

public final class DateUtil {

	private DateUtil(){}
	
//	public final static Date plusDaysFromNow(int days){
//		return LocalDate.now().plusDays(days).toDate();
//	}
//
//	public final static Date plusMonthsFromNow(int months){
//		return LocalDate.now().plusMonths(months).toDate();
//	}
//
	public final static Date plusHoursFromNow(int hours){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hours);

		return cal.getTime();
	}
	
	public final static Date plusMinsFromNow(int mins){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, mins);
		
		return cal.getTime();
	}
	
	public final static String format(Date date, String pattern){
		
		return new SimpleDateFormat(pattern).format(date);
	}
	
    public static String formatYYYYMMDD(Date date){
		
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * @param date yyyy年MM月dd日
	 * @return
	 */
	public final static String formatYYYYMMDay(Date date){

		return new SimpleDateFormat("yyyy年MM月dd日").format(date);
	}
	
    public final static String formatYYYYMMDDHHMM(Date date){
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}

    public final static String formatYYYYMMDDHHMMSS(Date date){
	
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

//	public final static LocalDateTime parse(String dateStr){
//		return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//	}


	public static void main(String[] args){
		//当前时间
		Calendar cl = setCalendar(2014,01,01);
		System.out.print("当前时间:");
		printCalendar(cl);
		//前一天
		cl = setCalendar(2014,01,01);
		getBeforeDay(cl);
		System.out.print("前一天:");
		printCalendar(cl);
		//后一天
		cl = setCalendar(2014,01,01);
		getAfterDay(cl);
		System.out.print("后一天:");
		printCalendar(cl);
	}

	/**
	 * 设置时间
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Calendar setCalendar(int year,int month,int date){
		Calendar cl = Calendar.getInstance();
		cl.set(year, month-1, date);
		return cl;
	}

	/**
	 * 获取当前时间的前一天时间
	 * @param cl
	 * @return
	 */
	private static Calendar getBeforeDay(Calendar cl){
		//使用roll方法进行向前回滚
		//cl.roll(Calendar.DATE, -1);
		//使用set方法直接进行设置
		int day = cl.get(Calendar.DATE);
		cl.set(Calendar.DATE, day-1);
		return cl;
	}

	/**
	 * 获取当前时间的后一天时间
	 * @param cl
	 * @return
	 */
	private static Calendar getAfterDay(Calendar cl){
		//使用roll方法进行回滚到后一天的时间
		//cl.roll(Calendar.DATE, 1);
		//使用set方法直接设置时间值
		int day = cl.get(Calendar.DATE);
		cl.set(Calendar.DATE, day+1);
		return cl;
	}

	/**
	 * 打印时间
	 * @param cl
	 */
	public static void printCalendar(Calendar cl){
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH)+1;
		int day = cl.get(Calendar.DATE);
		System.out.println(year+"-"+month+"-"+day);
	}


}

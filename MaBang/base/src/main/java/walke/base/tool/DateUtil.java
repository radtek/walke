package walke.base.tool;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 时间类型数据转换帮助类
 *
 * @author A-Rui(xiongrui0320@yahoo.com.cn)
 * @version 2013-5-9 上午11:24:29
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
    public final static String[] week = {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public final static String[] week2 = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static SimpleDateFormat SDF_YYMMDD = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date StringToDate(String dateString, String format) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }



    public static String StringToDate_YYYY_MM_DD(Date date) {
        String dates = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dates = sdf.format(date);
        } catch (Exception e) {
            date = null;
        }

        return dates;

    }

    public static Date StringToDate(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static Date StringToDatetpy(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            date = sdf.parse(dateString);
            System.out.println(date);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static Date StringToDate_YYYY_MM_DD_HH_MM(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static Date StringToDate_YYYYMMDDHHMM(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static Date StringToDate_YY_MM_DD_HH_MM(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static Date StringToDate_YYYY_MM_DD_HH_MM_SS(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static Date stringToDate(String dateString, String format) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date StringToDateShort(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getByTimeMillis(long timeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        return dateToString(c.getTime());

    }

    public static long getTimeMillis(String dateTime) {
        if (dateTime == null || dateTime.equals("")) {
            return 0l;
        }
        Date date = DateUtil.StringToDate(dateTime);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static String getNowDateYYYYMMDD() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String getNowDate(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getNowDateYYMMDD() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(date);
    }

    public static String getNowDateYYYY_MM_DD() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getNowDateYYYY_MM_DD_HH_MM() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getNowDateYYYY_MM_DD_HH_MM_SS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getNowDateYYYYMMDDHHMMSS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static String getNowDateYYYYMMDDHHMMSSSS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return sdf.format(date);
    }

    public static Integer handleIntTime(Date date, String weekDayName) {
        for (int i = 0; i <= 2; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, -i);
            int day = cal.get(Calendar.DAY_OF_WEEK);
            String tempWeek = week[day];
            if (tempWeek.equals(weekDayName))
                return Integer.valueOf(dateToString(cal.getTime(), "yyyyMMdd"));
        }
        return null;
    }

    public static String getWeekStr2(Date date, String[] week) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return week[cal.get(Calendar.DAY_OF_WEEK)];
    }

    public static String getWeekStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return week[cal.get(Calendar.DAY_OF_WEEK)];
    }

    /**
     * 补充年份 排除1月份获取同年12月份时间&12月份获取同年1月份时间
     *
     * @param dateStr
     * @return
     */
    public static Date strToDateDafueYear(String dateStr) {
        Date matchDate = DateUtil.StringToDate(dateStr, "MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(matchDate);
        calendar.get(Calendar.DAY_OF_MONTH);
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
        // 排除1月份获取同年12月份时间&12月份获取同年1月份时间
        if (calendar.getTimeInMillis() < now.getTimeInMillis() && calendar.get(Calendar.MONTH) == 1 && now.get(Calendar.MONTH) == 12) {
            calendar.set(Calendar.YEAR, now.get(Calendar.YEAR) + 1);
        } else if (calendar.getTimeInMillis() > now.getTimeInMillis() && calendar.get(Calendar.MONTH) == 12 && now.get(Calendar.MONTH) == 1) {
            calendar.set(Calendar.YEAR, now.get(Calendar.YEAR) - 1);
        }
        return calendar.getTime();
    }

    public static Date getDateByOffsetDay(Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, offset);
        return cal.getTime();
    }

    public static boolean isDateBefore(String date2, Date date1) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return date1.before(df.parse(date2));
        } catch (ParseException e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    public static boolean isIndexDCDateBefore(Date date) {
        try {
            Calendar c = Calendar.getInstance();
            GregorianCalendar ca = new GregorianCalendar();

            if (ca.get(GregorianCalendar.AM_PM) == 1)// 判断上下午时间
                c.set(Calendar.HOUR, -2);
            else
                c.set(Calendar.HOUR, +10);

            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);

            Date afterTime = c.getTime();// 当天10时后
            return afterTime.before(date);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    public static boolean isIndexDCDateAfter(Date date) {
        try {
            Calendar c = Calendar.getInstance();
            GregorianCalendar ca = new GregorianCalendar();

            if (ca.get(GregorianCalendar.AM_PM) == 1)// 判断上下午时间
                c.set(Calendar.HOUR, +22);
            else
                c.set(Calendar.HOUR, +34);

            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);

            Date beforeTime = c.getTime();// 明天10时前
            return beforeTime.after(date);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }

    }

    public static Date GetDCMatchEndTime(Date matchTime, int aheadMilli) {
        if (matchTime == null)
            return null;
        Calendar matchCal = Calendar.getInstance();
        matchCal.setTime(matchTime);

        Calendar stopPlayTicketCal = (Calendar) matchCal.clone();
        stopPlayTicketCal.set(Calendar.HOUR_OF_DAY, 4);
        stopPlayTicketCal.set(Calendar.MINUTE, 50);
        stopPlayTicketCal.set(Calendar.SECOND, 0);
        stopPlayTicketCal.add(Calendar.MILLISECOND, -aheadMilli);

        Calendar startPlayTicketCal = (Calendar) matchCal.clone();
        startPlayTicketCal.set(Calendar.HOUR_OF_DAY, 9);
        startPlayTicketCal.set(Calendar.MINUTE, 0);
        startPlayTicketCal.set(Calendar.SECOND, 0);

        Calendar weStartPlayTicketCal = (Calendar) startPlayTicketCal.clone();
        weStartPlayTicketCal.add(Calendar.MILLISECOND, +aheadMilli);
        // if(matchCal.after(stopPlayTicketCal)&&(matchCal.before(startPlayTicketCal)||matchCal.equals(startPlayTicketCal))){
        //
        // //(4:50-aheadMilli)——(9:00) 都设置成为(4:50-aheadMilli)
        // icon_return stopPlayTicketCal.getTime();
        // }else
        // if(matchCal.after(startPlayTicketCal)&&matchCal.before(weStartPlayTicketCal)){
        // //(9:00)——(9:00+aheadMilli) 都设置 (4:50-aheadMilli)+offset
        // int offset=(int)
        // (weStartPlayTicketCal.getTimeInMillis()-matchCal.getTimeInMillis());
        // stopPlayTicketCal.addChange(Calendar.MILLISECOND, +offset);
        // icon_return stopPlayTicketCal.getTime();
        if (matchCal.after(stopPlayTicketCal) && matchCal.before(weStartPlayTicketCal)) {
            // (4:50-aheadMilli)——(9:00+aheadMilli) 都设置 (4:50-aheadMilli)
            return stopPlayTicketCal.getTime();
        } else {
            matchCal.add(Calendar.MILLISECOND, -aheadMilli);
            return matchCal.getTime();
        }
    }

    /**
     * 获取距离现在的时间
     */
    public static String getMinutes(Date times) {
        return DateUtil.getMinutes(times.getTime());
    }

    /**
     * 获取距离现在的时间
     */
    public static String getMinutes(long times) {
        long time = new Date().getTime() - times;// time 单位是 毫秒
        String res = null;
        // 转化成天数
        // 一分钟内
        if (time < 60 * 1000) {
            res = "刚刚";
        }
        // 先判断是不是小于 60 * 60 * 1000 也就是 小于1小时，那么显示 ： **分钟前
        else if (time < 60 * 60 * 1000) {
            res = (time / 1000 / 60) + "分钟前";
        }
        // 如果大于等于1小时 小于等于一天，那么显示 ： **小时前
        else if (time >= 60 * 60 * 1000 && time < 24 * 60 * 60 * 1000) {
            res = (time / 1000 / 60 / 60) + "小时前";
        }
        // 如果大于等于1小时 小于等于一天，那么显示 ： **小时前
        else if (time >= 24 * 60 * 60 * 1000 && time < 3 * 24 * 60 * 60 * 1000) {
            res = (time / 1000 / 60 / 60 / 24) + "天前";
        }
        // 如果时间不明确或者发帖不足一分钟 ，则不显示
        else {
            res = DateUtil.getByTimeMillis(times);
        }

        return res;
    }

    /**
     * 取得系统当前时间前n个月的相对应的一天
     *
     * @param n int
     * @return String yyyy-mm-dd
     */
    public static String getNMonthBeforeCurrentDay(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -n);
        return "" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);

    }

    public static List<String> getAllBeforeDateToAfterDateSpace(String string, String string2) {
        List<String> date = null;
        try {
            date = new ArrayList<String>();
            Date dateTemp = new SimpleDateFormat("yyyy-MM-dd").parse(string);
            Date dateTemp2 = new SimpleDateFormat("yyyy-MM-dd").parse(string2);
            Calendar calendarTemp = Calendar.getInstance();
            calendarTemp.setTime(dateTemp);
            while (calendarTemp.getTime().getTime() != dateTemp2.getTime()) {
                date.add(new SimpleDateFormat("yyyy-MM-dd").format(calendarTemp.getTime()));
                calendarTemp.add(Calendar.DAY_OF_YEAR, 1);
            }
            date.add(string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /*******
     * 得到昨天日期
     *******/
    public static String getYesterDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    /**
     * 获取设备当前时间
     *
     * @return
     */
    public static long getCurrentTiem() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * 获取设备当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取设备当前时间
     *
     * @return
     */
    public static long getCurrentTiemMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 计算间隔返回时间字符串
     *
     * @param spanTime
     * @return
     */
    public static String getSpanDateStr(long spanTime, boolean showMinute) {
        spanTime = spanTime / 1000L;
        if (spanTime <= 0)
            return "00秒";
        long day = spanTime / 86400;
        long hours = (spanTime % 86400) / 3600;
        long minute = (spanTime % 3600) / 60;
        long second = spanTime % 60;

        String dStr = (day != 0) ? day + "天" : "";
        String hStr = (hours == 0 && dStr.length() == 0) ? "" : hours + "小时";
        // String hStr = (hours == 0 && dStr.length() == 0) ? "" : (hours < 10 ? "0" + hours : hours) + "小时";
        String mStr = (minute == 0 && hStr.length() == 0) ? "" : minute + "分";
        // String mStr = (minute == 0 && hStr.length() == 0) ? "" : (minute < 10 ? "0" + minute : minute) + "分";
        String sStr = "";
        if (showMinute || (!showMinute && mStr.length() == 0))
            sStr = (second == 0 && mStr.length() == 0) ? "" : second + "秒";
        // sStr = (second == 0 && mStr.length() == 0) ? "" : (second < 10 ? "0" + second : second) + "秒";
        return dStr + hStr + mStr + sStr;
    }


    public static String[] getServiceTime(Long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH|mm|ss|");
        Date date = new Date(time);
        String format = formatter.format(date);
        String[] split = format.split("\\|");
        return split;
    }

    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日hh:mm");
        String time = format.format(date);
        return time;
    }

    /**
     * 计算两个时间之间的隔间
     * @param sd1
     * @param sd2
     * @return  minute
     */
    public static String calculatorInterval(String sd1, String sd2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hh:mm");
        try {
            long interval = sdf.parse(sd1).getTime() - sdf.parse(sd2).getTime();
            interval = interval > 0 ? interval : interval * -1;
            interval /= 1000 * 60; //分
            long hour = interval / 60;
            long minute = interval % 60;
            LogUtil.e("----", "途中历史" + hour + "小时" + minute + "分");
            return minute + "";
        } catch (ParseException e) {
        }
        return "";

    }

    /**
     * @param time
     * @return
     */
    public static String long2Time_YY_MM_DD_hh_mm(long time) {
        String dates = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
            dates = sdf.format(new Date(time));
        } catch (Exception e) {
            dates = null;
        }

        return dates;

    }

    /**
     * @param timeMillis
     */
    public static String long2Date_yyyy_MM_DD_hh_mm(long timeMillis) {
        String dates = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
            dates = sdf.format(new Date(timeMillis));
        } catch (Exception e) {
            dates = null;
        }

        return dates;
    }
}

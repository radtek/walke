package walke.base.tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by walke.Z on 2017/8/17.
 */

public class PhoneUtil {
    /**
     * 获取手机信息
     */
    public static String getPhoneInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String mtyb = android.os.Build.BRAND;// 手机品牌
        String mtype = android.os.Build.MODEL; // 手机型号
        String imei = tm.getDeviceId();
        String imsi = tm.getSubscriberId();
        String numer = tm.getLine1Number(); // 手机号码
        String serviceName = tm.getSimOperatorName(); // 运营商
        return  "品牌: " + mtyb + "\n" + "型号: " + mtype + "\n" + "版本: Android " + android.os.Build.VERSION.RELEASE + "\n" + "IMEI: " + imei
                + "\n" + "IMSI: " + imsi + "\n" + "手机号码: " + numer + "\n" + "运营商: " + serviceName + "\n";
    }

    /**
     * 获取手机内存大小
     *
     * @return
     */
    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try
        {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString)
            {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        }
        catch (IOException e)
        {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获取当前可用内存大小
     *
     * @return
     */
    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.availMem);
    }

    /**
     * 获取手机CPU信息
     *
     * @return
     */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = { "", "" };
        String[] arrayOfString;
        try
        {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++)
            {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        }
        catch (IOException e) {

        }
        //tvHardwareInfo.append("CPU型号 " + cpuInfo[0] + "\n" + "CPU频率: " + cpuInfo[1] + "\n");
        return cpuInfo;
    }

    /**
     * 获取CPU核心数
     *
     * @return
     */
    public static int getNumCores() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            // Default to return 1 core
            return 1;
        }
    }

    /**  http://www.cnblogs.com/wangtianxj/archive/2011/03/18/1988358.html
     *   需要在工程的AndroidManifest.xml文件中，加入supports-screens节点
     *   <supports-screens
     android:smallScreens="true"
     android:normalScreens="true"
     android:largeScreens="true"
     android:resizeable="true"
     android:anyDensity="true" />
     * 这样的话，当前的Android程序就支持了多种分辨率，那么就可以得到正确的物理尺寸了。
     * @param activity
     * @return
     */
    public static Map<String,Integer> windowSize(Activity activity){
        Map<String,Integer> map=new HashMap<>();

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        float xdpi = metric.xdpi;
        float ydpi = metric.ydpi;
        float scaledDensity = metric.scaledDensity;
        map.put("屏幕宽度",width);
        map.put("屏幕高度",height);
        map.put("屏幕密度", (int) density);
        map.put("屏幕密度DPI",densityDpi);
        map.put("xdpi", (int) xdpi);
        map.put("ydpi", (int) ydpi);
        map.put("scaledDensity", (int) scaledDensity);
        return map;
    }

}

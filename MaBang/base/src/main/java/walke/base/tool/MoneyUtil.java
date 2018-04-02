package walke.base.tool;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2016/12/29.
 */
public class MoneyUtil {

    public static String fomatFromCentAddYuan(int money){

        double v = money * 0.01;
        DecimalFormat df =new DecimalFormat("#.00");
        String format = df.format(v);
        //icon_return format+"元";
        String result =String.format("%1$.2f",v);
        LogUtil.i("111",result);
        return result+"元";
    }
    public static String fomatFormCent(double money){
        double v = money * 0.01;
        // DecimalFormat df =new DecimalFormat("#.00");
        // String format = df.format(v);
        // icon_return format;
        String result =String.format("%1$.2f",v);
        LogUtil.i("111",result);
        return result;
    }

    public static String winMoney(int money){
        int v = (int) (money * 0.01);
        String result = v+"";
        LogUtil.i("111",result);
        return result;
    }


    public static int fomatToCent(String money){
        if (TextUtils.isEmpty(money)){
            return -1;
        }else {
            double aDouble = Double.parseDouble(money);
            double value = aDouble * 100;
            LogUtil.i("111","111");
            return (int) value;
        }
    }

}

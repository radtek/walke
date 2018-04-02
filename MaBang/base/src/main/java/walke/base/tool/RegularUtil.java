package walke.base.tool;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by walke.Z on 2017/7/31.
 * 正则工具类
 */

public class RegularUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";//"^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$" --之前

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}(\\d{2}[0-9xX])?$)";// /^\d{15}(\d{2}[0-9xX])?$/  (^\d{18}$)|(^\d{15}$)

    /**
     * 正则表达式：验证小写字母
     */
    public static final String LOWERCASE = "[a-z]";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    /*public static boolean isMobile(String mobile) {
        icon_return Pattern.matches(REGEX_MOBILE, mobile);
    }*/

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
      /*移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188  +147
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        [1][7358]\d{9}--原来
        [1][47358]\d{9}--2017.4.25 改*/
        String num = "[1][47358]\\d{9}";//"[1]"代表第1位为数字1，"[7358]"代表第二位可以为7、3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {//matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验小写字母
     *
     * @param letter
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLowercase(String letter) {
        return letter.matches(LOWERCASE);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}

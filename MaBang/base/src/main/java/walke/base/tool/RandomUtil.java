package walke.base.tool;

import java.util.Random;

public class RandomUtil {
    /**
     * 得到n位长度的随机数
     *
     * @param length 随机数的长度
     * @return 返回 n位(10位内)的随机整数
     */
    public static int Random(int length) {

        //生成最长10个数字
        if (length > 10) length = 10;

        int temp = 0;
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length);
        Random rand = new Random();
        while (true) {
            temp = rand.nextInt(max);
            if (temp >= min)
                break;
        }
        return temp;
    }

    public static void main(String[] args) {
        RandomUtil.Random(10);
    }
}

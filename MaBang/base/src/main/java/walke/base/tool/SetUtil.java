package walke.base.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by walke.Z on 2017/8/2.
 */

public class SetUtil {

    public static List<String> arrToList(String[] arr){
        List<String> list=new ArrayList<>();
        if (arr==null||arr.length==0)
            return list;
        for (String s : arr) {
            list.add(s);
        }
        return list;
    }

    public static void sort(List<Objects> list){
        Collections.sort(list, new Comparator<Objects>() {
                    /*  按票箱号排序
                     * int compare(Student o1, Student o2) 返回一个基本类型的整型，  
                     * 返回负数表示：o1 小于o2，    返回0 表示：o1和o2相等，   返回正数表示：o1大于o2。  
                     */
                    public int compare(Objects o1, Objects o2) {
                        if (o1.hashCode() > o2.hashCode()) {
                            return 1;
                        }
                        if (o1.hashCode() == o2.hashCode()) {
                            return 0;
                        }
                        return -1;
                    }
                }
        );

    }

}

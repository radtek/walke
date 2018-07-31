package com.aipiao.bkpkold.comparator;

import com.aipiao.bkpkold.bean.GameFootballContactSortModel;
import com.aipiao.bkpkold.utils.DateUtil;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by caihui on 2018/4/8.
 */

public class GameFootballContactSortModelTimeComparator implements Comparator<GameFootballContactSortModel> {
    @Override
    public int compare(GameFootballContactSortModel o1, GameFootballContactSortModel o2) {
        Date date1 = DateUtil.stringToDate(o1.getSortTime());
        Date date2 = DateUtil.stringToDate(o2.getSortTime());
        // 对日期字段进行升序，如果欲降序可采用after方法
        if (date1.before(date2)) {
            return 1;
        }
        return -1;

    }
}

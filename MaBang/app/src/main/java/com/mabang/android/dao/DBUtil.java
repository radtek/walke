package com.mabang.android.dao;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.j256.ormlite.table.TableUtils;
import com.mabang.android.entity.vo.BillboardInfo;

import java.util.List;

/**
 * Created by caihui on 2016/10/23.
 */
public class DBUtil {

    private static final String TAG = "DBUtil";

    /**
     * 增
     */
    public static boolean addBillboardInfo(Context context, BillboardInfo billboardInfo) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);

        try {
            helper.getBillboardInfoDao().create(billboardInfo);
            Log.d(TAG, "addUserTest: --->");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 增
     */
    public static boolean checkExist(Context context, BillboardInfo billboardInfo) {
        List<BillboardInfo> billboardInfos = queryAll(context);
        if (billboardInfos == null || billboardInfos.size() < 1) {
            return false;
        } else {
            for (BillboardInfo info : billboardInfos) {
                if (info.getId().equals(billboardInfo.getId()))
                    return true;
            }
        }
        return false;
    }

    /**
     * 删
     */
    public static boolean deleteOne(Context context, BillboardInfo billboardInfo) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);

        try {
            int delete = helper.getBillboardInfoDao().delete(billboardInfo);
            Log.i("walke", "deleteOne: ----------------------------delete=" + delete);//0 失败
            if (delete == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删
     */
//    public static boolean delete(Context context, BillboardInfo billboardInfo) {
//        DatabaseHelper helper = DatabaseHelper.getHelper(context);
//
//        try {
//            DeleteBuilder<BillboardInfo, Integer> integerDeleteBuilder = helper.getBillboardInfoDao().deleteBuilder();
//            DeleteBuilder<BillboardInfo, String> deleteBuilder = (DeleteBuilder<BillboardInfo, String>) integerDeleteBuilder;
//            int delete = helper.getBillboardInfoDao().deleteBuilder();
//            Log.i("walke", "deleteOne: ----------------------------delete=" + delete);//0 失败
//            if (delete == 1)
//                return true;
//            else
//                return false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * 删
     */
    public static boolean deleteList(Context context, List<BillboardInfo> bis) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);

        try {
            for (BillboardInfo bi : bis) {
//                helper.getBillboardInfoDao().deleteById(bi.getId());//这ById的"id"是表数据的下标
                int delete = helper.getBillboardInfoDao().delete(bi);
                Log.i("walke", "deleteList: ----------------------------delete=" + delete);//1 成功
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     */
    public static boolean deleteAll(Context context) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);
        try {
            TableUtils.clearTable(helper.getConnectionSource(), BillboardInfo.class);
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 改
     */
    public static boolean updateUserTest(Context context, BillboardInfo billboardInfo) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);
        try {
            helper.getBillboardInfoDao().update(billboardInfo);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查
     */
    public static List<BillboardInfo> queryAll(Context context) {
        DatabaseHelper helper = DatabaseHelper.getHelper(context);
        List<BillboardInfo> list = null;
        try {
            list = helper.getBillboardInfoDao().queryForAll();
//            BillboardInfo user = helper.getBillboardInfoDao().queryForId(1);
            System.out.println("------------------------------------------------------------" + list);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).toString());
            }
        }
        return list;
    }

}

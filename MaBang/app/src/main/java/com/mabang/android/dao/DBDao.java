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
public class DBDao {

    private static final String TAG = "DBDao";
    private Context mContext;

    public DBDao(Context context) {
        mContext = context;
    }
    /**
     * 增
     */
    public void addBillboardInfo(BillboardInfo billboardInfo)
    {
        DatabaseHelper helper = DatabaseHelper.getHelper(mContext);

        try {
            helper.getBillboardInfoDao().create(billboardInfo);
            Log.d(TAG, "addUserTest: --->");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 删
     */
    public void delUserTest(BillboardInfo billboardInfo)
    {
        DatabaseHelper helper=DatabaseHelper.getHelper(mContext);

        try {
            helper.getBillboardInfoDao().deleteById(billboardInfo.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     */
    public void deleteAll(){
        DatabaseHelper helper = DatabaseHelper.getHelper(mContext);

        try {
            TableUtils.clearTable(helper.getConnectionSource(),BillboardInfo.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 改
     */
    public void updateUserTest(BillboardInfo billboardInfo) {
        DatabaseHelper helper=DatabaseHelper.getHelper(mContext);
        try {
            helper.getBillboardInfoDao().update(billboardInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查
     */
    public void queryUserTest()
    {
        DatabaseHelper helper=DatabaseHelper.getHelper(mContext);
        List<BillboardInfo> list=null;
        try {
            list = helper.getBillboardInfoDao().queryForAll();
            BillboardInfo user = helper.getBillboardInfoDao().queryForId(5);
            System.out.println("------------------------------------------------------------"+user.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        if(list!=null)
        {
            for(int i=0;i<list.size();i++)
            {
                System.out.println(list.get(i).toString());
            }
        }
    }

}

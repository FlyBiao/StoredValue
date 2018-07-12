package com.sw.android.storedvalue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.sw.android.storedvalue.db.bean.PosPayBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/11/20 21:12
 * Version 1.0
 */

public class PosSqliteDatabaseUtils extends SQLiteOpenHelper {
    public static final String TAG = "TestSQLite";
    private static List<PosPayBean> posPayBeanArrayList=new ArrayList<>();
    //必须要有构造函数
    public PosSqliteDatabaseUtils(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                  int version) {
        super(context, name, factory, version);
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        try {
            //输出创建数据库的日志信息
            Log.i(TAG, "create Database------------->");
            //execSQL函数用于执行SQL语句
            db.execSQL(TableUtils.create_pos_stored_value_info_sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(db.getVersion()!=DBConstant.VERSION){
            //发现新版本数据库
//            db.execSQL(TableUtils.del_order_sql);
        }
    }

    //创建sqlite数据库
    public static void createDB(Context ct, String dbName, int version){
        //创建StuDBHelper对象
        PosSqliteDatabaseUtils dbHelper = new PosSqliteDatabaseUtils(ct,dbName,null,version);
        //得到一个可读的SQLiteDatabase对象
        SQLiteDatabase db =dbHelper.getReadableDatabase();
    }

    //插入数据
    public static void insterData(Context ct, PosPayBean bean){
        PosSqliteDatabaseUtils dbHelper = new PosSqliteDatabaseUtils(ct,DBConstant.DB,null,DBConstant.VERSION);
        //得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        //生成ContentValues对象 //key:列名，value:想插入的值
        ContentValues cv = new ContentValues();
        try {
            //往ContentValues对象存放数据，键-值对模式
            cv.put("CreateTime",bean.getCreateTime());
            cv.put("ShopId", bean.getShopId());
            cv.put("ShopName", bean.getShopName());
            cv.put("CreateName",bean.getCreateName());
            cv.put("OrderNo",bean.getOrderNo());
            cv.put("Amount",bean.getAmount());
            cv.put("TraceAudit",bean.getTraceAudit());
            cv.put("PayType",bean.getPayType());
            cv.put("PayName",bean.getPayName());
            cv.put("IsPractical",bean.getIsPractical());
            cv.put("PayStatus",bean.getPayStatus());
            cv.put("Message",bean.getMessage());
            cv.put("EnCode",bean.getEnCode());
            cv.put("AccountNumber",bean.getAccountNumber());
            cv.put("IsSuccess",bean.getIsSuccess());
            //调用insert方法，将数据插入数据库
            db.insert(TableUtils.pos_stored_value_info, null, cv);
            Log.i(TAG,"添加数据成功");
        }catch (Exception e){
            e.printStackTrace();
        }

        //关闭数据库
        db.close();
    }

    /**
     * 查询数据
     * @param ct
     * @param
     */
    public static void selectData(Context ct){
        PosSqliteDatabaseUtils dbHelper = new PosSqliteDatabaseUtils(ct,DBConstant.DB,null,DBConstant.VERSION);
        //得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        //参数1：表名
        //参数2：要想显示的列
        //参数3：where子句
        //参数4：where子句对应的条件值
        //参数5：分组方式
        //参数6：having条件
        //参数7：排序方式
        posPayBeanArrayList=new ArrayList<>();
        try {
            Cursor cursor = db.query(TableUtils.pos_stored_value_info, new String[]{"ShopId","ShopName","CreateName","OrderNo","Amount","TraceAudit","PayType","PayName","IsPractical","PayStatus","CreateTime","Message","EnCode","AccountNumber","IsSuccess"}, null, null, null, null, null);
            while(cursor.moveToNext()){
                String ShopId = cursor.getString(cursor.getColumnIndex("ShopId"));
                String ShopName = cursor.getString(cursor.getColumnIndex("ShopName"));
                String CreateName = cursor.getString(cursor.getColumnIndex("CreateName"));
                String OrderNo=cursor.getString(cursor.getColumnIndex("OrderNo"));
                String Amount = cursor.getString(cursor.getColumnIndex("Amount"));
                String TraceAudit = cursor.getString(cursor.getColumnIndex("TraceAudit"));
                String PayType = cursor.getString(cursor.getColumnIndex("PayType"));
                String PayName = cursor.getString(cursor.getColumnIndex("PayName"));
                String IsPractical=cursor.getString(cursor.getColumnIndex("IsPractical"));
                String PayStatus = cursor.getString(cursor.getColumnIndex("PayStatus"));
                String CreateTime = cursor.getString(cursor.getColumnIndex("CreateTime"));
                String Message = cursor.getString(cursor.getColumnIndex("Message"));
                String EnCode = cursor.getString(cursor.getColumnIndex("EnCode"));
                String AccountNumber = cursor.getString(cursor.getColumnIndex("AccountNumber"));
                String IsSuccess = cursor.getString(cursor.getColumnIndex("IsSuccess"));

                PosPayBean bean=new PosPayBean();
                bean.setShopId(ShopId);
                bean.setShopName(ShopName);
                bean.setCreateName(CreateName);
                bean.setOrderNo(OrderNo);
                bean.setAmount(Amount);
                bean.setTraceAudit(TraceAudit);
                bean.setPayType(PayType);
                bean.setPayName(PayName);
                bean.setIsPractical(IsPractical);
                bean.setPayStatus(PayStatus);
                bean.setCreateTime(CreateTime);
                bean.setMessage(Message);
                bean.setEnCode(EnCode);
                bean.setAccountNumber(AccountNumber);
                bean.setIsSuccess(IsSuccess);
                posPayBeanArrayList.add(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        EventBus.getDefault().post(posPayBeanArrayList);
        //关闭数据库
        db.close();
    }

    //删除所有数据数据
    public static void delete(Context ct){
        PosSqliteDatabaseUtils dbHelper = new PosSqliteDatabaseUtils(ct,DBConstant.DB,null,DBConstant.VERSION);
        //得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        try {
            db.delete(TableUtils.pos_stored_value_info, null, null);
            Log.i(TAG,"删除数据------->" );
        }catch (Exception e){
            e.printStackTrace();
        }
        //关闭数据库
        db.close();
    }

}

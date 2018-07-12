package com.sw.android.storedvalue.db;

/**
 * Author FGB
 * Description
 * Created at 2017/5/31 16:21
 * Version 1.0
 */

public class TableUtils {

    public static String pos_stored_value_info="pos_stored_value_info";

    public static final String create_pos_stored_value_info_sql = "create table pos_stored_value_info" +
            "(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "CreateTime varchar(200)," +
            "EnCode varchar(200)," +
            "ShopId varchar(200)," +
            "ShopName varchar(200)," +
            "CreateName varchar(200)," +
            "OrderNo varchar(200)," +
            "Amount varchar(200)," +
            "TraceAudit varchar(200)," +
            "AccountNumber varchar(200)," +
            "PayType varchar(200)," +
            "PayName varchar(200)," +
            "IsPractical varchar(200)," +
            "PayStatus varchar(200)," +
            "Message varchar(200)," +
            "IsSuccess varchar(200)" +
            ")";

    public static final String del_order_sql = "DROP TABLE pos_stored_value_info";

    public static final String alert_order_sql = "ALERT TABLE pos_stored_value_info ADD 需要新增的列明  varchar(256)";
}

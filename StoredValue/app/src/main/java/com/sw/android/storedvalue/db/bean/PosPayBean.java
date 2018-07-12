package com.sw.android.storedvalue.db.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/11/20 17:27
 * Version 1.0
 */

public class PosPayBean {

    private String Id;
    private String ShopId;//店铺Id
    private String ShopName;//店铺店铺名称
    private String CreateName;//订单营业员
    private String OrderNo;//订单Id
    private String Amount;//金额
    private String TraceAudit;//凭证号
    private String PayType;//支付支付类型
    private String PayName;//支付名称
    private String IsPractical;//是否实销
    private String PayStatus;//支付状态
    private String CreateTime;//创建时间
    private String Message;//消息
    private String EnCode;//设备编号
    private String AccountNumber;//卡号
    private String IsSuccess;//是否成功
    private int QueryType;

    public PosPayBean(){}
    public PosPayBean(String shopId, String shopName, String createName, String orderNo, String amount, String traceAudit, String payType, String payName, String isPractical, String payStatus, String createTime, String message, String enCode, String accountNumber, String isSuccess) {
        ShopId = shopId;
        ShopName = shopName;
        CreateName = createName;
        OrderNo = orderNo;
        Amount = amount;
        TraceAudit = traceAudit;
        PayType = payType;
        PayName = payName;
        IsPractical = isPractical;
        PayStatus = payStatus;
        CreateTime = createTime;
        Message = message;
        EnCode = enCode;
        AccountNumber = accountNumber;
        IsSuccess = isSuccess;
    }

    public JSONObject getPosPayInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("CreateTime",getCreateTime());
            obj.put("ShopId",getShopId());
            obj.put("ShopName",getShopName());
            obj.put("CreateName",getCreateName());
            obj.put("OrderNo",getOrderNo());
            obj.put("Amount",getAmount());
            obj.put("TraceAudit",getTraceAudit());
            obj.put("PayType",getPayType());
            obj.put("PayName",getPayName());
            obj.put("IsPractical",getIsPractical());
            obj.put("PayStatus",getPayStatus());
            obj.put("Message",getMessage());
            obj.put("EnCode",getEnCode());
            obj.put("AccountNumber",getAccountNumber());
            obj.put("IsSuccess",getIsSuccess());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public int getQueryType() {
        return QueryType;
    }

    public void setQueryType(int queryType) {
        QueryType = queryType;
    }

    public String getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        IsSuccess = isSuccess;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getEnCode() {
        return EnCode;
    }

    public void setEnCode(String enCode) {
        EnCode = enCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String shopId) {
        ShopId = shopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getCreateName() {
        return CreateName;
    }

    public void setCreateName(String createName) {
        CreateName = createName;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTraceAudit() {
        return TraceAudit;
    }

    public void setTraceAudit(String traceAudit) {
        TraceAudit = traceAudit;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getPayName() {
        return PayName;
    }

    public void setPayName(String payName) {
        PayName = payName;
    }

    public String getIsPractical() {
        return IsPractical;
    }

    public void setIsPractical(String isPractical) {
        IsPractical = isPractical;
    }

    public String getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(String payStatus) {
        PayStatus = payStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}

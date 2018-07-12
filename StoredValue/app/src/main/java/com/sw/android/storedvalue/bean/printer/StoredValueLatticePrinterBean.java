package com.sw.android.storedvalue.bean.printer;

/**
 * Author FGB
 * Description 充值点阵打印
 * Created at 2017/7/21 15:58
 * Version 1.0
 */

public class StoredValueLatticePrinterBean {

    private String Title;//充值规则标题
    private String shopName;//店铺名称
    private String orderId;//订单号
    private String counterName;//柜台
    private String shopClerkName;//营业员
    private String traceAuditNumber;//凭证号
    private String payType;//支付类型
    private double Amount;//充值金额
    private double GivenAmount;//赠送金额
    private double GivenBalance;//赠送余额
    private double UsableBalance;//可用余额
    private double TotalBalance;//总余额恶
    private String VipId;


    public double getUsableBalance() {
        return UsableBalance;
    }

    public void setUsableBalance(double usableBalance) {
        UsableBalance = usableBalance;
    }

    public double getTotalBalance() {
        return TotalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        TotalBalance = totalBalance;
    }

    public double getGivenBalance() {
        return GivenBalance;
    }

    public void setGivenBalance(double givenBalance) {
        GivenBalance = givenBalance;
    }

    public double getGivenAmount() {
        return GivenAmount;
    }

    public void setGivenAmount(double givenAmount) {
        GivenAmount = givenAmount;
    }

    public String getVipId() {
        return VipId;
    }

    public void setVipId(String vipId) {
        VipId = vipId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getShopClerkName() {
        return shopClerkName;
    }

    public void setShopClerkName(String shopClerkName) {
        this.shopClerkName = shopClerkName;
    }

    public String getTraceAuditNumber() {
        return traceAuditNumber;
    }

    public void setTraceAuditNumber(String traceAuditNumber) {
        this.traceAuditNumber = traceAuditNumber;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }
}

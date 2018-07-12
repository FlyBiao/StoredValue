package com.sw.android.storedvalue.bean.printer;

/**
 * Author FGB
 * Description 店铺充值点阵打印
 * Created at 2017/7/21 15:58
 * Version 1.0
 */

public class ShopStoredValueLatticePrinterBean {

    private String shopName;//店铺名称
    private String counterName;//柜台
    private String shopClerkName;//营业员
    private String payType;//支付类型

    private double WeiXinPayAmount;//微信充值金额
    private double WeiXinPayGivenAmount;//微信赠送金额

    private double AliPayAmount;//支付宝充值金额
    private double AliPayGivenAmount;//支付宝赠送金额

    private double UnionPayAmount;//银联充值金额
    private double UnionPayGivenAmount;//银联赠送金额

    private double CashPayAmount;//现金充值金额
    private double CashPayGivenAmount;//现金赠送金额

    private double CurrentTotalAmount;//当前充值总金额
    private double CurrentGivenTotalAmount;//当前赠送总金额


    public double getCashPayAmount() {
        return CashPayAmount;
    }

    public void setCashPayAmount(double cashPayAmount) {
        CashPayAmount = cashPayAmount;
    }

    public double getCashPayGivenAmount() {
        return CashPayGivenAmount;
    }

    public void setCashPayGivenAmount(double cashPayGivenAmount) {
        CashPayGivenAmount = cashPayGivenAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getWeiXinPayAmount() {
        return WeiXinPayAmount;
    }

    public void setWeiXinPayAmount(double weiXinPayAmount) {
        WeiXinPayAmount = weiXinPayAmount;
    }

    public double getWeiXinPayGivenAmount() {
        return WeiXinPayGivenAmount;
    }

    public void setWeiXinPayGivenAmount(double weiXinPayGivenAmount) {
        WeiXinPayGivenAmount = weiXinPayGivenAmount;
    }

    public double getAliPayAmount() {
        return AliPayAmount;
    }

    public void setAliPayAmount(double aliPayAmount) {
        AliPayAmount = aliPayAmount;
    }

    public double getAliPayGivenAmount() {
        return AliPayGivenAmount;
    }

    public void setAliPayGivenAmount(double aliPayGivenAmount) {
        AliPayGivenAmount = aliPayGivenAmount;
    }

    public double getUnionPayAmount() {
        return UnionPayAmount;
    }

    public void setUnionPayAmount(double unionPayAmount) {
        UnionPayAmount = unionPayAmount;
    }

    public double getUnionPayGivenAmount() {
        return UnionPayGivenAmount;
    }

    public void setUnionPayGivenAmount(double unionPayGivenAmount) {
        UnionPayGivenAmount = unionPayGivenAmount;
    }

    public double getCurrentTotalAmount() {
        return CurrentTotalAmount;
    }

    public void setCurrentTotalAmount(double currentTotalAmount) {
        CurrentTotalAmount = currentTotalAmount;
    }

    public double getCurrentGivenTotalAmount() {
        return CurrentGivenTotalAmount;
    }

    public void setCurrentGivenTotalAmount(double currentGivenTotalAmount) {
        CurrentGivenTotalAmount = currentGivenTotalAmount;
    }
}

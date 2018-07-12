package com.sw.android.storedvalue.bean.printer;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：点阵打印Bean
 * 创建日期：2016/11/4 13:54
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class LatticePrinterBean {

    private String shopName;//店铺名称
    private String orderId;//订单号
    private String counterName;//柜台
    private String shopClerkName;//营业员
    private String traceAuditNumber;//凭证号
    private int payType;//支付类型
    private String payTitleName;//支付标题名称
    private double originalPrice;//原价
    private double discount;//折扣
    private double discountPrice;//折后价格
    private double totalPrice;//总价格
    private String vipMobile;//会员手机
    private int thePoint=0;//本次积分
    private int totalPoint=0;//累计积分
    private int consumPtion=0;//消费积分


    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
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

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayTitleName() {
        return payTitleName;
    }

    public void setPayTitleName(String payTitleName) {
        this.payTitleName = payTitleName;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getVipMobile() {
        return vipMobile;
    }

    public void setVipMobile(String vipMobile) {
        this.vipMobile = vipMobile;
    }

    public int getThePoint() {
        return thePoint;
    }

    public void setThePoint(int thePoint) {
        this.thePoint = thePoint;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getConsumPtion() {
        return consumPtion;
    }

    public void setConsumPtion(int consumPtion) {
        this.consumPtion = consumPtion;
    }
}

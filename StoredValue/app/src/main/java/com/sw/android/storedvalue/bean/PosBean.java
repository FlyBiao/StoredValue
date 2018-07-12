package com.sw.android.storedvalue.bean;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：
 * 创建日期：2016/10/19 16:03
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class PosBean {

    public String AccountNumber;//卡号
    public String ReferenceNumber;//参考号
    public String TraceAuditNumber;//凭证号
    public String AliPayrefNo;//支付宝交易号
    public String WeiXinPayrefNo;//微信交易号
    public String OrderId;//支付订单号
    public double PayAmount;//支付金额
    public int PayType;//支付方式 2：微信，3：支付宝，4银联，5现金
    public String PayQrCode;//支付二维码
}

package com.sw.android.storedvalue.bean;

/**
 * Author FGB
 * Description
 * Created at 2017/10/30 16:08
 * Version 1.0
 */
public class PayLogBean {
    public String AccountNumber;//卡号
    public String ReferenceNumber;//参考号
    public String TraceAuditNumber;//凭证号
    public String AliPayrefNo;//支付宝交易号
    public String WeiXinPayrefNo;//微信交易号
    public double PayAmount;//支付金额
    public int PayType;//支付方式 2：微信，3：支付宝，4银联，5现金
    private String Remark;
    public String enCode;//设备EN号
    private int VipId;
    private int ShopId;
    private int RuleId;
    private int MachineNo;
    private int CashierId;

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        ReferenceNumber = referenceNumber;
    }

    public String getTraceAuditNumber() {
        return TraceAuditNumber;
    }

    public void setTraceAuditNumber(String traceAuditNumber) {
        TraceAuditNumber = traceAuditNumber;
    }

    public String getAliPayrefNo() {
        return AliPayrefNo;
    }

    public void setAliPayrefNo(String aliPayrefNo) {
        AliPayrefNo = aliPayrefNo;
    }

    public String getWeiXinPayrefNo() {
        return WeiXinPayrefNo;
    }

    public void setWeiXinPayrefNo(String weiXinPayrefNo) {
        WeiXinPayrefNo = weiXinPayrefNo;
    }

    public double getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(double payAmount) {
        PayAmount = payAmount;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int payType) {
        PayType = payType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    public int getVipId() {
        return VipId;
    }

    public void setVipId(int vipId) {
        VipId = vipId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public int getRuleId() {
        return RuleId;
    }

    public void setRuleId(int ruleId) {
        RuleId = ruleId;
    }

    public int getMachineNo() {
        return MachineNo;
    }

    public void setMachineNo(int machineNo) {
        MachineNo = machineNo;
    }

    public int getCashierId() {
        return CashierId;
    }

    public void setCashierId(int cashierId) {
        CashierId = cashierId;
    }
}

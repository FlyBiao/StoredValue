package com.sw.android.storedvalue.bean;


import com.sw.android.storedvalue.base.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description 会员信息结果Bean
 * Created at 2017/7/20 10:22
 * Version 1.0
 */

public class ResultVipInfoBean extends BaseBean {

    public VipInfoBean TModel;

    public class VipInfoBean implements Serializable {

        //    VipId	String
//    VipName	String	名称
//    VipIcon	String	会员头像
//    VipGrade	String	会员等级
//    ShopId	String	所属店铺Id
//        IdNumber String ""
//    RechargeShopId	String	储值店铺Id
//    Amount	String	实际充值可用余额
//    DonationAmount	String	赠送可用余额
//    ConsumeAmount	String	累计充值消费金额
//    Status	String	状态 -1：无储值账套 0:申请 1:生效 2:冻结 3:该店铺无法使用
//    ConsumeDonationAmount	String	累计赠送消费金额

        private String VipId;
        private String VipName;
        private String VipIcon;
        private String VipGrade;
        private int ShopId;
        private String RechargeShopId;
        private double Amount;
        private double DonationAmount;
        private double ConsumeAmount;
        private int Status;
        private double ConsumeDonationAmount;

        public String getVipId() {
            return VipId;
        }

        public void setVipId(String vipId) {
            VipId = vipId;
        }

        public String getVipName() {
            return VipName;
        }

        public void setVipName(String vipName) {
            VipName = vipName;
        }

        public String getVipIcon() {
            return VipIcon;
        }

        public void setVipIcon(String vipIcon) {
            VipIcon = vipIcon;
        }

        public String getVipGrade() {
            return VipGrade;
        }

        public void setVipGrade(String vipGrade) {
            VipGrade = vipGrade;
        }

        public int getShopId() {
            return ShopId;
        }

        public void setShopId(int shopId) {
            ShopId = shopId;
        }

        public String getRechargeShopId() {
            return RechargeShopId;
        }

        public void setRechargeShopId(String rechargeShopId) {
            RechargeShopId = rechargeShopId;
        }


        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public double getDonationAmount() {
            return DonationAmount;
        }

        public void setDonationAmount(double donationAmount) {
            DonationAmount = donationAmount;
        }

        public double getConsumeAmount() {
            return ConsumeAmount;
        }

        public void setConsumeAmount(double consumeAmount) {
            ConsumeAmount = consumeAmount;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public double getConsumeDonationAmount() {
            return ConsumeDonationAmount;
        }

        public void setConsumeDonationAmount(double consumeDonationAmount) {
            ConsumeDonationAmount = consumeDonationAmount;
        }
    }
}

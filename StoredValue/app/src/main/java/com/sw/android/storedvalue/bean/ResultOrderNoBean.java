package com.sw.android.storedvalue.bean;

import com.sw.android.storedvalue.base.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/9/30 10:03
 * Version 1.0
 */

public class ResultOrderNoBean extends BaseBean {

    public OrderNoBean TModel;

    public class OrderNoBean implements Serializable{

        /**
         * Amount : 0.01
         * CardNo : 13430706607
         * CreateTime : 2017/9/29 17:29:41
         * DonationAmount : 100.0
         * OrderNo : 10001751822017092900000004
         * PayMethod : 2
         * RuleName : 充0.01元赠100.00元
         * ShopId : 16190
         * TotalAmount : 10720.24
         * TotalDonationAmount : 4020.0
         * VipId : 543868
         */

        private double Amount;
        private String CardNo;
        private String CreateTime;
        private double DonationAmount;
        private String OrderNo;
        private int PayMethod;
        private String RuleName;
        private int ShopId;
        private double TotalAmount;
        private double TotalDonationAmount;
        private String VipId;

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public void setDonationAmount(double DonationAmount) {
            this.DonationAmount = DonationAmount;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }

        public void setPayMethod(int PayMethod) {
            this.PayMethod = PayMethod;
        }

        public void setRuleName(String RuleName) {
            this.RuleName = RuleName;
        }

        public void setShopId(int ShopId) {
            this.ShopId = ShopId;
        }

        public void setTotalAmount(double TotalAmount) {
            this.TotalAmount = TotalAmount;
        }

        public void setTotalDonationAmount(double TotalDonationAmount) {
            this.TotalDonationAmount = TotalDonationAmount;
        }

        public void setVipId(String VipId) {
            this.VipId = VipId;
        }

        public double getAmount() {
            return Amount;
        }

        public String getCardNo() {
            return CardNo;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public double getDonationAmount() {
            return DonationAmount;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public int getPayMethod() {
            return PayMethod;
        }

        public String getRuleName() {
            return RuleName;
        }

        public int getShopId() {
            return ShopId;
        }

        public double getTotalAmount() {
            return TotalAmount;
        }

        public double getTotalDonationAmount() {
            return TotalDonationAmount;
        }

        public String getVipId() {
            return VipId;
        }
    }
}

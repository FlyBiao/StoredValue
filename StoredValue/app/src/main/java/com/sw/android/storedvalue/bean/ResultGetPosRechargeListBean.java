package com.sw.android.storedvalue.bean;


import com.sw.android.storedvalue.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description POS充值查询结果Bean
 * Created at 2017/7/29 16:10
 * Version 1.0
 */

public class ResultGetPosRechargeListBean extends BaseBean {

    public List<GetPosRechargeListBean> TModel;


    public class GetPosRechargeListBean implements Serializable {

        /**
         * Amount : 0.02
         * PayType : 2
         * PayTypeName : 微信
         * Type : 1 充值  2  赠送
         * TypeName : 充值
         */

        private double Amount;
        private int PayType;
        private String PayTypeName;
        private String TypeName;
        private double DonationAmount;

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public void setPayType(int PayType) {
            this.PayType = PayType;
        }

        public void setPayTypeName(String PayTypeName) {
            this.PayTypeName = PayTypeName;
        }

        public double getDonationAmount() {
            return DonationAmount;
        }

        public void setDonationAmount(double donationAmount) {
            DonationAmount = donationAmount;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public double getAmount() {
            return Amount;
        }

        public int getPayType() {
            return PayType;
        }

        public String getPayTypeName() {
            return PayTypeName;
        }


        public String getTypeName() {
            return TypeName;
        }
    }
}

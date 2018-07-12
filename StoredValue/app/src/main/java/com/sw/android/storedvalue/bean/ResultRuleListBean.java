package com.sw.android.storedvalue.bean;


import com.sw.android.storedvalue.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 储值规则列表结果Bean
 * Created at 2017/7/20 10:31
 * Version 1.0
 */

public class ResultRuleListBean extends BaseBean {

    public List<RuleListBean> TModel;


    public class RuleListBean implements Serializable {
//        Id	Int		规则Id
//        Title	String		名称
//        Type	Int		充值类型 0:固定金额:1:固定比例 2:任意金额
//        UnitId	Int		结算单位Id
//        FromTime	Date		开始时间
//        ToTime	Date		结束时间
//        BegAmount	Decimal		开始金额/充值金额
//        EndAmount	Decimal		结束金额
//        DonationAmount	Decimal		赠送金额
//        DonationRate	Decimal		赠送比例
//        IsUsed	Int		状态


        private int Id;
        private String Title;
        private int Type;
        private int UId;
        private String FromTime;
        private String ToTime;
        private double BegAmount;
        private double EndAmount;
        private double DonationAmount;
        private double DonationRate;
        private int IsUsed;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public int getType() {
            return Type;
        }

        public void setType(int type) {
            Type = type;
        }

        public int getUId() {
            return UId;
        }

        public void setUId(int unitId) {
            UId = unitId;
        }

        public String getFromTime() {
            return FromTime;
        }

        public void setFromTime(String fromTime) {
            FromTime = fromTime;
        }

        public String getToTime() {
            return ToTime;
        }

        public void setToTime(String toTime) {
            ToTime = toTime;
        }

        public double getBegAmount() {
            return BegAmount;
        }

        public void setBegAmount(double begAmount) {
            BegAmount = begAmount;
        }

        public double getEndAmount() {
            return EndAmount;
        }

        public void setEndAmount(double endAmount) {
            EndAmount = endAmount;
        }

        public double getDonationAmount() {
            return DonationAmount;
        }

        public void setDonationAmount(double donationAmount) {
            DonationAmount = donationAmount;
        }

        public double getDonationRate() {
            return DonationRate;
        }

        public void setDonationRate(double donationRate) {
            DonationRate = donationRate;
        }

        public int getIsUsed() {
            return IsUsed;
        }

        public void setIsUsed(int isUsed) {
            IsUsed = isUsed;
        }
    }
}

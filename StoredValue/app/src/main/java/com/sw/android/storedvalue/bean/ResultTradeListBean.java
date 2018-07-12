package com.sw.android.storedvalue.bean;


import com.sw.android.storedvalue.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 交易记录结果Bean
 * Created at 2017/7/20 12:00
 * Version 1.0
 */

public class ResultTradeListBean extends BaseBean {

    public List<TradeListBean> TModel;


    public class TradeListBean implements Serializable {
//        Id	Int		单据流水号
//        Type	Int		0:结转 1:充值 2:充值赠送 3:充值金额购物消费 4:充值金额购物退款 5:充值退款 6:赠送金额购物消费 7:赠送金额购物消费退款
//        Amount	String		金额
//        Remark	String		交易说明
//        CreateTime	String		交易时间


        private String OrderNo;
        private int Type;
        private double Amount;
        private String Remark;
        private String CreateTime;
        private int Id;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String orderNo) {
            OrderNo = orderNo;
        }

        public int getType() {
            return Type;
        }

        public void setType(int type) {
            Type = type;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }
    }
}

package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultAddTradeBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description 充值
 * Created at 2017/7/20 9:09
 * Version 1.0
 */

public class AddTradeNet extends BaseValueNet {
    public AddTradeNet(Context context) {
        super(context, true);
        this.uri="Wall/SW/Vip/AddTrade";
    }

    public void setData(int VipId,int ShopId,String OrderNo,int PaymentType,double Amount,int RuleId,String MachineNo,int CashierId) {
        try {
            data.put("VipId",VipId);
            data.put("ShopId",ShopId);
            data.put("OrderNo",OrderNo);
            data.put("PaymentType",PaymentType);
            data.put("Amount",Amount);
            data.put("RuleId",RuleId);
            data.put("MachineNo",MachineNo);
            data.put("CashierId",CashierId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"充值:"+rJson);
        Gson gson=new Gson();
        ResultAddTradeBean bean=gson.fromJson(rJson,ResultAddTradeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }

}
package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultGetPosRechargeListBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description POS充值查询
 * Created at 2017/7/29 15:46
 * Version 1.0
 */
public class GetPosRechargeListNet extends BaseValueNet {
    public GetPosRechargeListNet(Context context) {
        super(context, true);
        this.uri="Wall/SW/Shop/GetPosRechargeList";
    }

    public void setData(String MachineNo,String start_date,String end_date) {
        try {
            data.put("start_date",start_date);
            data.put("end_date",end_date);
            data.put("MachineNo",MachineNo);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
//        Log.i(Constant.TAG,rJson);
        Gson gson=new Gson();
        ResultGetPosRechargeListBean bean=gson.fromJson(rJson,ResultGetPosRechargeListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }

}

package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultCheckUnitBean;
import com.sw.android.storedvalue.bean.ResultOrderNoBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description 查询单条储值记录
 * Created at 2017/7/19 20:11
 * Version 1.0
 */

public class GetOneByOrderNoNet extends BaseValueNet {
    public GetOneByOrderNoNet(Context context) {
        super(context, true);
        this.uri="Wall/SW/Vip/GetOneByOrderNo";
    }

    public void setData(String id) {
        try {
            data.put("OrderNo",id);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultOrderNoBean bean=gson.fromJson(rJson,ResultOrderNoBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }

}
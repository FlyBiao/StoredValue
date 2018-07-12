package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultRuleListBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description 储值规则列表
 * Created at 2017/7/19 20:13
 * Version 1.0
 */

public class RuleListNet extends BaseValueNet {
    public RuleListNet(Context context) {
        super(context, true);
        this.uri="Wall/SW/Rule/List";
    }

    public void setData(String VipId) {
        try {
            data.put("VipId", VipId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i("test","规则 列表:"+rJson);
        Gson gson=new Gson();
        ResultRuleListBean bean=gson.fromJson(rJson,ResultRuleListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }

}

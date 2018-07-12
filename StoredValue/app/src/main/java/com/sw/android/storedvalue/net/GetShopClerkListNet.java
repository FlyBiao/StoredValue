package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultShopClerkBean;
import com.sw.android.storedvalue.bean.ResultVipInfoBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.ACache;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

;

/**
 * Author FGB
 * Description 获取店铺店员列表
 * Created at 2017/7/19 17:56
 * Version 1.0
 */
public class GetShopClerkListNet extends BaseValueNet {
    private ACache aCache;
    public GetShopClerkListNet(Context context,ACache aCache) {
        super(context, true);
        this.uri="User/SW/Counselor/GetList";
        this.aCache=aCache;
    }

    public void setData(int PageIndex) {
        try {
            data.put("PageIndex", PageIndex);
            data.put("PageSize", 50);
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
        aCache.put("GetShopClerk",rJson);
        ResultShopClerkBean bean=gson.fromJson(rJson,ResultShopClerkBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }

}

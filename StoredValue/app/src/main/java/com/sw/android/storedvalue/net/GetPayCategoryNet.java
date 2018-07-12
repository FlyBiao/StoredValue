package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultAddTradeBean;
import com.sw.android.storedvalue.bean.ResultPayCategoryBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.ACache;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/9/12 11:50
 * Version 1.0
 */
public class GetPayCategoryNet  extends BaseValueNet {
    private ACache aCache;
    public GetPayCategoryNet(Context context,ACache aCache) {
        super(context, true);
        this.uri="Order/Sw/Retail/PayCategoryList";
        this.aCache=aCache;
    }

    public void setData() {
        try {
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
        aCache.put("GetPayCategory",rJson);
        ResultPayCategoryBean bean= gson.fromJson(rJson,ResultPayCategoryBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }
}

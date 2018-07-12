package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;
;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultVipInfoBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取会员储值账户
 * Created at 2017/7/19 17:56
 * Version 1.0
 */
public class GetOneNet extends BaseValueNet {
    public GetOneNet(Context context) {
        super(context, true);
        this.uri="Wall/SW/Vip/GetOne";
    }

    public void setData(String VipMobile) {
        try {
            data.put("VipMobile", VipMobile);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i("test","会员信息"+rJson);
        Gson gson=new Gson();
        ResultVipInfoBean bean=gson.fromJson(rJson,ResultVipInfoBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }

}

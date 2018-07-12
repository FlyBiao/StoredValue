package com.sw.android.storedvalue.net;

import android.content.Context;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ActiveBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.global.Urls;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/8/2 10:22
 * Version 1.0
 */

public class PosActiveNet extends BaseValueNet {
    public PosActiveNet(Context context) {
        super(context, true);
        this.uri= "User/Sw/Account/Active";
    }

    public void setData(String EnCode,String Account,String Password){
        try{
            data.put("EnCode",EnCode);
            data.put("Account",Account);
            data.put("Password",Password);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }

        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ActiveBean bean=gson.fromJson(rJson,ActiveBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}

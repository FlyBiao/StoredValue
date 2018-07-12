package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.UserBean;
import com.sw.android.storedvalue.global.Urls;
import com.sw.android.storedvalue.net.base.BaseValueNet;

import org.greenrobot.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/8/2 9:56
 * Version 1.0
 */

public class LoginNet extends BaseValueNet{
    public LoginNet(Context context) {
        super(context, true);
        this.uri= "User/Sw/Account/Login";
    }

    public void setData(String Account,String Password){
        try{
            data.put("Account",Account);
            data.put("Password",Password);

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
//        Log.i("POSBugInfo",rJson);
        Gson gson=new Gson();
        UserBean bean=gson.fromJson(rJson,UserBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i("POSBugInfo",err);
    }
}

package com.sw.android.storedvalue.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.base.BaseActivity;
import com.sw.android.storedvalue.bean.PosDeviceInfo;
import com.sw.android.storedvalue.bean.ResultVipInfoBean;
import com.sw.android.storedvalue.bean.UserBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.LoginNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;
import com.sw.android.storedvalue.utils.CheckUtil;
import com.sw.android.storedvalue.utils.MD5;
import com.sw.android.storedvalue.utils.Skip;
import com.sw.android.storedvalue.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：登录页面
 * 创建日期：2016/10/09
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.et_phone_num)
    private EditText et_phone_num;
    @ViewInject(R.id.et_password)
    private EditText et_password;

    private String enCode;//pos设备en号
    private String shopName;//店铺名称
    private String mCode;//pos店铺mCode

    private String account;
    private String pwd;

    private LoginNet loginNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDeviceInfo();

        //通过EventBus订阅事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //如果用户已经登录 直接跳转到主页
        if (prefs.getBoolean(Constant.IS_LOGIN) )
            Skip.mNext(mActivity, SummaryActivity.class, true);
//        Skip.mNext(mActivity,TestActivity.class, true);
    }

    @OnClick(R.id.bt_login)
    public void login(View v){
        switch (v.getId()){
            case R.id.bt_login:
                account = et_phone_num.getText().toString().trim();
                pwd = et_password.getText().toString().trim();
                //手机号格式及为空判断Account //密码格式及为空判断Password
                if(CheckUtil.phoneVerify(mContext,account)){
                    if (CheckUtil.passwordVerify(mContext, pwd)) {
                        loginNet=new LoginNet(mContext);
                        loginNet.setData(account,new MD5().toMD5(pwd));
                    }
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(UserBean bean) {
        if(bean.isSuccess()!=false){
            prefs.putBoolean(Constant.IS_LOGIN, bean.isSuccess());
            AbPrefsUtil.getInstance().putString(Constant.SPF_TOKEN,bean.TModel.UserTicket);
            AbPrefsUtil.getInstance().putString(Constant.SPF_TIME,String.valueOf(System.currentTimeMillis()));
            // 登录成功后跳转到CashierHomeActivity收银主页
            Intent intent = new Intent(LoginActivity.this,SummaryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            LoginActivity.this.finish();
        }else{
            ToastUtils.getToast(mContext,"Msg:"+bean.getMessage());
        }
    }


    /**
     * 获取旺POS设备信息
     */
    public void getDeviceInfo(){
        String deviceInfoJson = WeiposImpl.as().getDeviceInfo();
        PosDeviceInfo deviceInfo=gson.fromJson(deviceInfoJson, PosDeviceInfo.class);
        enCode=deviceInfo.getEn().replaceAll("\\s","");//pos设备EN号
        mCode=deviceInfo.getMcode();
        prefs.putString("enCode",enCode);
    }


}

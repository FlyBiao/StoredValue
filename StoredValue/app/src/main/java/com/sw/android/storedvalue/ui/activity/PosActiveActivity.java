package com.sw.android.storedvalue.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.base.BaseActivity;
import com.sw.android.storedvalue.bean.ActiveBean;
import com.sw.android.storedvalue.bean.PosDeviceInfo;
import com.sw.android.storedvalue.bean.ResultTradeListBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.global.Urls;
import com.sw.android.storedvalue.net.PosActiveNet;
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
 * 描    述：pos激活页面
 * 创建日期：2016/11/7 10:42
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class PosActiveActivity extends BaseActivity {

    private Button bt_active;
    private EditText et_phone_num,et_password,et_encode;

    private String enCode;//pos设备en号
    private String shopName;//店铺名称
    private String mCode;//pos店铺mCode

    private String account;//账号
    private String pwd;//密码

    private PosActiveNet posActiveNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_active);
        //通过EventBus订阅事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        getDeviceInfo();
        initView();
    }

    /**
     * 激活pos方法
     */
    public void activePos(){

        account = et_phone_num.getText().toString().trim();
        pwd = et_password.getText().toString().trim();

        //手机号格式及为空判断Account //密码格式及为空判断Password
        if(CheckUtil.phoneVerify(mContext,account)){
            if (CheckUtil.passwordVerify(mContext, pwd)) {
                if(!TextUtils.isEmpty(et_encode.getText().toString())){
                    posActiveNet=new PosActiveNet(mContext);
                    posActiveNet.setData(et_encode.getText().toString(),account,new MD5().toMD5(pwd));
                }else{
                    ToastUtils.show(mContext,"请输入设备EN号！", ToastUtils.CENTER);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ActiveBean bean) {
        if(bean.IsSuccess!=false){//激活成功
            if(bean.TModel!=null){
                AbPrefsUtil.getInstance().putString(Constant.SPF_TOKEN,bean.TModel.getUserTicket());
                prefs.putBoolean(Constant.IS_LOGIN, bean.isSuccess());
                AbPrefsUtil.getInstance().putString(Constant.SPF_TIME,String.valueOf(System.currentTimeMillis()));
                ToastUtils.show(mContext, "恭喜你！POS激活成功。", ToastUtils.CENTER);
                //跳到收银主页
                Skip.mNext(mActivity, SummaryActivity.class);

            }else{
                ToastUtils.show("用户票据为空！");
            }

        }else{
            ToastUtils.show(bean.getMessage());
        }
    }

    /**
     * 初始化视图控件
     */
    public void initView(){
        et_encode= (EditText) findViewById(R.id.et_encode);
        et_password= (EditText) findViewById(R.id.et_password);
        et_phone_num= (EditText) findViewById(R.id.et_phone_num);
        bt_active= (Button) findViewById(R.id.bt_active);
        et_encode.setFocusable(false);et_encode.setFocusableInTouchMode(false);//设置不可编辑状态；
        //设置设备EN号
        et_encode.setText(enCode);

        bt_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //激活pos
                activePos();

            }
        });
    }

    /**
     * 获取旺POS设备信息
     */
    public void getDeviceInfo(){
        String deviceInfoJson = WeiposImpl.as().getDeviceInfo();
        PosDeviceInfo deviceInfo=gson.fromJson(deviceInfoJson, PosDeviceInfo.class);
        enCode=deviceInfo.getEn().replaceAll("\\s","");//pos设备EN号
        mCode=deviceInfo.getMcode();
    }
}

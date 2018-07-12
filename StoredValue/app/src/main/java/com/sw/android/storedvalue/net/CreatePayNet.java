package com.sw.android.storedvalue.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.sw.android.storedvalue.bean.ResultCreatePayBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.net.base.BaseValueNet;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

/**
 * Author FGB
 * Description 创建支付流水
 * Created at 2017/7/19 17:56
 * Version 1.0
 */
public class CreatePayNet extends BaseValueNet {
    public CreatePayNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Retail/CreatePay";
    }

    public void setData(String SheetId,double Payment,int PayType,String PayNo,String TraceAudit,String BankNo,String Remark,String VoucherRecord,String TId,String BankName,int SheetCategory){
        try {
            data.put("SheetId",SheetId);//业务ID
            data.put("Payment",Payment);//支付金额
            data.put("PayType",PayType);//支付类型
            data.put("CurrencyType",0);//币种【默认0】
            data.put("PayNo",PayNo);//支付流水号
            data.put("TraceAudit",TraceAudit);//支付参考号
            data.put("BankNo",BankNo);//银行卡号
            data.put("Remark",Remark);//备注
            data.put("EnCode",AbPrefsUtil.getInstance().getString("enCode"));//设备EN号
            data.put("VoucherRecord",VoucherRecord);//凭证记录
            data.put("TId",TId);//店铺ID
            data.put("SheetCategory",SheetCategory);//0, 零售单 1, 储值 2,独立收银
            data.put("BankName",BankName);//交易银行名称
            data.put("CardCategory",1);//交易银行名称
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"支付流水:"+rJson);
        Gson gson=new Gson();
        ResultCreatePayBean lbean = gson.fromJson(rJson, ResultCreatePayBean.class);
        EventBus.getDefault().post(lbean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
    }

}

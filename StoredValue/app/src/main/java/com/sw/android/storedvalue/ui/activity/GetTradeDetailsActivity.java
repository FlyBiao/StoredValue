package com.sw.android.storedvalue.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.base.BaseActivity;
import com.sw.android.storedvalue.bean.ResultOrderNoBean;
import com.sw.android.storedvalue.bean.printer.StoredValueLatticePrinterBean;
import com.sw.android.storedvalue.bean.printer.StoredValueLatticePrinterInfoBean;
import com.sw.android.storedvalue.net.GetOneByOrderNoNet;
import com.sw.android.storedvalue.utils.DecimalFormatUtils;
import com.sw.android.storedvalue.utils.SingleCashierPrinterTools;
import com.sw.android.storedvalue.utils.Skip;
import com.sw.android.storedvalue.utils.ToastUtils;
import com.zhl.cbdialog.CBDialogBuilder;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.LatticePrinter;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * 充值记录
 */
public class GetTradeDetailsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private LinearLayout ll_Printer;

    private TextView tv_shop;
    private TextView tv_order_no;
    private TextView tv_counterName;
    private TextView tv_shopClerkName;
    private TextView tv_create_time;
    private TextView tv_active;
    private TextView tv_Amount;
    private TextView tv_GivenAmount;
    private TextView tv_payType;
    private TextView tv_vip_id;
    private TextView tv_traceAuditNumber;
    private TextView tv_UsableBalance;
    private TextView tv_GivenBalance;
    private TextView tv_TotalBalance;

    //打印===========
    private LatticePrinter latticePrinter;// 点阵打印
    private StoredValueLatticePrinterBean latticePrinterBean;

    private String Id;
    private GetOneByOrderNoNet getOneByOrderNoNet;

    //3s后执行代码
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //调用pos打印机
            setLatticePrinter();
            Skip.mNext(mActivity,GetTradeListActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_trade_details);
        //通过EventBus订阅事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ResultOrderNoBean bean) {
        if(bean.isSuccess()!=false){
            if(bean.TModel!=null){
                tv_order_no.setText(bean.TModel.getOrderNo());
                tv_create_time.setText(bean.TModel.getCreateTime());
                tv_active.setText(bean.TModel.getRuleName());
                tv_Amount.setText(bean.TModel.getAmount()+"");
                tv_GivenAmount.setText(bean.TModel.getDonationAmount()+"");
                tv_vip_id.setText(bean.TModel.getCardNo());
                tv_traceAuditNumber.setText(bean.TModel.getOrderNo());
                tv_UsableBalance.setText(DecimalFormatUtils.decimalFormatRounds(bean.TModel.getTotalAmount())+"");
                tv_GivenBalance.setText(DecimalFormatUtils.decimalFormatRounds(bean.TModel.getTotalDonationAmount())+"");
                tv_TotalBalance.setText(DecimalFormatUtils.decimalFormatRounds(bean.TModel.getTotalDonationAmount()+bean.TModel.getTotalAmount())+"");

                switch (bean.TModel.getPayMethod())
                {
                    case 2:
                        tv_payType.setText("微信");
                        break;
                    case 3:
                        tv_payType.setText("支付宝");
                        break;
                    case 4:
                        tv_payType.setText("银联刷卡");
                        break;
                    case 5:
                        tv_payType.setText("现金");
                        break;
                }
            }
        }else{
            ToastUtils.getLongToast(mContext,"获取订单详情失败！"+bean.getMessage());
        }
    }

    private void initData() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            Id=bundle.getString("Id");
        }
        if(prefs.getString("ShopArea")!=null){
            tv_shop.setText(prefs.getString("ShopArea")+prefs.getString("shopNameNick"));
        }else{
            tv_shop.setText(prefs.getString("shopNameNick"));
        }

        tv_counterName.setText(prefs.getString("shopNameNick"));
        tv_shopClerkName.setText(prefs.getString("userName"));

        getOneByOrderNoNet=new GetOneByOrderNoNet(mContext);
        getOneByOrderNoNet.setData(Id);
    }

    private void initView() {
        ll_Printer=(LinearLayout) findViewById(R.id.ll_Printer);
        ll_Printer.setOnClickListener(this);
        ll_back=(LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);

        tv_shop=(TextView) findViewById(R.id.tv_shop);
        tv_order_no=(TextView) findViewById(R.id.tv_order_no);
        tv_counterName=(TextView) findViewById(R.id.tv_counterName);
        tv_shopClerkName=(TextView) findViewById(R.id.tv_shopClerkName);
        tv_create_time=(TextView) findViewById(R.id.tv_create_time);
        tv_active=(TextView) findViewById(R.id.tv_active);
        tv_Amount=(TextView) findViewById(R.id.tv_Amount);
        tv_GivenAmount=(TextView) findViewById(R.id.tv_GivenAmount);
        tv_payType=(TextView) findViewById(R.id.tv_payType);
        tv_vip_id=(TextView) findViewById(R.id.tv_vip_id);
        tv_traceAuditNumber=(TextView) findViewById(R.id.tv_traceAuditNumber);
        tv_UsableBalance=(TextView) findViewById(R.id.tv_UsableBalance);
        tv_GivenBalance=(TextView) findViewById(R.id.tv_GivenBalance);
        tv_TotalBalance=(TextView) findViewById(R.id.tv_TotalBalance);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_Printer:
                isPrinter();
                break;
        }
    }

    public void setLatticePrinter(){
        try {
            // 设备可能没有打印机，open会抛异常
            latticePrinter = WeiposImpl.as().openLatticePrinter();
        } catch (Exception e) {
            // TODO: handle exception
        }
        //点阵打印
        if (latticePrinter == null) {
            Toast.makeText(mContext, "尚未初始化点阵打印sdk，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }else{
            // 打印内容赋值
            latticePrinter.setOnEventListener(new IPrint.OnEventListener() {

                @Override
                public void onEvent(final int what, String in) {
                    final String info = in;
                    // 回调函数中不能做UI操作，所以可以使用runOnUiThread函数来包装一下代码块
                    runOnUiThread(new Runnable() {
                        public void run() {
                            final String message = SingleCashierPrinterTools.getPrintErrorInfo(what, info);
                            if (message == null || message.length() < 1) {
                                return;
                            }
                            showResultInfo("打印", "打印结果信息", message);
                        }
                    });
                }
            });

            latticePrinterBean=new StoredValueLatticePrinterBean();
            if(prefs.getString("ShopAddress")!=null){
                latticePrinterBean.setCounterName(prefs.getString("ShopAddress"));
                latticePrinterBean.setShopName(prefs.getString("ShopArea")+prefs.getString("shopNameNick"));
            }else{
                latticePrinterBean.setCounterName(prefs.getString("shopNameNick"));
                latticePrinterBean.setShopName(prefs.getString("shopNameNick"));
            }
            latticePrinterBean.setShopClerkName(prefs.getString("userName"));
            latticePrinterBean.setTitle(tv_active.getText().toString());
            latticePrinterBean.setAmount(Double.parseDouble(tv_Amount.getText().toString()));
            latticePrinterBean.setOrderId(tv_order_no.getText().toString());
            latticePrinterBean.setTraceAuditNumber(tv_traceAuditNumber.getText().toString());
            latticePrinterBean.setVipId(tv_vip_id.getText().toString());
            latticePrinterBean.setGivenAmount(Double.parseDouble(tv_GivenAmount.getText().toString()));
            latticePrinterBean.setUsableBalance(Double.parseDouble(tv_UsableBalance.getText().toString()));
            latticePrinterBean.setGivenBalance(Double.parseDouble(tv_GivenBalance.getText().toString()));
            latticePrinterBean.setTotalBalance(Double.parseDouble(tv_TotalBalance.getText().toString()));
            latticePrinterBean.setPayType("微信支付");

            StoredValueLatticePrinterInfoBean.printLattice(latticePrinter,latticePrinterBean);
        }
    }

    private void isPrinter(){
        new CBDialogBuilder(mContext)
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setTitle("充值成功")
                .setMessage("是否需要打印充值票据？")
                .setConfirmButtonText("确定")
                .setCancelButtonText("取消")
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setButtonClickListener(true, new CBDialogBuilder.onDialogbtnClickListener() {
                    @Override
                    public void onDialogbtnClick(Context context, Dialog dialog, int whichBtn) {
                        switch (whichBtn) {
                            case BUTTON_CONFIRM:
                                ToastUtils.getLongToast(mContext,"正在打印票据......");
                                setLatticePrinter();
                                Runnable mRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        mHandler.sendEmptyMessage(1);
                                    }
                                };
                                mHandler .postDelayed(mRunnable, 3000); // 在Handler中执行子线程并延迟3s。
                                break;
                            case BUTTON_CANCEL:
                                ToastUtils.show("已取消打印票据！");
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create().show();
    }

    /**
     * pos打印显示结果信息
     * @param operInfo
     * @param titleHeader
     * @param info
     */
    private void showResultInfo(String operInfo, String titleHeader, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(titleHeader + ":" + info);
        builder.setTitle(operInfo);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
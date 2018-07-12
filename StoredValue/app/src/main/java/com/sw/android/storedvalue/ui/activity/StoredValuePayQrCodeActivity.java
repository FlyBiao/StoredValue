package com.sw.android.storedvalue.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.base.BaseActivity;
import com.sw.android.storedvalue.bean.PayLogBean;
import com.sw.android.storedvalue.bean.PosBean;
import com.sw.android.storedvalue.bean.ResultAddTradeBean;
import com.sw.android.storedvalue.bean.ResultCreatePayBean;
import com.sw.android.storedvalue.bean.printer.StoredValueLatticePrinterBean;
import com.sw.android.storedvalue.bean.printer.StoredValueLatticePrinterInfoBean;
import com.sw.android.storedvalue.db.PosSqliteDatabaseUtils;
import com.sw.android.storedvalue.db.bean.PosPayBean;
import com.sw.android.storedvalue.global.Gloabl;
import com.sw.android.storedvalue.net.AddTradeNet;
import com.sw.android.storedvalue.net.CreatePayNet;
import com.sw.android.storedvalue.net.PosPayLogNet;
import com.sw.android.storedvalue.utils.AbDateUtil;
import com.sw.android.storedvalue.utils.CreateQRImageUtils;
import com.sw.android.storedvalue.utils.SingleCashierPrinterTools;
import com.sw.android.storedvalue.utils.Skip;
import com.sw.android.storedvalue.utils.ToastUtils;
import com.wangpos.pay.UnionPay.PosConfig;
import com.wangpos.poscore.IPosCallBack;
import com.wangpos.poscore.PosCore;
import com.wangpos.poscore.impl.PosCoreFactory;
import com.zhl.cbdialog.CBDialogBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.LatticePrinter;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：订单收款二维码
 * 创建日期：2016/10/27
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class StoredValuePayQrCodeActivity extends BaseActivity {

    private ImageView iv_order_pay_code_img;
    private EditText ed_order_qrcode_show_msg;
    private LinearLayout ll_order_qrcode_pay_back;
    private TextView tv_pay_amount;

    private PosCore pCore;
    private PosCallBack callBack;
    private String refNo;

    private int payType;
    private int shopClerkId;
    private Integer money;
    private double payMoney;
    private String orderNo;
    private String userName;
    private String shopClerkName;
    private String shopNameNick;
    private String title;
    private String vipMobile;
    private String vipId;
    private int shopId;
    private int id;//规则id
    private String strJson;

    private CreatePayNet createPayNet;

    private double DonationAmount;
    private double UsableBalance;//可用余额
    private double GivenBalance;//赠送余额
    private double TotalBalance;//总余额

    private int payOrderStauts=1;//支付订单状态【0:未知,1:订单需要继续查询,用户还未完成支付;2:订单已经关闭;3:订单完成支付】

    private AddTradeNet addTradeNet;
    private PosPayLogNet payLogNet;

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(payOrderStauts==3){//订单完成支付
                try {
                    PosPayBean bean=new PosPayBean(shopId+"",shopNameNick,shopClerkName,orderNo,payMoney+"",refNo,payType+"","","","3", AbDateUtil.getCurrentDate(),"订单完成支付",prefs.getString("enCode"),"","true");
                    insertData(bean);
                    Log.i("jsonDugInfo", "停止查询，跳转首页并且显示支付结果。");
                    handler.removeCallbacks(runnable);

                    PayLogBean logBean=new PayLogBean();
                    logBean.setRemark("POS储值");
                    logBean.setPayType(payType);
                    logBean.setEnCode(prefs.getString("enCode"));
                    logBean.setPayAmount(payMoney);
                    logBean.setVipId(Integer.parseInt(vipId));
                    logBean.setShopId(shopId);
                    logBean.setReferenceNumber(refNo);
                    logBean.setTraceAuditNumber(refNo);
                    logBean.setWeiXinPayrefNo(refNo);
                    logBean.setAliPayrefNo(refNo);
                    logBean.setCashierId(shopClerkId);
                    logBean.setRuleId(id);
                    strJson=gson.toJson(logBean);

                    addTradeNet=new AddTradeNet(mContext);
                    addTradeNet.setData(Integer.parseInt(vipId),shopId,refNo,payType,payMoney,id,prefs.getString("enCode"),shopClerkId);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else if(payOrderStauts==2){//订单已经关闭
                PosPayBean bean=new PosPayBean(shopId+"",shopNameNick,shopClerkName,orderNo,payMoney+"",refNo,payType+"","","","2", AbDateUtil.getCurrentDate(),"订单已经关闭",prefs.getString("enCode"),"","false");
                insertData(bean);
                Log.i("jsonDugInfo", "订单已经关闭。");
                //停止计时器
                handler.removeCallbacks(runnable);
            }else if(payOrderStauts==1){//订单需要继续查询
                PosPayBean bean=new PosPayBean(shopId+"",shopNameNick,shopClerkName,orderNo,payMoney+"",refNo,payType+"","","","1", AbDateUtil.getCurrentDate(),"订单需要继续查询..",prefs.getString("enCode"),"","false");
                insertData(bean);
                handler.removeCallbacks(runnable);
                Log.i("jsonDugInfo", "订单需要继续查询。");
                handler.removeCallbacks(runnable);
                queryPayResult(refNo);//调用执行查询支付结果
                //每隔一秒刷新一次
                handler.postDelayed(this, 2000);
            }else{
                PosPayBean bean=new PosPayBean(shopId+"",shopNameNick,shopClerkName,orderNo,payMoney+"",refNo,payType+"","","","", AbDateUtil.getCurrentDate(),"订单未知....",prefs.getString("enCode"),"","false");
                insertData(bean);
                Log.i("jsonDugInfo", "订单未知。");
                //停止计时器
                handler.removeCallbacks(runnable);
            }
        }
    };

    //打印===========
    private LatticePrinter latticePrinter;// 点阵打印
    private StoredValueLatticePrinterBean latticePrinterBean;

    //3s后执行代码
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //调用pos打印机
            setLatticePrinter();
            //跳回会员首页
            bundle.putString("vipMobile",vipMobile);
            Skip.mNextFroData(mActivity,SummaryActivity.class,bundle,true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order_pay_qr_code);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            title=bundle.getString("title");
            vipMobile=bundle.getString("vipMobile");
            payType=bundle.getInt("payType");
            shopClerkId=bundle.getInt("shopClerkId");
            payMoney=bundle.getDouble("PayMoney");
            orderNo=bundle.getString("OrderNo");
            shopNameNick=bundle.getString("shopNameNick");
            shopClerkName=bundle.getString("shopClerkName");
            userName=bundle.getString("userName");
            shopId=bundle.getInt("shopId");
            vipId=bundle.getString("vipId");
            id=bundle.getInt("id");

            DonationAmount=bundle.getDouble("DonationAmount");
            TotalBalance=bundle.getDouble("TotalBalance");
            UsableBalance=bundle.getDouble("UsableBalance");
            GivenBalance=bundle.getDouble("GivenBalance");
        }

        //通过EventBus订阅事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initPosCore();
        initView();
        initData();

        //启动计时器 每1秒执行一次runnable
        handler.postDelayed(runnable,2000);

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ResultCreatePayBean bean) {
        if(bean.isSuccess()!=true){
            try {
                payLogNet=new PosPayLogNet(mContext);
                if(payType==1){//积分支付退款回调

                }else if(payType==2){//微信支付退款回调
                    PosPayBean bean1=new PosPayBean(prefs.getString("userShopId"),shopNameNick,userName,orderNo,payMoney+"",refNo,"2","微信支付流水失败","","3",AbDateUtil.getCurrentDate(),"进入支付流水微信支付回调",prefs.getString("enCode"),"","false");
                    insertData(bean1);
                    payLogNet.setData("微信支付",strJson,bean.getMessage());
                }else if(payType==3){//支付宝退款回调
                    PosPayBean bean1=new PosPayBean(prefs.getString("userShopId"),shopNameNick,userName,orderNo,payMoney+"",refNo,"3","支付宝流水失败","","3",AbDateUtil.getCurrentDate(),"进入支付流水支付宝回调",prefs.getString("enCode"),"","false");
                    insertData(bean1);
                    payLogNet.setData("支付宝支付",strJson,bean.getMessage());
                }else if(payType==4){//银联支付退款回调
                    payLogNet.setData("银联支付",strJson,bean.getMessage());
                }else if(payType==5){//现金支付退款回调
                    payLogNet.setData("现金支付",strJson,bean.getMessage());
                }else{
                    payLogNet.setData("",strJson,bean.getMessage());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //接收后台接口支付成功消息
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ResultAddTradeBean bean) {
        if(bean.isSuccess()!=false){
            createPayNet=new CreatePayNet(mContext);

            if(payType==2){
                createPayNet.setData(bean.getTModel()+"",payMoney,payType,refNo,refNo,refNo,"POS储值",refNo,shopId+"","微信",1);
            }else if(payType==3){
                createPayNet.setData(bean.getTModel()+"",payMoney,payType,refNo,refNo,refNo,"POS储值",refNo,shopId+"","支付宝",1);
            }
            isPrinter();
        }else{
            ToastUtils.getLongToast(mContext,"Message:"+bean.getMessage());
            payLogNet=new PosPayLogNet(mContext);
            if(payType==2){
                PosPayBean bean1=new PosPayBean(prefs.getString("userShopId"),shopNameNick,userName,orderNo,payMoney+"",refNo,"2","微信支付","","3",AbDateUtil.getCurrentDate(),"进入支付流水微信支付回调",prefs.getString("enCode"),"","false");
                insertData(bean1);
                payLogNet.setData("PayFromStore",strJson,"微信支付");
            }else if (payType==3){
                PosPayBean bean1=new PosPayBean(prefs.getString("userShopId"),shopNameNick,userName,orderNo,payMoney+"",refNo,"3","支付宝","","3",AbDateUtil.getCurrentDate(),"进入支付流水支付宝回调",prefs.getString("enCode"),"","false");
                insertData(bean1);
                payLogNet.setData("PayFromStore",strJson,"支付宝支付");
            }else{
                payLogNet.setData("PayFromStore",strJson,"其他支付");
            }
        }
    }

    /**
     * 查询支付结果
     */
    public void queryPayResult(final String refNos){
        if(refNos!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PosCore.RXiaoFei_WX_ZFB chaxun_wx_zfb = pCore.chaXun_WX_ZFB(refNos, callBack);
                        payOrderStauts=chaxun_wx_zfb.orderStauts;
                        PosPayBean bean=new PosPayBean(shopId+"",shopNameNick,shopClerkName,orderNo,payMoney+"",chaxun_wx_zfb.exInfo.get(Gloabl.thirdSerialNo)+"",payType+"","","",chaxun_wx_zfb.orderStauts+"", AbDateUtil.getCurrentDate(),"订单未知....",prefs.getString("enCode"),"","false");
                        insertData(bean);
                        Log.d("jsonDugInfo","查询二维码支付状态"+"订单状态:" + chaxun_wx_zfb.orderStauts + "\n" + "第三方订单号:" + chaxun_wx_zfb.exInfo.get(Gloabl.thirdSerialNo));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("jsonDugInfo","QUERY_ERROR:"+e.getLocalizedMessage());
                    showMsg(e.getLocalizedMessage());
                    }
                }
            }).start();
        }
    }

    public void initData(){
        money=(int)(payMoney*100);
        if(payType==2){//微信
            initPayQRCode(money+"","1");
        }else{//支付宝
            initPayQRCode(money+"","2");
        }
    }

    public void initView(){
        iv_order_pay_code_img= (ImageView) findViewById(R.id.iv_order_pay_code_img);
        ll_order_qrcode_pay_back= (LinearLayout) findViewById(R.id.ll_order_qrcode_pay_back);
        ed_order_qrcode_show_msg= (EditText) findViewById(R.id.ed_order_qrcode_show_msg);
        tv_pay_amount=(TextView) findViewById(R.id.tv_pay_amount);

        tv_pay_amount.setText("￥"+payMoney);

        ll_order_qrcode_pay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止计时器
                handler.removeCallbacks(runnable);
                bundle.putString("vipMobile",vipMobile);
                Skip.mNextFroData(mActivity,VipInfoActivity.class,bundle,true);
            }
        });
    }

    /**
     * 初始化支付二维码
     * @param amount 支付金额
     * @param payType 支付方式   1：微信，2：支付宝
     */
    public void initPayQRCode(final String amount,final String payType){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PosCore.RXiaoFei_WX_ZFB rXiaoFei_wx_zfb = pCore.xiaoFei_WX_ZFB(amount, payType, callBack);
                    refNo = rXiaoFei_wx_zfb.retrievalReferenceNumber;
                    showMsg("扫描以下付款二维码进行支付");
                    //直接发布
                    PosBean posBean=new PosBean();
                    posBean.PayQrCode=rXiaoFei_wx_zfb.primaryAccountNumber;
                    EventBus.getDefault().post(posBean);

                } catch (Exception e) {
                    e.printStackTrace();
                    showMsg(e.getLocalizedMessage());
                }
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(PosBean bean) {
        try {
            if (bean.PayQrCode!=null) {

                CreateQRImageUtils createQRImageUtils=new CreateQRImageUtils();
                iv_order_pay_code_img.setImageBitmap(createQRImageUtils.createQRImage(bean.PayQrCode, 650, 650));

                //注册广播
                registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

            } else {
                Toast.makeText(StoredValuePayQrCodeActivity.this, "请输入要生成二维码的字符串",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化CoreApp连接对象
     *
     * @return
     */
    private void initPosCore() {
        if (pCore == null) {
            // SocketCommClient sc = new SocketCommClient(ADDR,this);

            // 配置数据为开发阶段的数据
            HashMap<String, String> init_params = new HashMap<>();

            // 渠道ID和主密钥索引,任意传入1个,则可以指定主密钥索引,如果都不传 默认为渠道ID为6669 自动分配终端主密钥索引
//            init_params.put(PosConfig.Name_EX + "1052", "1012");// 渠道号  [华势暂时使用广东银联的渠道ID来获取商户号和终端号, 2个插件共用同一个密钥索引]
            init_params.put(PosConfig.Name_EX + "1053", "CoreApp签购单台头");// 签购单小票台头

            init_params.put(PosConfig.Name_EX + "1100", "cn.weipass.cashier");// 核心APP 包名
            init_params.put(PosConfig.Name_EX + "1101", "com.wangpos.cashiercoreapp.services.CoreAppService");// 核心APP 类名
//            init_params.put(PosConfig.Name_EX + "1600", "这是小票:${myOrderNo}");
            init_params.put(PosConfig.Name_EX + "1600_1", "这是第一联的小票:${1D:${myOrderNo}}");
            init_params.put(PosConfig.Name_EX + "1600_2", "这是第二联的小票:${2D:${myOrderNo}}");

            init_params.put(PosConfig.Name_EX + "1091", "0");// 交易成功，就删除冲正文件 不以小票打印是否成功 为判断点 1.不以小票打印成功为判断点 0.以小票打印成功为判断点
//            init_params.put(PosConfig.Name_EX + "1054", "0");// 是否打印小票  1，可以打 0，不打
//            init_params.put(PosConfig.Name_EX + "1087", "0");//是否 关闭插卡 1.开启插卡 0.关闭插卡
            init_params.put(PosConfig.Name_EX + "1089", "1");//是否 需要获取 已开通的支付方式 1.获取 0.不获取
            init_params.put(PosConfig.Name_EX + "1085", "1");//是否支持TC校验，批上送 1，是 0，不是
            init_params.put(PosConfig.Name_EX + "1086", "1");//是否启动 电子签名 1.是 0 不是

            init_params.put(PosConfig.Name_MerchantName, "coreApp");

            pCore = PosCoreFactory.newInstance(this, init_params);
            callBack = new PosCallBack(pCore);
        }

    }

    class PosCallBack implements IPosCallBack {
        private final PosCore core;

        PosCallBack(PosCore core) {
            this.core = core;
        }

        @Override
        public void onInfo(String s) {
            showMsg(s);
        }

        @Override
        public void onEvent(int eventID, Object[] params) throws Exception {
            switch (eventID) {
                case EVENT_Task_start: {
                    showMsg("任务进程开始执行");
                    break;
                }
                case EVENT_Task_end: {
                    showMsg("任务进程执行结束");
                    break;
                }

                case EVENT_Comm_start: {
                    showMsg("开始网络通信");
                    break;
                }
                case EVENT_Comm_end: {
                    showMsg("网络通信完成");
                    break;
                }
                case EVENT_DownloadPlugin_start: {
                    showMsg("开始下载插件");
                    break;
                }
                case EVENT_DownloadPlugin_end: {
                    showMsg("插件下载完成");
                    break;
                }
                case EVENT_InstallPlugin_start: {
                    showMsg("开始安装插件");
                    break;
                }
                case EVENT_InstallPlugin_end: {
                    showMsg("插件安装完成");
                    break;
                }
                case EVENT_RunPlugin_start: {
                    showMsg("开始启动插件");
                    break;
                }
                case EVENT_RunPlugin_end: {
                    showMsg("插件启动完成");
                    break;
                }

                default: {
                    showMsg("Event:" + eventID);
                    break;
                }
            }

        }
    }

    private void showMsg(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ed_order_qrcode_show_msg.setText(msg);
            }
        });
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
                                Skip.mNextFroData(mActivity,SummaryActivity.class,bundle,true);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create().show();
    }

    /**
     * 设置收银点阵打印方法
     */
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
            if(shopClerkName!=null && !"".equals(shopClerkName)){
                latticePrinterBean.setShopClerkName(shopClerkName);
            }else{
                latticePrinterBean.setShopClerkName(prefs.getString("userName"));
            }
            latticePrinterBean.setTitle(title);
            latticePrinterBean.setAmount(payMoney);
            latticePrinterBean.setOrderId(orderNo);
            latticePrinterBean.setTraceAuditNumber(refNo);
            latticePrinterBean.setVipId(vipMobile);
            latticePrinterBean.setGivenAmount(DonationAmount);
            latticePrinterBean.setUsableBalance(UsableBalance+latticePrinterBean.getAmount());
            latticePrinterBean.setGivenBalance(GivenBalance+latticePrinterBean.getGivenAmount());
            latticePrinterBean.setTotalBalance(latticePrinterBean.getGivenBalance()+latticePrinterBean.getUsableBalance());

            if(payType==2){
                latticePrinterBean.setPayType("微信支付");
            }else if(payType==3){
                latticePrinterBean.setPayType("支付宝");
            }else if(payType==4){
                latticePrinterBean.setPayType("银联刷卡");
            }else{
                latticePrinterBean.setPayType("现金");
            }

            StoredValueLatticePrinterInfoBean.printLattice(latticePrinter,latticePrinterBean);
        }
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


    /**
     * 监听是否点击了home键将客户端推到后台
     */
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键,程序到了后台
                    //停止计时器
                    handler.removeCallbacks(runnable);
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
                    //表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };

    private void insertData(PosPayBean bean){
        PosSqliteDatabaseUtils.insterData(mContext,bean);
    }

}

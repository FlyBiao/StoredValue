package com.sw.android.storedvalue.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.adapter.PosPayCategoryAdapter;
import com.sw.android.storedvalue.base.BaseActivity;
import com.sw.android.storedvalue.base.BaseRecyclerView;
import com.sw.android.storedvalue.bean.PayLogBean;
import com.sw.android.storedvalue.bean.PosBean;
import com.sw.android.storedvalue.bean.ResultAddTradeBean;
import com.sw.android.storedvalue.bean.ResultCreatePayBean;
import com.sw.android.storedvalue.bean.ResultPayCategoryBean;
import com.sw.android.storedvalue.bean.ResultShopClerkBean;
import com.sw.android.storedvalue.bean.ShopClerkBean;
import com.sw.android.storedvalue.bean.ShopClerkListBean;
import com.sw.android.storedvalue.bean.printer.StoredValueLatticePrinterBean;
import com.sw.android.storedvalue.bean.printer.StoredValueLatticePrinterInfoBean;
import com.sw.android.storedvalue.db.PosSqliteDatabaseUtils;
import com.sw.android.storedvalue.db.bean.PosPayBean;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.listener.OnItemClickListener;
import com.sw.android.storedvalue.net.AddTradeNet;
import com.sw.android.storedvalue.net.CreatePayNet;
import com.sw.android.storedvalue.net.GetPayCategoryNet;
import com.sw.android.storedvalue.net.GetShopClerkListNet;
import com.sw.android.storedvalue.net.PosPayLogNet;
import com.sw.android.storedvalue.utils.AbDateUtil;
import com.sw.android.storedvalue.utils.InitPlay;
import com.sw.android.storedvalue.utils.RandomUtils;
import com.sw.android.storedvalue.utils.SingleCashierPrinterTools;
import com.sw.android.storedvalue.utils.Skip;
import com.sw.android.storedvalue.utils.ToastUtils;
import com.sw.android.storedvalue.view.SwipeBackLayout;
import com.wangpos.pay.UnionPay.PosConfig;
import com.wangpos.poscore.IPosCallBack;
import com.wangpos.poscore.PosCore;
import com.wangpos.poscore.impl.PosCoreFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhl.cbdialog.CBDialogBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.LatticePrinter;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * 储值支付类型
 */
public class PaymentTypeActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout llBack;
    private TextView tvAmountValue,tvTitle;
    private EditText tv_show_amount;
    private SwipeBackLayout swipeBackLayout;
    private SwipeMenuRecyclerView rvView;

    private String title;
    private String vipId;
    private double BegAmount;
    private double EndAmount;
    private double DonationAmount;
    private double UsableBalance;//可用余额
    private double GivenBalance;//赠送余额
    private double TotalBalance;//总余额
    private int shopId;
    private int id;
    private String vipMobile;
    private AddTradeNet addTradeNet;
    private String strJson;

    //收银=========
    private byte[] lock = new byte[1];
    private int EVENT_NO_PAPER = 1;
    private final int LOCK_WAIT = 0;
    private final int LOCK_CONTINUE = 1;
    //8583协议中的参考号
    private String orderNo;
    private String primaryAccountNumber;//银行卡号

    private String refNum;
    private String amount;
    private Integer money;
    private int Type;//充值类型 0:固定金额:1:固定比例 2:任意金额
    private int payType;
    private String shopNameNick="";
    private double payMoney;//订单支付价格
    private PosCore.RXiaoFei rXiaoFei;
    private PosCore pCore;
    private PosCallBack callBack;
    private int index=0;

    private int payOrderStauts=1;//支付订单状态【0:未知,1:订单需要继续查询,用户还未完成支付;2:订单已经关闭;3:订单完成支付】
    private String queryOrderNo=null;

    private GetPayCategoryNet getPayCategoryNet;
    private GetShopClerkListNet shopClerkListNet;

    //打印===========
    private LatticePrinter latticePrinter;// 点阵打印
    private StoredValueLatticePrinterBean latticePrinterBean;

    private List<ResultPayCategoryBean.PayCategoryBea> categoryBeaList;
    private PosPayCategoryAdapter posPayCategoryAdapter;

    private BaseDialog baseDialog;

    private List<Boolean> checkList = new ArrayList<Boolean>(); // 判断listview单选位置
    private List<ShopClerkBean> shopClerkBeen;

    public List<ShopClerkListBean> clerkListBeen=new ArrayList<>();

    private String shopClerkName=null;
    private int shopClerkId;

    private CreatePayNet createPayNet;
    private PosPayLogNet payLogNet;

    private MyAdapter myAdapter;

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
        setContentView(R.layout.activity_payment_type);

        //通过EventBus订阅事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            vipMobile=bundle.getString("vipMobile");
            title=bundle.getString("title");
            EndAmount=bundle.getDouble("EndAmount");
            BegAmount=bundle.getDouble("BegAmount");
            DonationAmount=bundle.getDouble("DonationAmount");
            TotalBalance=bundle.getDouble("TotalBalance");
            UsableBalance=bundle.getDouble("UsableBalance");
            GivenBalance=bundle.getDouble("GivenBalance");
            shopId=bundle.getInt("shopId");
            vipId=bundle.getString("vipId");
            id=bundle.getInt("id");
            Type=bundle.getInt("Type");

        }
        initView();
        initData();
        //初始化CoreApp连接对象
        initPosCore();
    }

    private void initData(){
        if(mCache.getAsString("GetPayCategory")!=null && !"".equals(mCache.getAsString("GetPayCategory"))){
            ResultPayCategoryBean bean= gson.fromJson(mCache.getAsString("GetPayCategory"),ResultPayCategoryBean.class);
            categoryBeaList=bean.TModel;

            posPayCategoryAdapter=new PosPayCategoryAdapter(categoryBeaList);
            posPayCategoryAdapter.setOnItemClickListener(onItemClickListener);
            rvView.setAdapter(posPayCategoryAdapter);
        }else{
            getPayCategoryNet=new GetPayCategoryNet(mContext,mCache);
            getPayCategoryNet.setData();
        }

        if(mCache.getAsString("GetShopClerk")!=null && !"".equals(mCache.getAsString("GetShopClerk"))){
            ResultShopClerkBean bean=gson.fromJson(mCache.getAsString("GetShopClerk"),ResultShopClerkBean.class);
            clerkListBeen=new ArrayList<>();
            clerkListBeen=bean.TModel;

        }else{
            shopClerkListNet=new GetShopClerkListNet(mContext,mCache);
            shopClerkListNet.setData(1);
        }

        //订单号生成规则MMddHHmm+4位随机数
        orderNo= AbDateUtil.getCurrentTimeAsNumber()+ RandomUtils.getFourRandom();

        addTradeNet=new AddTradeNet(mContext);

        tvAmountValue.setText(BegAmount+"");
        tvTitle.setText("("+title+")");
    }

    private void initView() {
        rvView= (SwipeMenuRecyclerView) findViewById(R.id.rv_view);
        BaseRecyclerView.initRecyclerView(mContext,rvView,false);

        llBack= (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
        tvAmountValue= (TextView) findViewById(R.id.tv_amount_value);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tv_show_amount= (EditText) findViewById(R.id.tv_show_amount);

        swipeBackLayout=(SwipeBackLayout)findViewById(R.id.swipe_back_layout);
        swipeBackLayout.setSwipeBackListener(new SwipeBackLayout.SwipeBackFinishActivityListener(this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(ResultPayCategoryBean bean) {
        if(bean.isSuccess()!=false){
            categoryBeaList=bean.TModel;

            posPayCategoryAdapter=new PosPayCategoryAdapter(categoryBeaList);
            posPayCategoryAdapter.setOnItemClickListener(onItemClickListener);
            rvView.setAdapter(posPayCategoryAdapter);
        }else{
//            ToastUtils.getLongToast(mContext,"Message:"+bean.getMessage());
        }
    }

    //接收店铺店员列表消息
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ResultShopClerkBean bean) {
        if(bean.isSuccess()!=false){
            if(bean.TModel!=null && bean.TModel.size()!=0){
                clerkListBeen=new ArrayList<>();
                clerkListBeen=bean.TModel;
            }
        }else{
            ToastUtils.getLongToast(mContext,"获取店员失败！"+bean.getMessage());
        }
    }

    //支付支付流水
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(ResultCreatePayBean bean) {
        if(bean.isSuccess()!=false){

        }else{
            payLogNet=new PosPayLogNet(mContext);
            if(payType==1){//积分支付退款回调

            }else if(payType==2){//微信支付退款回调
                payLogNet.setData("微信支付",strJson,bean.getMessage());
            }else if(payType==3){//支付宝退款回调
                payLogNet.setData("支付宝支付",strJson,bean.getMessage());
            }else if(payType==4){//银联支付退款回调
                PosPayBean bean1=new PosPayBean(prefs.getString("userShopId"),shopNameNick,shopClerkName,orderNo,payMoney+"",refNum,"4","银联刷卡","","3",AbDateUtil.getCurrentDate(),"银联支付支付流水..",prefs.getString("enCode"),"","false");
                insertData(bean1);
                payLogNet.setData("银联支付",strJson,bean.getMessage());
            }else if(payType==5){//现金支付退款回调
                payLogNet.setData("现金支付",strJson,bean.getMessage());
            }else{
                payLogNet.setData("",strJson,bean.getMessage());
            }
        }
    }

    //接收后台接口支付成功消息
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ResultAddTradeBean bean) {
        if(bean.isSuccess()!=false){
            if(payType==5){
                createPayNet=new CreatePayNet(mContext);
                createPayNet.setData(bean.getTModel()+"",BegAmount,5,orderNo,orderNo,orderNo,"POS储值",orderNo,shopId+"","",1);
                isPrinter();
            }else if(payType==4){
                createPayNet=new CreatePayNet(mContext);
                if(refNum!=null){
                    createPayNet.setData(bean.getTModel()+"",BegAmount,4,refNum,refNum,primaryAccountNumber,"POS储值",refNum,shopId+"","",1);
                }else{
                    createPayNet.setData(bean.getTModel()+"",BegAmount,4,orderNo,orderNo,primaryAccountNumber,"POS储值",orderNo,shopId+"","",1);
                }

//                isPrinter();
            }
        }else{
            ToastUtils.getLongToast(mContext,"Message:"+bean.getMessage());
            payLogNet=new PosPayLogNet(mContext);
            if(payType==4){
                PosPayBean bean1=new PosPayBean(prefs.getString("userShopId"),shopNameNick,shopClerkName,orderNo,payMoney+"",refNum,"4","银联刷卡充值失败","","3",AbDateUtil.getCurrentDate(),"银联支付..",prefs.getString("enCode"),"","false");
                insertData(bean1);
                payLogNet.setData("PayFromStore",strJson,"银联支付");
            }else if(payType==5){
                payLogNet.setData("PayFromStore",strJson,"现金支付");
            }else{
                payLogNet.setData("PayFromStore",strJson,"其他支付");
            }
        }
    }

    //接收银联回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(PosBean bean) {
        if(bean.ReferenceNumber!=null){
            addTradeNet=new AddTradeNet(mContext);
            addTradeNet.setData(Integer.parseInt(vipId),shopId,bean.ReferenceNumber,4,BegAmount,id,prefs.getString("enCode"),shopClerkId);
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
            if(shopClerkName!=null){
                latticePrinterBean.setShopClerkName(shopClerkName);
            }else{
                latticePrinterBean.setShopClerkName(prefs.getString("userName"));
            }
            if(prefs.getString("ShopAddress")!=null){
                latticePrinterBean.setCounterName(prefs.getString("ShopAddress"));
                latticePrinterBean.setShopName(prefs.getString("ShopArea")+prefs.getString("shopNameNick"));
            }else{
                latticePrinterBean.setCounterName(prefs.getString("shopNameNick"));
                latticePrinterBean.setShopName(prefs.getString("shopNameNick"));
            }
            latticePrinterBean.setTitle(title);
            latticePrinterBean.setAmount(BegAmount);
            latticePrinterBean.setOrderId(orderNo);
            latticePrinterBean.setTraceAuditNumber(orderNo);
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
            }else if(payType==5){
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
            public void onClick(DialogInterface dialog, int which) {dialog.dismiss();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                Skip.mBack(mActivity);
                break;
        }
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            payType=categoryBeaList.get(position).getCategoryType();
            if(payType==2 || payType==3 || payType==4 || payType==5){
                baseDialog = new BaseDialog(mContext);
                baseDialog.mInitShow();
                baseDialog.setCancelable(false);
            }else{
                ToastUtils.getLongToast(mContext,"暂不支持该支付方式！");
            }
        }
    };


    public void xianjin(){
        new CBDialogBuilder(mContext)
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setTitle("温馨提示！")
                .setMessage("是否使用现金充值？")
                .setConfirmButtonText("确定")
                .setCancelButtonText("取消")
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setButtonClickListener(true, new CBDialogBuilder.onDialogbtnClickListener() {
                    @Override
                    public void onDialogbtnClick(Context context, Dialog dialog, int whichBtn) {
                        switch (whichBtn) {
                            case BUTTON_CONFIRM:
                                addTradeNet.setData(Integer.parseInt(vipId),shopId,orderNo,5,BegAmount,id,prefs.getString("enCode"),shopClerkId);
                                break;
                            case BUTTON_CANCEL:
                                ToastUtils.show("已取消现金支付");
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create().show();
    }

    /**
     * 微信支付
     */
    private void weiXinPlay(int payType){
        payMoney=BegAmount;
        bundle.putString("title",title);
        bundle.putString("vipMobile",vipMobile);
        bundle.putInt("payType",payType);
        bundle.putInt("shopClerkId",shopClerkId);
        bundle.putDouble("Amount",payMoney);
        bundle.putString("OrderNo",orderNo);
        bundle.putString("shopNameNick",shopNameNick);
        bundle.putString("shopClerkName",shopClerkName);
        bundle.putString("userName",prefs.getString("userName"));
        bundle.putInt("shopId",shopId);
        bundle.putString("vipId",vipId);
        bundle.putInt("id",id);
        bundle.putDouble("DonationAmount",DonationAmount);
        bundle.putDouble("TotalBalance",TotalBalance);
        bundle.putDouble("UsableBalance",UsableBalance);
        bundle.putDouble("GivenBalance",GivenBalance);
        Skip.mNextFroData(mActivity,WeiXinAliPayActivity.class,bundle);
    }

    /**
     * 支付宝
     */
    private void aliPlay(int payType){
        payMoney=BegAmount;
        bundle.putString("title",title);
        bundle.putString("vipMobile",vipMobile);
        bundle.putInt("payType",payType);
        bundle.putInt("shopClerkId",shopClerkId);
        bundle.putDouble("Amount",payMoney);
        bundle.putString("OrderNo",orderNo);
        bundle.putString("shopClerkName",shopClerkName);
        bundle.putString("shopNameNick",shopNameNick);
        bundle.putString("userName",prefs.getString("userName"));
        bundle.putInt("shopId",shopId);
        bundle.putString("vipId",vipId);
        bundle.putInt("id",id);
        bundle.putDouble("TotalBalance",TotalBalance);
        bundle.putDouble("UsableBalance",UsableBalance);
        bundle.putDouble("GivenBalance",GivenBalance);
        Skip.mNextFroData(mActivity,WeiXinAliPayActivity.class,bundle);
    }

    /**
     * 启动银联收银
     */
    private void unionPay(String orderNo){
        amount = "2";
        lock[0] = LOCK_WAIT;
        doConsumeHasTemplate(amount,orderNo);
    }

    /**
     * 消费
     */
    private void doConsumeHasTemplate(final String amount ,final String orderNo) {
        new Thread() {
            public void run() {
                try {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("myOrderNo", orderNo);
                    rXiaoFei= pCore.xiaoFei(amount, map, callBack);
                    PosPayBean bean=new PosPayBean(shopId+"",shopNameNick,shopClerkName,orderNo,payMoney+"",rXiaoFei.retrievalReferenceNumber,"4","银联支付","","3",AbDateUtil.getCurrentDate(),"消费成功...",prefs.getString("enCode"),rXiaoFei.primaryAccountNumber,"true");
                    insertData(bean);
                    refNum = rXiaoFei.retrievalReferenceNumber;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PosBean posBean=new PosBean();
                                posBean.AccountNumber=rXiaoFei.primaryAccountNumber;
                                posBean.ReferenceNumber=rXiaoFei.retrievalReferenceNumber;
                                posBean.TraceAuditNumber=rXiaoFei.systemTraceAuditNumber;

                                if(rXiaoFei.retrievalReferenceNumber!=null){
                                    PayLogBean logBean=new PayLogBean();
                                    logBean.setRemark("POS储值");
                                    logBean.setPayType(payType);
                                    logBean.setEnCode(prefs.getString("enCode"));
                                    logBean.setPayAmount(BegAmount);
                                    logBean.setVipId(Integer.parseInt(vipId));
                                    logBean.setShopId(shopId);
                                    logBean.setReferenceNumber(rXiaoFei.retrievalReferenceNumber);
                                    logBean.setTraceAuditNumber(rXiaoFei.retrievalReferenceNumber);
                                    logBean.setCashierId(shopClerkId);
                                    logBean.setAccountNumber(primaryAccountNumber);
                                    logBean.setRuleId(id);
                                    strJson=gson.toJson(logBean);

                                    addTradeNet=new AddTradeNet(mContext);
                                    addTradeNet.setData(Integer.parseInt(vipId),shopId,refNum,4,BegAmount,id,prefs.getString("enCode"),shopClerkId);
                                }else{

                                    PayLogBean logBean=new PayLogBean();
                                    logBean.setRemark("POS储值");
                                    logBean.setPayType(payType);
                                    logBean.setEnCode(prefs.getString("enCode"));
                                    logBean.setPayAmount(BegAmount);
                                    logBean.setVipId(Integer.parseInt(vipId));
                                    logBean.setShopId(shopId);
                                    logBean.setReferenceNumber(rXiaoFei.retrievalReferenceNumber);
                                    logBean.setTraceAuditNumber(rXiaoFei.retrievalReferenceNumber);
                                    logBean.setCashierId(shopClerkId);
                                    logBean.setAccountNumber(primaryAccountNumber);
                                    logBean.setRuleId(id);
                                    strJson=gson.toJson(logBean);

                                    addTradeNet=new AddTradeNet(mContext);
                                    addTradeNet.setData(Integer.parseInt(vipId),shopId,orderNo,4,BegAmount,id,prefs.getString("enCode"),shopClerkId);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    InitPlay.showMsg(e.getLocalizedMessage(),mActivity,tv_show_amount);
                    Log.d(Constant.TAG,"收银ERROR：="+e.getLocalizedMessage());
                }
            }
        }.start();
    }

    /**
     * 显示消费对话框
     * @param core
     * @throws Exception
     */
    private void showConsumeDialog(final PosCore core) throws Exception {
        payMoney=BegAmount;
        lock[0] = LOCK_WAIT;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = getLayoutInflater().inflate(R.layout.consume_dialog, null);
                final AlertDialog dialog = new AlertDialog.Builder(PaymentTypeActivity.this).setView(view).setCancelable(false).create();
                dialog.show();

                Button btn_confirm = (Button) view.findViewById(R.id.btn_consume_confiem);
                Button btn_cancel = (Button) view.findViewById(R.id.btn_consume_cancel);
                final EditText ed_consumen_amount = (EditText) view.findViewById(R.id.ed_consume_amount);
                ed_consumen_amount.setFocusable(false);ed_consumen_amount.setFocusableInTouchMode(false);//设置不可编辑状态；
                if(!TextUtils.isEmpty(ed_consumen_amount.getText().toString())){
                    ed_consumen_amount.setText("");//设置支付金额
                }
                ed_consumen_amount.setText(payMoney+"");//设置支付金额
                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//确定支付

                        synchronized (lock) {
                            money=(int)(payMoney*100);
                            lock[0] = LOCK_CONTINUE;
                            lock.notify();
                        }
                        dialog.dismiss();
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {//取消支付
                    @Override
                    public void onClick(View v) {
                        synchronized (lock) {
                            ed_consumen_amount.setText("");//设置支付金额
                            lock[0] = LOCK_CONTINUE;
                            lock.notify();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        // 等待输入
        synchronized (lock) {
            while (true) {
                if (lock[0] == LOCK_WAIT) {
                    try {
                        lock.wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
        core.setXiaoFeiAmount(money+"");//设置消费金额
    }

    /**
     * 收银回调
     */
    public class PosCallBack implements IPosCallBack {
        private final PosCore core;

        PosCallBack(PosCore core) {
            this.core = core;
        }

        @Override
        public void onInfo(String s) {
            InitPlay.showMsg(s,mActivity,tv_show_amount);
        }

        @Override
        public void onEvent(int eventID, Object[] params) throws Exception {
            Log.i("test","onEvent:"+eventID+"");
            switch (eventID) {
                case 110:
                    InitPlay.showMsg("打印票据"+ params[0],mActivity,tv_show_amount);
                    break;
                case EVENT_Setting:{
                    core.reprint(refNum);
                    InitPlay.showMsg("doSetting:完成",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_Task_start: {
                    InitPlay.showMsg("任务进程开始执行",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_Task_end: {
                    InitPlay.showMsg("任务进程执行结束",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_CardID_start: {
                    InitPlay.showMsg("读取银行卡信息",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_CardID_end: {
                    String cardNum = (String) params[0];
                    if (!TextUtils.isEmpty(cardNum)) {
                        Log.w(Constant.TAG, "卡号为:" + params[0]);
                        primaryAccountNumber= params[0]+"";
                        showConsumeDialog(core);
                    }
                    break;
                }
                case EVENT_Comm_start: {
                    InitPlay.showMsg("开始网络通信",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_Comm_end: {
                    InitPlay.showMsg("网络通信完成",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_DownloadPlugin_start: {
                    InitPlay.showMsg("开始下载插件",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_DownloadPlugin_end: {
                    InitPlay.showMsg("插件下载完成",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_InstallPlugin_start: {
                    InitPlay.showMsg("开始安装插件",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_InstallPlugin_end: {
                    InitPlay.showMsg("插件安装完成",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_RunPlugin_start: {
                    InitPlay.showMsg("开始启动插件",mActivity,tv_show_amount);
                    break;
                }
                case EVENT_RunPlugin_end: {
                    InitPlay.showMsg("插件启动完成",mActivity,tv_show_amount);
                    break;
                }

                case EVENT_AutoPrint_start:{
                    InitPlay.showMsg("参考号:" + params[0],mActivity,tv_show_amount);
                    break;
                }
                case EVENT_AutoPrint_end:
                    break;

                case IPosCallBack.ERR_InTask:{
                    if ((Integer) params[0] == EVENT_NO_PAPER) {
	                        showRePrintDialog();
                    }
                }

                default: {
                    InitPlay.showMsg("Event:" + eventID,mActivity,tv_show_amount);
                    break;
                }
            }
        }
    }


    private boolean needRePrint;

    /**
     * 显示重打印按钮
     */
    private void showRePrintDialog() {
        lock[0] = LOCK_WAIT;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PaymentTypeActivity.this);
                dialog.setMessage("打印机缺纸");
                dialog.setPositiveButton("重打印", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        synchronized (lock) {
                            needRePrint = true;
                            lock[0] = LOCK_CONTINUE;
                            lock.notify();
                        }
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        synchronized (lock) {
                            needRePrint = false;
                            lock[0] = LOCK_CONTINUE;
                            lock.notify();
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });


        // 等待输入
        synchronized (lock) {
            while (true) {
                if (lock[0] == LOCK_WAIT) {
                    try {
                        lock.wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }


        try {
            pCore.printContinue(needRePrint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPosCore() {
        if (pCore == null) {
            // 配置数据为开发阶段的数据
            HashMap<String, String> init_params = new HashMap<String,String>();
            init_params.put(PosConfig.Name_EX + "1053", prefs.getString("shopNameNick"));// 签购单小票台头
            init_params.put(PosConfig.Name_EX + "1100", "cn.weipass.cashier");// 核心APP 包名
            init_params.put(PosConfig.Name_EX + "1101", "com.wangpos.cashiercoreapp.services.CoreAppService");// 核心APP 类名
            init_params.put(PosConfig.Name_EX + "1093", "2");// 是否需要打印三联签购单 1.需要 2.不需要
            init_params.put(PosConfig.Name_EX + "1012", "1");// 华势通道
            init_params.put(PosConfig.Name_MerchantName, "coreApp");

            pCore = PosCoreFactory.newInstance(this, init_params);
            callBack = new PaymentTypeActivity.PosCallBack(pCore);

        }
    }

    private  ListView listview;
    private TextView tv_cancel,tv_sure;
    public class BaseDialog extends Dialog implements View.OnClickListener {

        public BaseDialog(Context context) {
            this(context, R.style.dialog);
        }

        public BaseDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_shop_clerk);

            listview = (ListView) findViewById(R.id.lsitview);
            tv_cancel=(TextView) findViewById(R.id.tv_cancel);
            tv_cancel.setOnClickListener(this);
            tv_sure=(TextView) findViewById(R.id.tv_sure);
            tv_sure.setOnClickListener(this);

            shopClerkBeen=new ArrayList<>();
            for (int i=0;i<clerkListBeen.size();i++){
                ShopClerkBean clerkBean=new ShopClerkBean();
                clerkBean.setClerkId(clerkListBeen.get(i).getCOUNSELOR_ID());
                clerkBean.setMobile(clerkListBeen.get(i).getCOUNSELOR_MOBILE());
                clerkBean.setName(clerkListBeen.get(i).getCOUNSELOR_NAME());
                clerkBean.setId(i+1);
                shopClerkBeen.add(clerkBean);
            }

            for (int i = 0; i < shopClerkBeen.size(); i++) {
                checkList.add(false); // 均为未选
            }
            myAdapter = new MyAdapter(mContext,shopClerkBeen);
            listview.setAdapter(myAdapter);
        }

        public void mInitShow() {
            show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    shopClerkName="";
                    shopClerkId=0;
                    baseDialog.dismiss();
                    break;
                case R.id.tv_sure:
                    baseDialog.dismiss();
                    switch (payType){
                        case 2://微信支付
                             weiXinPlay(payType);
                            break;
                        case 3://支付宝
                            aliPlay(payType);
                            break;
                        case 4://银联支付
                            unionPay(orderNo);
                            break;
                        case 5://现金支付
                            xianjin();
                            break;
                        default:
                            ToastUtils.getLongToast(mContext,"暂不支持该支付方式！");
                            break;
                    }
                    break;
            }
        }
    }


    //设置选中的位置，将其他位置设置为未选
    public void checkPosition(int position) {
        for (int i = 0; i < checkList.size(); i++) {
            if (position == i) {// 设置已选位置
                checkList.set(i, true);
                shopClerkName=shopClerkBeen.get(i).getName();
                shopClerkId=shopClerkBeen.get(i).getClerkId();
            } else {
                checkList.set(i, false);
                shopClerkName=null;
            }
        }
        myAdapter.notifyDataSetChanged();
    }



    //自定义adapter
    class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        List<ShopClerkBean> myList;

        public MyAdapter(Context context, List<ShopClerkBean> myList) {
            this.inflater = LayoutInflater.from(context);
            this.myList = myList;
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_shop_clerk, null);
                holder = new ViewHolder();
                holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.cbCheckBox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_id.setText(myList.get(position).getId()+"");
                holder.tv_name.setText(myList.get(position).getName());
                holder.checkBox.setChecked(checkList.get(position));

            convertView.setOnClickListener(new View.OnClickListener() {//item单击进行单选设置

                @Override
                public void onClick(View v) {
                    checkPosition(position);
                }
            });
            return convertView;
        }

        public final class ViewHolder {
            TextView tv_id,tv_name;
            CheckBox checkBox;
        }
    }
    private void insertData(PosPayBean bean){
        PosSqliteDatabaseUtils.insterData(mContext,bean);
    }

}

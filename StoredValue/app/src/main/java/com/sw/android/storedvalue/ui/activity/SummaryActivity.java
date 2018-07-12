package com.sw.android.storedvalue.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.adapter.PosRechargeListAdapter;
import com.sw.android.storedvalue.base.BaseActivity;
import com.sw.android.storedvalue.base.BaseRecyclerView;
import com.sw.android.storedvalue.bean.ResultGetPosRechargeListBean;
import com.sw.android.storedvalue.bean.UserBean;
import com.sw.android.storedvalue.bean.UserInfoBean;
import com.sw.android.storedvalue.bean.printer.ShopStoredValueLatticePrinterBean;
import com.sw.android.storedvalue.bean.printer.ShopStoredValueLatticePrinterInfoBean;
import com.sw.android.storedvalue.net.GetPosRechargeListNet;
import com.sw.android.storedvalue.net.UserInfoNet;
import com.sw.android.storedvalue.scanner.CaptureActivity;
import com.sw.android.storedvalue.utils.AbDateUtil;
import com.sw.android.storedvalue.utils.CheckUtil;
import com.sw.android.storedvalue.utils.DecimalFormatUtils;
import com.sw.android.storedvalue.utils.SingleCashierPrinterTools;
import com.sw.android.storedvalue.utils.Skip;
import com.sw.android.storedvalue.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zhl.cbdialog.CBDialogBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.LatticePrinter;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * 储值汇总
 */
public class SummaryActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout llBack,ll_choose;
    private TextView tv_printer_ticket,tv_stored_value,tv_not_data;
    private TextView tv_CurrentTotalAmount,tv_CurrentGivenTotalAmount;
    private SwipeMenuRecyclerView rvView;
    private SwipeRefreshLayout swipeLayout;
    private BaseDialog baseDialog;

    private int REQUEST_CONTACT = 20;
    final int RESULT_CODE = 101;
    private String scanCode;

    private double currentTotalAmount;
    private double currentGivenTotalAmount;

    private String userName;//用户名【营业员】
    private String shopNameNick;//店铺名

    private TopRightMenu mTopRightMenu;
    private String startDate= AbDateUtil.getCurrentDate("yyyy-MM-dd");//开始时间
    private String endData=AbDateUtil.getCurrentDate("yyyy-MM-dd");//结束时间

    //打印===========
    private LatticePrinter latticePrinter;// 点阵打印
    private ShopStoredValueLatticePrinterBean shopStoredValueLatticePrinterBean;

    private UserInfoNet userInfoNet;
    private GetPosRechargeListNet posRechargeListNet;
    private PosRechargeListAdapter posRechargeListAdapter;
    private List<ResultGetPosRechargeListBean.GetPosRechargeListBean> rechargeListBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //通过EventBus订阅事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initView();

        userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();

        posRechargeListNet=new GetPosRechargeListNet(mContext);
        posRechargeListNet.setData(prefs.getString("enCode"),AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(UserInfoBean bean) {
        if(bean.isSuccess()!=false && bean.TModel!=null){
            shopNameNick=bean.TModel.getShopName();
            userName=bean.TModel.getName();
            prefs.putString("userName",userName);
            prefs.putString("shopNameNick",shopNameNick);
            prefs.putString("ShopAddress",bean.TModel.getShopAddress());
            prefs.putString("ShopArea",bean.TModel.getShopArea());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ResultGetPosRechargeListBean bean) {
        if(bean.isSuccess()!=false){
            rechargeListBeen=new ArrayList<>();
            if(bean.TModel!=null && bean.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);

                rechargeListBeen.addAll(bean.TModel);
                currentTotalAmount=0.0;
                currentGivenTotalAmount=0.0;

                for (int i=0;i<rechargeListBeen.size();i++){
                    currentTotalAmount+=rechargeListBeen.get(i).getAmount();
                    currentGivenTotalAmount+=rechargeListBeen.get(i).getDonationAmount();
                }
                tv_CurrentTotalAmount.setText(DecimalFormatUtils.decimalFormatRound(currentTotalAmount)+"");
                tv_CurrentGivenTotalAmount.setText(DecimalFormatUtils.decimalFormatRound(currentGivenTotalAmount)+"");

                posRechargeListAdapter=new PosRechargeListAdapter(rechargeListBeen,mContext);
                rvView.setAdapter(posRechargeListAdapter);
            }else{
                tv_CurrentTotalAmount.setText("0.0");
                tv_CurrentGivenTotalAmount.setText("0.0");
                posRechargeListAdapter=new PosRechargeListAdapter(rechargeListBeen,mContext);
                rvView.setAdapter(posRechargeListAdapter);

                tv_not_data.setVisibility(View.VISIBLE);
            }

        }else{
            ToastUtils.getLongToast(mContext,"Msg:"+bean.getMessage());
        }
    }

    private void initView() {
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_CurrentTotalAmount=(TextView) findViewById(R.id.tv_CurrentTotalAmount);
        tv_CurrentGivenTotalAmount=(TextView) findViewById(R.id.tv_CurrentGivenTotalAmount);
        rvView= (SwipeMenuRecyclerView) findViewById(R.id.rv_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        BaseRecyclerView.initRecyclerView(mContext, rvView, swipeLayout, mOnRefreshListener, false);

        llBack= (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
        ll_choose= (LinearLayout) findViewById(R.id.ll_choose);
        ll_choose.setOnClickListener(this);
        tv_printer_ticket= (TextView) findViewById(R.id.tv_printer_ticket);
        tv_printer_ticket.setOnClickListener(this);
        tv_stored_value= (TextView) findViewById(R.id.tv_stored_value);
        tv_stored_value.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
//                Skip.mBack(mActivity);
                exit();
                break;
            case R.id.ll_choose:
                showMore();
                break;
            case R.id.tv_printer_ticket:
                if(rechargeListBeen.size()!=0){
                    isPrinter();
                }else{
                    ToastUtils.getToast(mContext,"当前没有票据可打印!");
                }
                break;
            case R.id.tv_stored_value:
                baseDialog = new BaseDialog(mContext);
                baseDialog.mInitShow();
                baseDialog.setCancelable(false);
                break;
        }
    }

    private void showMore(){
        mTopRightMenu = new TopRightMenu(SummaryActivity.this);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.mipmap.today,"今天"));
        menuItems.add(new MenuItem( R.mipmap.yesterday,"昨天"));
        menuItems.add(new MenuItem(R.mipmap.week,"本周"));
        mTopRightMenu
                .setHeight(480)     //默认高度480
                .setWidth(320)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .addMenuItem(new MenuItem( R.mipmap.month,"本月"))
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position){
                            case 0://今日
                                posRechargeListNet=new GetPosRechargeListNet(mContext);
                                posRechargeListNet.setData(prefs.getString("enCode"),AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
                                startDate=AbDateUtil.getCurrentDate("yyyy-MM-dd");
                                endData=AbDateUtil.getCurrentDate("yyyy-MM-dd");
                                break;
                            case 1://昨天
                                posRechargeListNet=new GetPosRechargeListNet(mContext);
                                posRechargeListNet.setData(prefs.getString("enCode"),AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd"));
                                startDate=AbDateUtil.YesTerDay("yyyy-MM-dd");
                                endData=AbDateUtil.getCurrentDate("yyyy-MM-dd");
                                break;
                            case 2://本周
                                posRechargeListNet=new GetPosRechargeListNet(mContext);
                                posRechargeListNet.setData(prefs.getString("enCode"),AbDateUtil.getFirstDayOfWeek("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfWeek("yyyy-MM-dd 23:59:59"));
                                startDate=AbDateUtil.getFirstDayOfWeek("yyyy-MM-dd");
                                endData=AbDateUtil.getLastDayOfWeek("yyyy-MM-dd");
                                break;
                            case 3://本月
                                posRechargeListNet=new GetPosRechargeListNet(mContext);
                                posRechargeListNet.setData(prefs.getString("enCode"),AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
                                startDate=AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd");
                                endData=AbDateUtil.getLastDayOfMonth("yyyy-MM-dd");
                                break;
                            default:
                                break;
                        }
                    }
                })
                .showAsDropDown(ll_choose, -225, 0);
    }

    /**
     * 处理扫描Activity返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_CODE) {
            if(data.getStringExtra("mScanVipResult")!=null && data.getStringExtra("mScanVipResult").equals("1008")){
                scanCode= data.getStringExtra("resultCode");
                if(scanCode!=null){
                    etInviteCode.setText(scanCode);
                    bundle.putString("vipMobile",scanCode);
                    Skip.mNextFroData(mActivity,VipInfoActivity.class,bundle);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public EditText etInviteCode;
    public class BaseDialog extends Dialog implements View.OnClickListener {

        ImageView ivScanCode;
        TextView tvCancel;
        TextView tvSure;

        private String inviteCode;

        public BaseDialog(Context context) {
            this(context, R.style.dialog);
        }

        public BaseDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_scan_vip);

            etInviteCode = (EditText) findViewById(R.id.et_invite_code);
            tvSure = (TextView) findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(this);
            tvCancel = (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);
            ivScanCode = (ImageView) findViewById(R.id.iv_scan_code);
            ivScanCode.setOnClickListener(this);
        }

        public void mInitShow() {
            show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    baseDialog.dismiss();
                    break;
                case R.id.iv_scan_code:
                    //扫描会员二维码
                    Skip.mScanVipActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
                    break;
                case R.id.tv_sure:
                    inviteCode = etInviteCode.getText().toString().trim();
                    if(CheckUtil.phoneVerify(mContext,inviteCode)){
                        baseDialog.dismiss();
                        bundle.putString("vipMobile",inviteCode);
                        Skip.mNextFroData(mActivity,VipInfoActivity.class,bundle);
                    }
                    break;
            }
        }
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            rvView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //重新加载数据
                    posRechargeListNet=new GetPosRechargeListNet(mContext);
                    posRechargeListNet.setData(prefs.getString("enCode"),AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
                    swipeLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private void isPrinter(){
        new CBDialogBuilder(mContext)
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setTitle("温馨提示")
                .setMessage("是否打印当前充值票据？")
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
     * 设置店铺充值票据点阵打印方法
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
            //设置打印信息
            shopStoredValueLatticePrinterBean=new ShopStoredValueLatticePrinterBean();
            shopStoredValueLatticePrinterBean.setShopClerkName(prefs.getString("userName"));
            if(prefs.getString("ShopAddress")!=null){
                shopStoredValueLatticePrinterBean.setShopName(prefs.getString("ShopArea")+prefs.getString("shopNameNick"));
                shopStoredValueLatticePrinterBean.setCounterName(prefs.getString("ShopAddress"));
            }else{
                shopStoredValueLatticePrinterBean.setShopName(prefs.getString("shopNameNick"));
                shopStoredValueLatticePrinterBean.setCounterName(prefs.getString("shopNameNick"));
            }

            for (int i=0;i<rechargeListBeen.size();i++){
                if(rechargeListBeen.get(i).getPayType()==2){
                    shopStoredValueLatticePrinterBean.setWeiXinPayAmount(rechargeListBeen.get(i).getAmount());
                    shopStoredValueLatticePrinterBean.setWeiXinPayGivenAmount(rechargeListBeen.get(i).getDonationAmount());

                }else if(rechargeListBeen.get(i).getPayType()==3){
                    shopStoredValueLatticePrinterBean.setAliPayAmount(rechargeListBeen.get(i).getAmount());
                    shopStoredValueLatticePrinterBean.setAliPayGivenAmount(rechargeListBeen.get(i).getDonationAmount());

                }else if(rechargeListBeen.get(i).getPayType()==4){
                    shopStoredValueLatticePrinterBean.setUnionPayAmount(rechargeListBeen.get(i).getAmount());
                    shopStoredValueLatticePrinterBean.setUnionPayGivenAmount(rechargeListBeen.get(i).getDonationAmount());

                }else if(rechargeListBeen.get(i).getPayType()==5){
                    shopStoredValueLatticePrinterBean.setCashPayAmount(rechargeListBeen.get(i).getAmount());
                    shopStoredValueLatticePrinterBean.setCashPayGivenAmount(rechargeListBeen.get(i).getDonationAmount());
                }
            }
            shopStoredValueLatticePrinterBean.setCurrentTotalAmount(currentTotalAmount);
            shopStoredValueLatticePrinterBean.setCurrentGivenTotalAmount(currentGivenTotalAmount);

            ShopStoredValueLatticePrinterInfoBean.printLattice(latticePrinter,shopStoredValueLatticePrinterBean);
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

    /**
     * 退出
     */
    public void exit(){
        new CBDialogBuilder(mContext)
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setTitle("退出登录")
                .setMessage("是否退出登录，退出后将不能做任何操作！")
                .setConfirmButtonText("确定")
                .setCancelButtonText("取消")
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setButtonClickListener(true, new CBDialogBuilder.onDialogbtnClickListener() {
                    @Override
                    public void onDialogbtnClick(Context context, Dialog dialog, int whichBtn) {
                        switch (whichBtn) {
                            case BUTTON_CONFIRM:
                                prefs.cleanAll();
                                mCache.remove("GetPayCategory");
                                Skip.mNext(mActivity, LoginActivity.class, true);
                                break;
                            case BUTTON_CANCEL:
                                ToastUtils.show("已取消退出");
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create().show();
    }
}

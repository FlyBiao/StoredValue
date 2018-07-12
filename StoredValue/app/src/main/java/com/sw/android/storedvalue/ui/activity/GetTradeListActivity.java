package com.sw.android.storedvalue.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.adapter.TradeListAdapter;
import com.sw.android.storedvalue.base.BaseActivity;
import com.sw.android.storedvalue.base.BaseRecyclerView;
import com.sw.android.storedvalue.bean.ResultTradeListBean;
import com.sw.android.storedvalue.bean.SortBean;
import com.sw.android.storedvalue.bean.TradeVipIdBean;
import com.sw.android.storedvalue.listener.OnItemClickListener;
import com.sw.android.storedvalue.net.GetTradeListNet;
import com.sw.android.storedvalue.utils.Skip;
import com.sw.android.storedvalue.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值记录
 */
public class GetTradeListActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvNotData;
    private LinearLayout llBack;
    private SwipeMenuRecyclerView rvView;
    private SwipeRefreshLayout swipeLayout;

    private int PageIndex=1;
    private String vipId;
    private JSONArray arrayVipId;
    private JSONArray arraySort;
    private GetTradeListNet tradeListNet;
    private List<ResultTradeListBean.TradeListBean> tradeListBeen;
    private TradeListAdapter tradeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_trade_list);
        //通过EventBus订阅事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            vipId=bundle.getString("vipId");
        }

        initView();
        initData();
    }

    private void initData() {
        arrayVipId=new JSONArray();
        TradeVipIdBean vipIdBean=new TradeVipIdBean();
        vipIdBean.setField("VipId");
        vipIdBean.setValue(vipId);
        arrayVipId.put(vipIdBean.getVipIdInfo());

        arraySort=new JSONArray();
        SortBean sortBean=new SortBean();
        sortBean.setField("CreateTime");
        sortBean.setValue("desc");
        arraySort.put(sortBean.getSortIdInfo());

        tradeListNet=new GetTradeListNet(mContext);
        tradeListNet.setData(PageIndex,arrayVipId,arraySort);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(ResultTradeListBean bean) {
        if(bean.isSuccess()!=false){
            if(bean.TModel.size()!=0){
                tradeListBeen=new ArrayList<>();
                tradeListBeen.addAll(bean.TModel);

                tradeListAdapter=new TradeListAdapter(tradeListBeen,mContext);
                tradeListAdapter.setOnItemClickListener(onItemClickListener);
                rvView.setAdapter(tradeListAdapter);
            }else{
                tvNotData.setVisibility(View.VISIBLE);
            }
        }else{
            ToastUtils.getLongToast(mContext,"Message:"+bean.getMessage());
        }

    }

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
        tvNotData= (TextView) findViewById(R.id.tv_not_data);
        rvView= (SwipeMenuRecyclerView) findViewById(R.id.rv_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        BaseRecyclerView.initRecyclerView(mContext, rvView, swipeLayout, mOnRefreshListener, true);
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
                    tradeListNet=new GetTradeListNet(mContext);
                    tradeListNet.setData(PageIndex,arrayVipId,arraySort);
                    swipeLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

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
            bundle.putString("Id",tradeListBeen.get(position).getOrderNo());
            Skip.mNextFroData(mActivity,GetTradeDetailsActivity.class,bundle);
        }
    };
}

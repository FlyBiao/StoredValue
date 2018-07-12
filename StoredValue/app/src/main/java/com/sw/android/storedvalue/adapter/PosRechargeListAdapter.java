package com.sw.android.storedvalue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.bean.ResultGetPosRechargeListBean;
import com.sw.android.storedvalue.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description POS充值查询列表
 * Created at 2017/7/19 15:33
 * Version 1.0
 */

public class PosRechargeListAdapter extends SwipeMenuAdapter<PosRechargeListAdapter.DefaultViewHolder> {

    private List<ResultGetPosRechargeListBean.GetPosRechargeListBean> titles;
    private OnItemClickListener mOnItemClickListener;
    private static Context mContext;

    public PosRechargeListAdapter(List<ResultGetPosRechargeListBean.GetPosRechargeListBean> titles, Context mContext) {
        this.titles = titles;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pos_recharge_list, parent, false);
    }

    @Override
    public PosRechargeListAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PosRechargeListAdapter.DefaultViewHolder holder, int position) {
        holder.setData(titles.get(position).getAmount(),titles.get(position).getDonationAmount(),titles.get(position).getPayType());
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_PayAmount,tv_PayGivenAmount;
        ImageView iv_pay_type;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_PayAmount= (TextView) itemView.findViewById(R.id.tv_PayAmount);
            tv_PayGivenAmount= (TextView) itemView.findViewById(R.id.tv_PayGivenAmount);
            iv_pay_type= (ImageView) itemView.findViewById(R.id.iv_pay_type);

        }

        public void setData(double Amount,double PayGivenAmount,int payType) {
            tv_PayAmount.setText(Amount+"");
            tv_PayGivenAmount.setText(PayGivenAmount+"");

            switch (payType){
                case 2:
                    iv_pay_type.setImageResource(R.mipmap.weixin);
                    break;
                case 3:
                    iv_pay_type.setImageResource(R.mipmap.alipay);
                    break;
                case 4:
                    iv_pay_type.setImageResource(R.mipmap.unionpay);
                    break;
                case 5:
                    iv_pay_type.setImageResource(R.mipmap.cash);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}

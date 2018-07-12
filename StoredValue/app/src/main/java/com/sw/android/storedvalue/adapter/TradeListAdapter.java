package com.sw.android.storedvalue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.bean.ResultTradeListBean;
import com.sw.android.storedvalue.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 充值记录列表
 * Created at 2017/7/19 15:33
 * Version 1.0
 */

public class TradeListAdapter extends SwipeMenuAdapter<TradeListAdapter.DefaultViewHolder> {

    private List<ResultTradeListBean.TradeListBean> titles;
    private OnItemClickListener mOnItemClickListener;
    private static Context mContext;

    public TradeListAdapter(List<ResultTradeListBean.TradeListBean> titles, Context mContext) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trade_list, parent, false);
    }

    @Override
    public TradeListAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TradeListAdapter.DefaultViewHolder holder, int position) {
        holder.setData(titles.get(position).getType(),titles.get(position).getAmount(),titles.get(position).getOrderNo(),titles.get(position).getCreateTime());
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_id,tv_create_time,tv_amount,tv_type;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_amount= (TextView) itemView.findViewById(R.id.tv_amount);
            tv_id= (TextView) itemView.findViewById(R.id.tv_id);
            tv_create_time= (TextView) itemView.findViewById(R.id.tv_create_time);
            tv_type= (TextView) itemView.findViewById(R.id.tv_type);
        }

        public void setData(int type,double Amount,String id ,String time) {
            switch (type){
                case 0://0:结转
                    break;
                case 1://1:充值
                    tv_type.setText("充值");
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.green_normal));
                    tv_amount.setText("+"+ Amount);
                    tv_amount.setTextColor(mContext.getResources().getColor(R.color.green_normal));
                    break;
                case 2://2:充值赠送
                    tv_type.setText("赠送");
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                    tv_amount.setText("+"+ Amount);
                    tv_amount.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                    break;
                case 3://3:充值金额购物消费
                    tv_type.setText("购物消费");
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.gray1));
                    tv_amount.setText("-"+ Amount);
                    tv_amount.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                    break;
                case 4://4:充值金额购物退款
                    tv_type.setText("购物退款");
                    tv_type.setText("购物消费");
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.gray1));
                    tv_amount.setText("-"+ Amount);
                    tv_amount.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                    break;
                case 5:// 5:充值退款
                    tv_type.setText("充值退款");
                    tv_type.setText("购物消费");
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.gray1));
                    tv_amount.setText("-"+ Amount);
                    tv_amount.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                    break;
                case 6:// 6:赠送金额购物消费
                    tv_type.setText("赠送购物消费");
                    tv_type.setText("购物消费");
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.gray1));
                    tv_amount.setText("-"+ Amount);
                    tv_amount.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                    break;
                case 7://7:赠送金额购物消费退款
                    tv_type.setText("赠送购物消费退款");
                    tv_type.setText("购物消费");
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.gray1));
                    tv_amount.setText("-"+Amount);
                    tv_amount.setTextColor(mContext.getResources().getColor(R.color.green_pressed));
                    break;
            }

            tv_create_time.setText(time);
            tv_id.setText(id);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}

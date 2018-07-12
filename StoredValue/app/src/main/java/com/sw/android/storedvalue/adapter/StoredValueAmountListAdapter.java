package com.sw.android.storedvalue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.bean.ResultRuleListBean;
import com.sw.android.storedvalue.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 充值规则列表
 * Created at 2017/7/19 15:33
 * Version 1.0
 */

public class StoredValueAmountListAdapter extends SwipeMenuAdapter<StoredValueAmountListAdapter.DefaultViewHolder> {

    private List<ResultRuleListBean.RuleListBean> titles;
    private OnItemClickListener mOnItemClickListener;
    private static Context mContext;

    public StoredValueAmountListAdapter(List<ResultRuleListBean.RuleListBean> titles, Context mContext) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_stored, parent, false);
    }

    @Override
    public StoredValueAmountListAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StoredValueAmountListAdapter.DefaultViewHolder holder, int position) {
        holder.setData(titles.get(position).getType(),titles.get(position).getBegAmount(),titles.get(position).getEndAmount(),titles.get(position).getTitle());
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_amount,tvTitle;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_amount= (TextView) itemView.findViewById(R.id.tv_amount);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(int type,double BegAmount,double EndAmount ,String title) {
            switch (type){
                case 0://0:固定金额:
                    tv_amount.setText(BegAmount+"");
                    tvTitle.setText("("+title+")");
                    break;
                case 1://1:比例金额
                    tv_amount.setText(BegAmount+"-"+ EndAmount);
                    tvTitle.setText("("+title+")");
                    break;
                case 2://2:任意金额
                    tv_amount.setVisibility(View.GONE);
                    tvTitle.setText("("+title+")");
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

package com.sw.android.storedvalue.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.bean.ResultPayCategoryBean;
import com.sw.android.storedvalue.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/9/12 9:37
 * Version 1.0
 */

public class PosPayCategoryAdapter extends SwipeMenuAdapter<PosPayCategoryAdapter.DefaultViewHolder> {

    public List<ResultPayCategoryBean.PayCategoryBea> categoryBeaList;
    private OnItemClickListener mOnItemClickListener;

    public PosPayCategoryAdapter(List<ResultPayCategoryBean.PayCategoryBea> categoryBeaList){
        this.categoryBeaList=categoryBeaList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return categoryBeaList == null ? 0 : categoryBeaList.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pos_pay_category, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(categoryBeaList.get(position).getDescription(),categoryBeaList.get(position).getCategoryType());
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener mOnItemClickListener;
        TextView tv_pay_description;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_pay_description= (TextView) itemView.findViewById(R.id.tv_pay_description);
        }

        public void setData(String description,int CategoryType) {
            this.tv_pay_description.setText(description);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}

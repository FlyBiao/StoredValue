package com.sw.android.storedvalue.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.bean.ResultTradeListBean;
import com.sw.android.storedvalue.bean.ShopClerkBean;
import com.sw.android.storedvalue.listener.OnItemClickListener;
import com.sw.android.storedvalue.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/10/13 15:16
 * Version 1.0
 */

public class ShopClerkAdapter  extends SwipeMenuAdapter<ShopClerkAdapter.DefaultViewHolder> {

    private List<ShopClerkBean> titles;
    private OnItemClickListener mOnItemClickListener;
    private static Context mContext;
    private static Activity mActivity;

    public ShopClerkAdapter(List<ShopClerkBean> titles, Context mContext,Activity mActivity) {
        this.titles = titles;
        this.mContext=mContext;
        this.mActivity=mActivity;
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_clerk, parent, false);
    }

    @Override
    public ShopClerkAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        ShopClerkAdapter.DefaultViewHolder viewHolder = new ShopClerkAdapter.DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShopClerkAdapter.DefaultViewHolder holder, final int position) {
        holder.setData(titles.get(position).getName(),position+1);

//        //checkbox监听
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//
//                }
//            }
//        });
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_id,tv_name;
        CheckBox checkBox;
        OnItemClickListener mOnItemClickListener;


        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_id= (TextView) itemView.findViewById(R.id.tv_id);

            checkBox = (CheckBox) itemView.findViewById(R.id.cbCheckBox);
        }

        public void setData(String name,int indexs) {
            tv_name.setText(name);
            tv_id.setText(indexs+"");

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}

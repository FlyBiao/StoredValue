package com.sw.android.storedvalue.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.sw.android.storedvalue.view.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * Author FGB
 * Description
 * Created at 2017/7/20 14:03
 * Version 1.0
 */

public class BaseRecyclerView {

    /**
     * 初始化RecyclerView
     * @param ct
     * @param recyclerView SwipeMenuRecyclerView控件
     * @param mSwipeRefreshLayout SwipeRefreshLayout 刷新布局
     * @param mOnRefreshListener 下拉刷新监听
     * @param isAddItemDecoration 是否添加分割线 true:Add,false no add
     */
    public static void initRecyclerView(Context ct, SwipeMenuRecyclerView recyclerView, SwipeRefreshLayout mSwipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener mOnRefreshListener, boolean isAddItemDecoration){
        recyclerView.setLayoutManager(new LinearLayoutManager(ct));// 布局管理器。
        recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);//下拉刷新
        if(isAddItemDecoration==true){
            recyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        }
    }



    public static void initRecyclerView(Context ct, SwipeMenuRecyclerView recyclerView, boolean isAddItemDecoration){
        recyclerView.setLayoutManager(new LinearLayoutManager(ct));// 布局管理器。
        recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        if(isAddItemDecoration==true){
            recyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        }
    }

}

package com.sw.android.storedvalue.bean;

import com.sw.android.storedvalue.base.BaseBean;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：激活posBean
 * 创建日期：2016/11/8 11:43
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class ActivePosBean extends BaseBean {
    private int TModel;//pos激活状态：0未激活，1激活

    public int getTModel() {
        return TModel;
    }

    public void setTModel(int TModel) {
        this.TModel = TModel;
    }
}

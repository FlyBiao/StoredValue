package com.sw.android.storedvalue.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：网络加载对话框
 * 创建日期：2016/5/6 15:35
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class WaitDialog extends ProgressDialog {

    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage("正在请求，请稍候…");
    }
}

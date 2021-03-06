package com.sw.android.storedvalue.utils;

import android.content.Context;
import android.util.Log;

import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：旺POS SDK工具
 * 创建日期：2016/10/14 09:22
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class PosSDKTools {
    public final static String Tag = "SdkTools";

    /**
     * 初始化SDK
     *
     * @param context
     */
    public static void initSdk(Context context) {
        /**
         * WeiposImpl的初始化（init函数）和销毁（destroy函数），
         * 最好分别放在一级页面的onCreate和onDestroy中执行。 其他子页面不用再调用，可以直接获取能力对象并使用。
         */
        WeiposImpl.as().init(context, new Weipos.OnInitListener() {

            @Override
            public void onInitOk() {
                Log.e(Tag, "onInitOk--------------");
            }

            @Override
            public void onError(String message) {
                // TODO Auto-generated method stub
                final String msg = message;
                Log.e(Tag, "onError--------------" + msg);
            }

            @Override
            public void onDestroy() {
                // TODO Auto-generated method stub
                Log.e(Tag, "onDestroy--------------");
            }
        });
    }
}

/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sw.android.storedvalue.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.sw.android.storedvalue.R;
import com.sw.android.storedvalue.utils.ACache;
import com.sw.android.storedvalue.utils.AbDataPrefsUtil;
import com.sw.android.storedvalue.utils.AbPrefsUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


/**
 * ================================================
 * 作    者：FGB
 * 描    述：BaseActivity
 * 创建日期：2016/5/29
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public abstract class BaseActivity extends FragmentActivity {

    /* Activity集合，便于管理 */
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();
    protected Activity mActivity;
    protected Context mContext;
    protected AbPrefsUtil prefs;
    protected AbDataPrefsUtil dataPrefs;
    //缓存
    protected  ACache mCache;
    protected Gson gson;
    protected Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);//注入view和事件

        mCache = ACache.get(this);
        activityList.add(this);
        mActivity = this;
        mContext = this;
        prefs = AbPrefsUtil.getInstance();
        dataPrefs = AbDataPrefsUtil.getInstance();
        gson=new Gson();
        bundle=new Bundle();
    }

    private View.OnClickListener mBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mMenuButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onRightButtonClick(v.getId());
        }
    };
    protected void onRightButtonClick(int what) {
    }


    @Override
    protected void onDestroy() {
        //取消EventBus订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void showMessageDialog(String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.know, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}

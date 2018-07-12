/**
 * Copyright © YOLANDA. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sw.android.storedvalue.utils;


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.sw.android.storedvalue.global.App;


/**
 * ================================================
 * 作    者：FGB
 * 描    述：Toast工具，专门做提示显示使用，可以设置Toast显示的位置
 * 创建日期：2016/6/6 09:29
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class ToastUtils {


    private static Context context = null;
    private static Toast toast = null;
    public static final int TOP = Gravity.TOP;
    public static final int CENTER = Gravity.CENTER;
    public static final int BOTTOM = Gravity.BOTTOM;

    public static void getToast(Context context, String text) {
        if (ToastUtils.context == context) {
            // toast.cancel();
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        } else {
            ToastUtils.context = context;
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void getLongToast(Context context, String text) {
        if (ToastUtils.context == context) {
            // toast.cancel();
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_LONG);
        } else {

            ToastUtils.context = context;
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 位置自由显示
     *
     * @param context
     * @param text
     * @param position
     */
    public static void show(Context context, String text, int position) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        if (position == TOP) {
            toast.setGravity(position, 0, 160);
        } else {
            toast.setGravity(position, 0, 0);
        }
        toast.show();
    }

    public static void show(CharSequence msg) {
        Toast.makeText(App.getInstance(), msg, Toast.LENGTH_LONG).show();
    }

    public static void show(int stringId) {
        Toast.makeText(App.getInstance(), stringId, Toast.LENGTH_LONG).show();
    }

}

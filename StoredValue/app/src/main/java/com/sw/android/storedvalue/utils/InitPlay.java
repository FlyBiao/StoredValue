package com.sw.android.storedvalue.utils;

import android.app.Activity;
import android.widget.EditText;

/**
 * Author FGB
 * Description
 * Created at 2017/7/21 9:51
 * Version 1.0
 */

public class InitPlay {



    public static void showMsg(final String msg, Activity activity, final EditText et) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et.setText("");
                et.setTextSize(20);
                et.setText(msg);
            }
        });
    }

}

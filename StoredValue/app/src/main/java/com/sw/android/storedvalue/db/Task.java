package com.sw.android.storedvalue.db;

import android.util.Log;

import java.util.TimerTask;

/**
 * Author FGB
 * Description
 * Created at 2017/11/20 22:46
 * Version 1.0
 */

public class Task extends TimerTask {
    public void run() {
        Log.i("test","我有一头小毛驴!");
        System.out.println("我有一头小毛驴!");
    }
}

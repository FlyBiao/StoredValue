package com.sw.android.storedvalue.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存工具类
 * @author FlyBiao
 *
 */
public class AbPrefsUtil {
	private static AbPrefsUtil prefsUtil;
	public Context context;
	public SharedPreferences prefs;
	public SharedPreferences.Editor editor;

	public synchronized static AbPrefsUtil getInstance() {
		return prefsUtil;
	}

	public static void init(Context context, String prefsname, int mode) {
		prefsUtil = new AbPrefsUtil();
		prefsUtil.context = context;
		prefsUtil.prefs = prefsUtil.context.getSharedPreferences(prefsname, mode);
		prefsUtil.editor = prefsUtil.prefs.edit();
	}

	private AbPrefsUtil() {
	}

	public boolean getBoolean(String key, boolean defaultVal) {
		return this.prefs.getBoolean(key, defaultVal);
	}

	public boolean getBoolean(String key) {
		return this.prefs.getBoolean(key, false);
	}

	public String getString(String key, String defaultVal) {
		return this.prefs.getString(key, defaultVal);
	}

	public String getString(String key) {
		return this.prefs.getString(key, null);
	}

	public int getInt(String key, int defaultVal) {
		return this.prefs.getInt(key, defaultVal);
	}

	public int getInt(String key) {
		return this.prefs.getInt(key, 0);
	}

	public float getFloat(String key, float defaultVal) {
		return this.prefs.getFloat(key, defaultVal);
	}

	public float getFloat(String key) {
		return this.prefs.getFloat(key, 0f);
	}

	public long getLong(String key, long defaultVal) {
		return this.prefs.getLong(key, defaultVal);
	}

	public long getLong(String key) {
		return this.prefs.getLong(key, 0l);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Set<String> getStringSet(String key, Set<String> defaultVal) {
		return this.prefs.getStringSet(key, defaultVal);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Set<String> getStringSet(String key) {
		return this.prefs.getStringSet(key, null);
	}

	public Map<String, ?> getAll() {
		return this.prefs.getAll();
	}

	public void putString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
		// return this;
	}

	public void putInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void putFloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	public void putLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 保存List
	 * @param tag
	 * @param datalist
	 */
	public <T> void setDataList(String tag, List<T> datalist) {
		if (null == datalist || datalist.size() <= 0)
			return;

		Gson gson = new Gson();
		//转换成json数据，再保存
		String strJson = gson.toJson(datalist);
//		editor.clear();
		editor.putString(tag, strJson);
		editor.commit();

	}

	/**
	 * 获取lIst
	 * @param tag
	 * @return
	 */
	public <T> List<T> getDataList(String tag) {
		List<T> datalist=new ArrayList<T>();
		String strJson = prefs.getString(tag, null);
		if (null == strJson) {
			return datalist;
		}
		Gson gson = new Gson();
		datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
		}.getType());

		return datalist;
	}

	/**
	 * 获取lIst
	 * @param tag
	 * @return
	 */
	public String getJsonArray(String tag) {
		String strJson = prefs.getString(tag, null);

		return strJson;
	}


	public void commit() {
		editor.commit();
	}

	/**
	 * 清楚所有缓存
	 */
	public void cleanAll() {
		editor.clear().commit();
	}

	/**
	 * 删除指定缓存数据
	 * @param key  要删除的key
     */
	public void removeKey(String key) {
		editor.remove(key).commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public AbPrefsUtil putStringSet(String key, Set<String> value) {
		editor.putStringSet(key, value);
		editor.commit();
		return this;
	}
}

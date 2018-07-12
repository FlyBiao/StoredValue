package com.sw.android.storedvalue.net.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.sw.android.storedvalue.utils.AbAppUtil;
import com.zhl.cbdialog.CBDialogBuilder;

/**
 * 网络相关工具类，网络检测、网络连接方式
 * 
 * @author Evan
 * 
 */
public class NetworkUtil {

	/** 网络不可用 */
	public static final int NO_NETWORK = 0;
	/** 是wifi连接 */
	public static final int WIFI_NETWORK = 1;
	/** 不是wifi连接 */
	public static final int NOWIFI = 5;
	public static final int G2_NETWORK = 2;// edge means gprs
	public static final int G3_NETWORK = 3;
	public static final int G4_NETWORK = 4;

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 检测网络是否存在
	 * 
	 * @param mActivity
	 */
	public static void checkNetwork(final Activity mActivity) {
		if (getAPNType(mActivity) == NO_NETWORK) {
			new CBDialogBuilder(mActivity)
					.setTouchOutSideCancelable(true)
					.showCancelButton(true)
					.setTitle("网络连接")
					.setMessage("抱歉，当前的网络连接不可用，是否进行网络设置？")
					.setConfirmButtonText("确定")
					.setCancelButtonText("取消")
					.setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
					.setButtonClickListener(true, new CBDialogBuilder.onDialogbtnClickListener() {
						@Override
						public void onDialogbtnClick(Context context, Dialog dialog, int whichBtn) {
							switch (whichBtn) {
								case BUTTON_CONFIRM:
									// 进入网络配置界面
									if (AbAppUtil.getSysVersion() > 10) {// 3.0以上版本用此方法做兼容
										mActivity.startActivity(new Intent(Settings.ACTION_SETTINGS));
									} else {
										mActivity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
										// startActivity(new
										// Intent(Settings.ACTION_WIFI_SETTINGS));
										// //进入手机中的wifi网络设置界面
									}
									break;
								case BUTTON_CANCEL:
									break;
								default:
									break;
							}
						}
					})
					.create().show();
		}

	}

	/**
	 * 检验网络连接 并判断是否是wifi连接
	 * 
	 * @param context
	 * @return
	 * 		<li>没有网络：Network.NONETWORK;</li>
	 *         <li>wifi 连接：Network.WIFI;</li>
	 *         <li>mobile 连接：Network.NOWIFI</li>
	 */
	public static int getAPNType(Context context) {

		if (!checkNetWork(context)) {
			return NetworkUtil.NO_NETWORK;
		}
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting())
			return NetworkUtil.WIFI_NETWORK;
		else
			return NetworkUtil.NOWIFI;
	}

	/**
	 * 检测网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context) {
		// 1.获得连接设备管理器
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null || !ni.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return 0(无网络)，1(wifi),2(gprs),3(3g)
	 */
	public static final int getSelfNetworkType(Context mContext) {
		if (mContext == null)
			return NO_NETWORK; // 网络未连接时当无网络
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null)
			return NO_NETWORK;
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null) {
			if (Build.VERSION.SDK_INT > 19) {// 判断sdk大于等于5.0
				activeNetInfo = connectivityManager.getNetworkInfo(0);
				if (activeNetInfo != null) {
					if ("Cellular".equalsIgnoreCase(activeNetInfo.getTypeName())) {
						return G3_NETWORK;
					}
				} else {
					return NO_NETWORK;
				}
			}
		}

		int netSubtype = -1;
		if (activeNetInfo != null) {
			netSubtype = activeNetInfo.getSubtype();
		}
		if (activeNetInfo != null && activeNetInfo.isConnected()) {
			if ("WIFI".equalsIgnoreCase(activeNetInfo.getTypeName())) {// wifi
				return WIFI_NETWORK;
			} else if (activeNetInfo.getTypeName() != null) {// 3g,双卡手机有时为mobile2
				if (activeNetInfo.getTypeName().toLowerCase().contains("mobile")) {
					if (netSubtype == TelephonyManager.NETWORK_TYPE_UMTS
							|| netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_0
							|| netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_A
							|| netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_B
							|| netSubtype == TelephonyManager.NETWORK_TYPE_HSDPA
							|| netSubtype == TelephonyManager.NETWORK_TYPE_HSUPA
							|| netSubtype == TelephonyManager.NETWORK_TYPE_HSPA || netSubtype == 17
							// 4.0系统 H+网络为15 TelephonyManager.NETWORK_TYPE_HSPAP
							|| netSubtype == 15) {
						return G3_NETWORK;
					} else if (netSubtype == TelephonyManager.NETWORK_TYPE_LTE) {
						return G4_NETWORK;
					} else {
						return G2_NETWORK;
					}
				} else if ("Cellular".equalsIgnoreCase(activeNetInfo.getTypeName())) {
					return G3_NETWORK;
				}
			}
		}
		return NO_NETWORK;// 网络未连接时当无网络
	}
}

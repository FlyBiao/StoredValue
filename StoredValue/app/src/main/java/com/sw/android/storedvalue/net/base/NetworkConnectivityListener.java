package com.sw.android.storedvalue.net.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;


public class NetworkConnectivityListener {

	private Context context_;

	private List<NetworkCallBack> networkCallBacks = new ArrayList<NetworkCallBack>();

	private boolean isListening_;
	private boolean isFrist = true;
	private ConnectivityBroadcastReceiver receiver_;

	private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if ((!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) || !isListening_) {
				return;
			}
			if (isFrist) {
				isFrist = false;
				return;
			}
			int type = NetworkUtil.getSelfNetworkType(context);
			NotifyEvent(type);
		}
	};

	private void NotifyEvent(int type) {
		for (NetworkCallBack callback : networkCallBacks) {
			callback.getSelfNetworkType(type);
		}
	}

	public NetworkConnectivityListener() {
		receiver_ = new ConnectivityBroadcastReceiver();
	}

	public synchronized void startListening(Context context) {
		if (!isListening_) {
			context_ = context;
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			context.registerReceiver(receiver_, filter);
			isListening_ = true;
		}
	}

	public synchronized void stopListening() {
		if (isListening_) {
			context_.unregisterReceiver(receiver_);
			isListening_ = false;
		}
	}

	public void registerNetworkCallBack(NetworkCallBack callBack) {
		networkCallBacks.add(callBack);
	}

	public void unregisterNetworkCallBack(NetworkCallBack callBack) {
		if (networkCallBacks.contains(callBack)) {
			networkCallBacks.remove(callBack);
		}
	}

	public interface NetworkCallBack {
		public void getSelfNetworkType(int type);
	}
}
package com.sw.android.storedvalue.net.base;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.sw.android.storedvalue.dialog.WaitDialog;
import com.sw.android.storedvalue.global.App;
import com.sw.android.storedvalue.global.Constant;
import com.sw.android.storedvalue.global.Urls;
import com.sw.android.storedvalue.utils.AbDataPrefsUtil;
import com.sw.android.storedvalue.utils.AbPrefsUtil;
import com.sw.android.storedvalue.utils.MD5;
import com.sw.android.storedvalue.utils.ToastUtils;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

/**
 * 网络请求基类
 * 
 * @author Evan
 *
 */
public class BaseValueNet {

	private static final String TAG = "BaseNet";
	public Context mContext;
	protected String uri;
	protected JSONObject data;
	private boolean ishow;
	private WaitDialog dialog;
	protected AbDataPrefsUtil abData;

	public BaseValueNet(Context context, boolean show) {
		this.mContext = context;
		data = new JSONObject();
		abData = AbDataPrefsUtil.getInstance();
		this.ishow = show;
		if (show)
			dialog = new WaitDialog(context);
	}

	/**
	 * 获取授权
	 * @return
	 */
	public String getToken() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN))
		.append("SW-AppAuthorizationToken")
				.append(AbPrefsUtil.getInstance().getString(Constant.SPF_TIME, ""));
		return new MD5().toMD5(sbuf.toString());
	}
	
	/**
	 * 开始请求网络
	 */
	public void mPostNet() {
		if (App.mInstance().getNetType() != NetworkUtil.NO_NETWORK) {
			RequestParams params = new RequestParams();
			try {
				params.setBodyEntity(new StringEntity(data.toString(), "UTF-8"));
				params.addHeader("Content-Type", "application/json");
				String token = this.getToken();
				String time = AbPrefsUtil.getInstance().getString(Constant.SPF_TIME, "");
				if (!"".equals(token) && !"".equals(time)) {
					StringBuffer auth = new StringBuffer();
					auth.append("SW-Authorization ").append(token).append(";").append(time);
					params.addHeader("Authorization", auth.toString());
					
//					Log.i(Constant.TAG, "auth=="+data+"auth=="+auth.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Log.i(Constant.TAG,"发送请求:" +"URL=:"+ Urls.SERVER_S + uri + "--数据参数=:" + data);
			
			HttpUtils http = new HttpUtils();
			http.send(HttpMethod.POST, Urls.SERVER_S + uri, params, new RequestCallBack<HttpUtils>() {
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					if (isUploading) {
					} else {
					}
				}

				//加载刷新
				@Override
				public void onStart() {
					super.onStart();
					if (ishow && dialog != null)
						dialog.show();
				}


				@Override
				public void onFailure(HttpException arg0, String arg1) {
					try {
						if (ishow && dialog != null)
							dialog.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mFail(arg0, arg1);
				}

				@Override
				public void onSuccess(ResponseInfo<HttpUtils> responseInfo) {
					try {
						if (ishow && dialog != null)
							dialog.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mSuccess(responseInfo.result + "");
				}
			});
		} else {
			ToastUtils.show("未Intent网络，请检查后重试！");
		}
	}

	public String getAccount() {
		return AbPrefsUtil.getInstance().getString(Constant.SPF_ACCOUNT);
	}

	

	protected void mSuccess(String rJson) {
		LogUtils.d("成功：" + rJson);
//		Log.i("com.cesaas.android.ep.ekawuyou.global.BaseNet", "rJson==:"+rJson);
	}

	protected void mFail(HttpException e, String err) {
		LogUtils.d("失败：" + err);
		ToastUtils.show( "服务器连接或返回错误！");
	}

}

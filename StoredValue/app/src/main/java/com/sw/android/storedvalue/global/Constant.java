package com.sw.android.storedvalue.global;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：常量
 * 创建日期：2016/5/6 15:35
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class Constant {

	public static String TAG="POSBugInfo";
	/** 日志开关 */
	public static boolean DEBUG = false;
	/** 图片处理：裁剪 */
	public static final int CUTIMG = 0;
	/** 图片处理：缩放 */
	public static final int SCALEIMG = 1;
	// 用户头像
	public static String SPF_UIMG = "img";

	//表示是否登录
	public static String IS_LOGIN = "isLogin";
	/** 标示是否登录 */
	/** 帐号（手机号） */
	public static String SPF_ACCOUNT = "account";
	// 昵称
	public static String SPF_NICK = "nick";
	//请求的TOKEN
	public static String SPF_TOKEN= "token";
	//时间参数
	public static String SPF_TIME = "time";

	//================订单状态====================//
// 全部,(已取消订单)
	public static String XFOS_ALL = "0";
	// 新增订单(未支付订单)
	public static String XFOS_ADDED = "10";
	// 支付中(第三方支付已返回支付结果，服务器还未获取到第三方支付回调，此时订单状态还不是支付成功)
	public static String XFOS_PAYING = "20";
	// 已支付
	public static String XFOS_PAYED = "30";
	// 已发货
	public static String XFOS_SHIPMENTS = "40";
	// 交易退款后关闭
	public static String XFOS_CLOSE = "80";
	// 买家或卖家主动取消
	public static String XFOS_CANCEL = "60";
	// 完成
	public static String XFOS_FINISHED = "100";
	/**
	 * 等待顾问接单
	 */
	public static int WAIT_CONSULTANT_RECEIVING = 0;
	/**
	 * 顾问已接单
	 */
	public static int CONSULTANT_RECEIVED = 10;

	/**
	 * 顾问已确认，待卖家发货
	 */
	public static int StringCONSULTANT_CONFIRM = 20;
	/**
	 * 顾问确认无货，待卖家确认
	 */
	public static int StringOUT_OF_STOCK = 30;

	//================分销订单状态OrderStatus====================//
	/**
	 * 10:订单已创建，待买家付款
	 */
	public static int WAIT_BUYER_PAY=10;
	/**
	 * 20:订单支付中
	 */
	public static int TRADE_PAYING = 20;
	/**
	 * 30:买家已付款，待卖家发货
	 */
	public static int WAIT_SELLER_SEND = 30;
	/**
	 * 40:卖家已发货，待卖家确认
	 */
	public static int WAIT_BUYER_CONFIRM =40 ;
	/**
	 * 80:交易退款后关闭
	 */
	public static int TRADE_CLOSED = 80;
	/**
	 *  90:交易取消，买家卖家主动取消
	 */
	public static int TRADE_CANCEL = 90;
	/**
	 * 100:交易完成
	 */
	public static int TRADE_FINISHED =100 ;

	//================订单快递方式====================//
	/**
	 * 0:快递配送
	 */
	public static int Express = 0;
	/**
	 * 1:到店取货
	 */
	public static int StoreExtract=1;

	//================我的收益佣金状态====================//
	/**
	 * 0:暂时冻结
	 */
	public static int FREEZE=0;
	/**
	 * 1:可以结算
	 */
	public static int SETTLEMENT=1;
	/**
	 * 2:已经收
	 */
	public static int RECEIVE=2;
	/**
	 * 3:不能结算
	 */
	public static int NO_SETTLEMENT=3;



	//================POS支付方式====================//
	/**
	 * 积分支付
	 */
	public static int POINTPAY=1;
	/**
	 * 微信支付
	 */
	public static int WEIXINPAY=2;
	/**
	 * 支付宝
	 */
	public static int ALIPAY=3;
	/**
	 * 银联支付
	 */
	public static int UNIONPAY=4;
	/**
	 * 现金支付
	 */
	public static int CASH=5;

//	#收银SDK错误码========================================================

//	1000 通用错误
	public static int BASE_ERROR=2000;
//	2001 未配置收银通道
	public static int NO_CHECK_OUT=2001;
//	2002 插件下载失败
	public static int DOWNLOAD_FAILURE=2002;
//	5000 密码键盘通用错误
	public static int PWD_ERROR=5001;
//	5001 用户取消输密
	public static int USER_CANCEL_INPUT_PWD=5001;
//	5002 输密超时
	public static int PWD_INPUT_OUT_TIME=5002;
//	2003 不支持的插件版本
//	2004 收银台与收银核心通讯失败
//	2005 核心与收银插件通信失败
//	2006 核心与配置服务器通信失败
//	2007 配置解析失败
//	2008 获取商户号,终端号时,渠道号不能为测试值
//	2009 获取商户号终端号信息失败
//
//	3000 安全模块通用错误
//	3001 保存主密钥失败
//	3002 保存工作密钥失败
//	3003 保存工作密钥失败,校验错
//	3004 渠道ID为null 没有终端主密钥索引标记
//	3005 传输密钥解密终端主密钥失败
//
//	4000 读卡通用错误
//	4001 用户取消读卡
	public static int USER_CANCEL_READ_CARD=4001;
//	4002 读卡超时
	public static int READ_CARD_OUT_TIME=4002;
//
//	5000 密码键盘通用错误
//	5001 用户取消输密
//	5002 输密超时
//	5003 PIK错误
//
//	6000 打印通用错误
//	6001 打印机缺纸
	public static int PRINTER_PAPER_OUT=6001;
//	6002 打印机过热
	public static int PRINTER_GUO_RE=6002;
//	6003 上层应用取消打印
//
//	7000 交易通用错误
//	7001 网络错误,连接服务器失败
//	7002 网络错误,服务器返回数据超时
//	7003 数据格式错误
//	7004 MAC校验错
//	7005 交易失败
	public static int PAY_FAILURE=7005;
//	7006 冲正失败
//	7007 缓存数据达到500,请进行批结算
//	7008 签到错误
//	7009 上层应用，批结算传入的信息有误
//	7010 没有需要批结算的记录
//	7011 插件没有实现批结算接口
//	7012 网络正常，返回数据状态失败

}

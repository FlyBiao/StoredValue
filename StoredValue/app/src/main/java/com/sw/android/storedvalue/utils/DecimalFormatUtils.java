package com.sw.android.storedvalue.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 十进制格式工具
 * @author FGB
 *
 */
public class DecimalFormatUtils {

	/**
	 * 非四舍五入保留两位小数
	 * @param d
	 */
	public static void decimalFormat(double d){
		DecimalFormat df = new DecimalFormat("######0.00");
		df.format(d);
	}

	public static String decimalToFormats(double d){
		DecimalFormat df = new DecimalFormat("######0");
		return df.format(d);
	}
	
	public static String decimalToFormat(double d){
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(d);
	}
	
	/**
	 * 四舍五入保留两位小数
	 * @param d
	 * @return
	 */
	public static double decimalFormatRound(double d){
		BigDecimal b=new BigDecimal(d);
		return b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 四舍五入保留两位小数
	 * @param d
	 * @return
	 */
	public static double decimalFormatRounds(double d){
		BigDecimal b=new BigDecimal(d);
		return b.setScale(0,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}

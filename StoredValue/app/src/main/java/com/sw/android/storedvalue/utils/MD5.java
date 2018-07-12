package com.sw.android.storedvalue.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private MessageDigest messageDigest;
	private StringBuffer hexValue;

	public String toMD5(String value) {
		char[] charArray = value.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int numA = 0; numA < charArray.length; numA++)
			byteArray[numA] = (byte) charArray[numA];
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] md5Bytes = messageDigest.digest(byteArray);
		hexValue = new StringBuffer();
		for (int numB = 0; numB < md5Bytes.length; numB++) {
			int val = ((int) md5Bytes[numB]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String getMD5(String path) {

		return path;
	}
}
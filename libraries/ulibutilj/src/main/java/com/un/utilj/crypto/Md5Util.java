package com.un.utilj.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	/**
	 * 将hash后的字节数组转为字符串
	 *
	 * @param data
	 *
	 * @return
	 */
	public static String md5BytesToString(byte[] data) {
		if (data == null || data.length <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (byte d : data) {
			sb.append(String.format("%02x", d));
		}
		return sb.toString();
	}

	public static byte[] hash(String oriStr) {
		if (oriStr == null || oriStr.isEmpty()) {
			return null;
		}
		MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (mdInst == null) {
			return null;
		}

		byte[] btInput = oriStr.getBytes();
		mdInst.update(btInput);
		return mdInst.digest();
	}

	public static byte[] hash(byte[] data) {
		if (data == null || data.length <= 0) {
			return null;
		}
		MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (mdInst == null) {
			return null;
		}

		mdInst.update(data);
		return mdInst.digest();
	}

	public static String hashStr(String oriStr) {
		return md5BytesToString(hash(oriStr));
	}

	public static String hashStr(byte[] oriStr) {
		return md5BytesToString(hash(oriStr));
	}
}

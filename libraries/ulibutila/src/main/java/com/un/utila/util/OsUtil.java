package com.un.utila.util;

import android.os.Build;

/**
 * 操作系统相关
 */
public class OsUtil {

	/**
	 * android5及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.LOLLIPOP
	 */
	public static boolean isGeAndroid5() {
		return android.os.Build.VERSION.SDK_INT >= 21;
	}

	/**
	 * android5.1及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.LOLLIPOP_MR1
	 */
	public static boolean isGeAndroid5_1() {
		return android.os.Build.VERSION.SDK_INT >= 22;
	}

	/**
	 * android6及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.M
	 */
	public static boolean isGeAndroid6() {
		return android.os.Build.VERSION.SDK_INT >= 23;
	}

	/**
	 * android7及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.N
	 */
	public static boolean isGeAndroid7() {
		return android.os.Build.VERSION.SDK_INT >= 24;
	}

	/**
	 * android7.1及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.N_MR1
	 */
	public static boolean isGeAndroid7_1() {
		return android.os.Build.VERSION.SDK_INT >= 25;
	}

	/**
	 * android8及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.O
	 */
	public static boolean isGeAndroid8() {
		return android.os.Build.VERSION.SDK_INT >= 26;
	}

	/**
	 * android8.1及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.O_MR1
	 */
	public static boolean isGeAndroid8_1() {
		return android.os.Build.VERSION.SDK_INT >= 27;
	}

	/**
	 * android9及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.P
	 */
	public static boolean isGeAndroid9() {
		return android.os.Build.VERSION.SDK_INT >= 28;
	}

	/**
	 * android10及以上
	 *
	 * @return
	 *
	 * @see Build.VERSION_CODES.Q
	 */
	public static boolean isGeAndroid10() {
		return android.os.Build.VERSION.SDK_INT >= 29;
	}
}

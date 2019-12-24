package com.un.utilj.datetime;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 用于格式化日期的工具类
 */
public class DateFormatUtil {

	public static final String PATTERN_DATE_0 = "yyyy/MM/dd";

	public static final String PATTERN_DATE_1 = "yyyyMMdd";

	public static final String PATTERN_TIME_0 = "HH:mm";

	public static final String PATTERN_DT_0 = "yyyyMMddHH";

	public static String getFormatDatetime(String format, long milli, TimeZone timeZone, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		sdf.setTimeZone(timeZone);
		return sdf.format(milli);
	}

	public static String getFormatDatetime(String format, long milli, TimeZone timeZone) {
		return getFormatDatetime(format, milli, timeZone, Locale.getDefault());
	}

	public static String getFormatDatetime(String format, long milli) {
		return getFormatDatetime(format, milli, TimeZone.getDefault(), Locale.getDefault());
	}

	public static String getFormatDate_yMd(long milli) {
		return getFormatDatetime(PATTERN_DATE_0, milli);
	}

	public static String getFormatDate_yMd1(long milli) {
		return getFormatDatetime(PATTERN_DATE_1, milli);
	}

	public static String getFormatTime_Hm(long milli) {
		return getFormatDatetime(PATTERN_TIME_0, milli);
	}

	/**
	 * 用24:00代替00:00
	 *
	 * @param milli
	 *
	 * @return
	 */
	public static String getFormatTime_Hm24(long milli) {
		String result = getFormatDatetime(PATTERN_TIME_0, milli);
		if (result.startsWith("00")) {
			result = result.replaceFirst("00", "24");
		}
		return result;
	}
}

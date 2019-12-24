package com.un.utilj.datetime;

import java.util.Calendar;

/**
 * 日期时间处理工具类
 */

public class CalendarUtil {

	public static void lastDay(Calendar calendar) {
		if (calendar == null) {
			return;
		}
		calendar.add(Calendar.DATE, -1);
	}

	public static void nextDay(Calendar calendar) {
		if (calendar == null) {
			return;
		}
		calendar.add(Calendar.DATE, 1);
	}

	/**
	 * 将日期以天取整
	 *
	 * @param calendar
	 */
	public static Calendar formatToDay(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		Calendar resultCalendar = (Calendar) calendar.clone();
		resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		resultCalendar.set(Calendar.MILLISECOND, 0);
		return resultCalendar;
	}

	/**
	 * 是否为同一天
	 */
	public static boolean isSameDay(Calendar calendarBase, Calendar calendarCompare) {
		if (calendarBase == null || calendarCompare == null) {
			return false;
		}
		Calendar calendarB = formatToDay(calendarBase);
		Calendar calendarC = formatToDay(calendarCompare);
		return calendarC.equals(calendarB);
	}

	/**
	 * 是否为过去的日期
	 */
	public static boolean isPassedDay(Calendar calendarBase, Calendar calendarCompare) {
		if (calendarBase == null || calendarCompare == null) {
			return false;
		}
		Calendar calendarB = formatToDay(calendarBase);
		Calendar calendarC = formatToDay(calendarCompare);
		return calendarC.before(calendarB);
	}

	/**
	 * 是否为未来的日期
	 */
	public static boolean isFutureDay(Calendar calendarBase, Calendar calendarCompare) {
		if (calendarBase == null || calendarCompare == null) {
			return false;
		}
		Calendar calendarB = formatToDay(calendarBase);
		Calendar calendarC = formatToDay(calendarCompare);
		return calendarC.after(calendarB);
	}

	/**
	 * 设置时分秒
	 */
	public static void setHms(Calendar calendar, int hour, int minute, int second) {
		if (calendar == null) {
			return;
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public static void increaseMilli(Calendar calendar, long increaseMilli) {
		long milli = calendar.getTimeInMillis() + increaseMilli;
		calendar.setTimeInMillis(milli);
	}
}

package com.un.utilj.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 分隔符
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

	public static void addHour(Calendar calendar, int hour) {
		if (calendar == null) {
			return;
		}
		calendar.add(Calendar.HOUR_OF_DAY, hour);
	}

	public static void nextHour(Calendar calendar) {
		CalendarUtil.addHour(calendar, 1);
	}

	public static void lastHour(Calendar calendar) {
		CalendarUtil.addHour(calendar, -1);
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
		resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);
		resultCalendar.set(Calendar.MILLISECOND, 0);
		return resultCalendar;
	}

	/**
	 * 将日期以天取整
	 *
	 * @param calendar
	 */
	public static void changeFormatToDay(Calendar calendar) {
		if (calendar == null) {
			return;
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
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
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
				hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * 更改时间
	 *
	 * @param calendar
	 * @param changeMilli
	 */
	public static void changeMilli(Calendar calendar, long changeMilli) {
		long milli = calendar.getTimeInMillis() + changeMilli;
		calendar.setTimeInMillis(milli);
	}

	public static void changeMilliLimitMin(Calendar calendar, long increaseMilli, Calendar limit) {
		long milli = calendar.getTimeInMillis() + increaseMilli;
		milli = Math.max(milli, limit.getTimeInMillis());
		calendar.setTimeInMillis(milli);
	}

	public static void changeMilliLimitMax(Calendar calendar, long increaseMilli, Calendar limit) {
		long milli = calendar.getTimeInMillis() + increaseMilli;
		milli = Math.min(milli, limit.getTimeInMillis());
		calendar.setTimeInMillis(milli);
	}

	public static void translateMilliLimit(
			Calendar startCalendar, Calendar endCalendar,
			long changeMilli,
			Calendar minStart, Calendar maxEnd) {
		long startMilli = startCalendar.getTimeInMillis() + changeMilli;
		long endMilli = endCalendar.getTimeInMillis() + changeMilli;
		if (startMilli < minStart.getTimeInMillis() || endMilli > maxEnd.getTimeInMillis()) {
			return;
		}
		startCalendar.setTimeInMillis(startMilli);
		endCalendar.setTimeInMillis(endMilli);
	}

	public static void scaleMilliLimit(
			Calendar startCalendar, Calendar endCalendar,
			long changeMilli,
			long mixLimitMilli,
			Calendar minStart, Calendar maxEnd) {
		long startMilli = startCalendar.getTimeInMillis() + changeMilli;
		long endMilli = endCalendar.getTimeInMillis() - changeMilli;
		long delta = endMilli - startMilli;
		if (delta < mixLimitMilli) {
			return;
		}
		CalendarUtil.changeMilliLimitMin(startCalendar, changeMilli, minStart);
		CalendarUtil.changeMilliLimitMax(endCalendar, -changeMilli, maxEnd);
	}

	/**
	 * 将日期以小时取整
	 *
	 * @param calendar
	 */
	public static Calendar formatToHour(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		Calendar resultCalendar = (Calendar) calendar.clone();
		resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), 0, 0);
		resultCalendar.set(Calendar.MILLISECOND, 0);
		return resultCalendar;
	}

	/**
	 * 将日期以分钟取整
	 *
	 * @param calendar
	 */
	public static Calendar formatToMinu(Calendar calendar, int step) {
		if (calendar == null) {
			return null;
		}
		Calendar resultCalendar = (Calendar) calendar.clone();
		int minu = calendar.get(Calendar.MINUTE)/step*step;
		resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), minu, 0);
		resultCalendar.set(Calendar.MILLISECOND, 0);
		return resultCalendar;
	}


	/**
	 * Calendar转Date
	 *
	 * @param calendar
	 *
	 * @return
	 */
	public static Date formatCalendarToDate(Calendar calendar) {
		return calendar.getTime();
	}

	/**
	 * Date转Calendar
	 *
	 * @param date
	 *
	 * @return
	 *
	 * @throws ParseException
	 */
	public static Calendar formatDateToCalendar(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Date转日期("yyyy-MM-dd")
	 *
	 * @param date
	 *
	 * @return
	 */
	public static String formatDateToDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		return format.format(date);
	}

	/**
	 * 日期("yyyy-MM-dd")转Date
	 *
	 * @param dateString
	 *
	 * @return
	 *
	 * @throws ParseException
	 */
	public static Date formatDateStringToDate(String dateString) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		return format.parse(dateString);
	}

	/**
	 * Calendar转日期("yyyy-MM-dd")
	 *
	 * @param calendar
	 *
	 * @return
	 */
	public static String formatCalendarToDateString(Calendar calendar) {
		return formatDateToDateString(formatCalendarToDate(calendar));
	}

	/**
	 * 日期("yyyy-MM-dd")转Calendar
	 *
	 * @param dateString
	 *
	 * @return
	 *
	 * @throws ParseException
	 */
	public static Calendar formatDateStringToCalendar(String dateString) throws ParseException {
		return formatDateToCalendar(formatDateStringToDate(dateString));
	}

	/**
	 * Date转时间("HH:mm")
	 *
	 * @param date
	 *
	 * @return
	 */
	public static String formatDateToShortTimeString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
		return format.format(date);
	}

	/**
	 * 时间("HH:mm")转Date
	 *
	 * @param timeString
	 *
	 * @return
	 *
	 * @throws ParseException
	 */
	public static Date formatShortTimeStringToDate(String timeString) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
		return format.parse(timeString);
	}

	/**
	 * Calendar转时间("HH:mm")
	 *
	 * @param calendar
	 *
	 * @return
	 */
	public static String formatCalendarToShortTimeString(Calendar calendar) {
		return formatDateToShortTimeString(formatCalendarToDate(calendar));
	}

	/**
	 * 时间("HH:mm")转Calendar
	 *
	 * @param timeString
	 * 		日期字符串
	 *
	 * @return
	 *
	 * @throws ParseException
	 */
	public static Calendar formatShortTimeStringToCalendar(String timeString) throws ParseException {
		return formatDateToCalendar(formatShortTimeStringToDate(timeString));
	}

	/**
	 * 时间戳(long)转Date
	 *
	 * @param timestamp
	 * 		时间戳
	 *
	 * @return
	 */
	public static Date formatTimestampToDate(long timestamp) {
		return new Date(timestamp);
	}

	/**
	 * Date转月份("yyyy/MM")
	 *
	 * @param date
	 * 		时间戳
	 *
	 * @return
	 */
	public static String formatDateToMonthString(Date date) {
		// FIXME: 2020/4/2 debug需要，release改为"yyyy/MM"
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
		return format.format(date);
	}

	/**
	 * 时间戳(long)转月份("yyyy/MM")
	 *
	 * @param timestamp
	 * 		时间戳
	 *
	 * @return
	 */
	public static String formatTimestampToMonthString(long timestamp) {
		return formatDateToMonthString(formatTimestampToDate(timestamp));
	}

}

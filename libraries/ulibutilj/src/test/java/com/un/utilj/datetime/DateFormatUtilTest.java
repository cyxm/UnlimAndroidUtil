package com.un.utilj.datetime;

import org.junit.Assert;
import org.junit.Test;

public class DateFormatUtilTest {

	@Test
	public void getFormatDatetime() {
		long[] boundParams = new long[]{
				0,
				1,
				-1,
				Long.MIN_VALUE,
				Long.MAX_VALUE
		};

		for (long param : boundParams) {
			String formatDate = DateFormatUtil.getFormatDatetime(null, param);
			Assert.assertEquals("", formatDate);

			formatDate = DateFormatUtil.getFormatDatetime("", param);
			Assert.assertEquals("", formatDate);
		}

		//		{
		//			String formatDate = DateFormatUtil.getFormatDatetime("yyyy/MM/dd hh:mm:ss", Long.MAX_VALUE);
		//			Assert.assertEquals("292278994/08/17 03:12:55", formatDate);
		//		}
		//
		//		{
		//			String formatDate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL).format(new Date((long) 2E12));
		//			Assert.assertEquals("292278994/08/17 03:12:55", formatDate);
		//		}
	}
}
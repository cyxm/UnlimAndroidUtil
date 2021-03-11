package com.un.utilj.datetime;

import org.junit.Assert;
import org.junit.Test;

public class DateFormatUtilTest {

	@Test
	public void getFormatDatetime() {
		{
			String formatDate = DateFormatUtil.getFormatDatetime(null, 1568648L);
			Assert.assertEquals("", formatDate);
		}
		{
			String formatDate = DateFormatUtil.getFormatDatetime("", 1568648L);
			Assert.assertEquals("", formatDate);
		}
	}
}
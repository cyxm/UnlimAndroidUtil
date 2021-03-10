package com.un.utilj.net;

import org.junit.Assert;
import org.junit.Test;

public class IpAddrUtilTest {

	@Test
	public void str2Int() {
		String[] falseParams = new String[]{
				null,
				"",
				"1",
				"1.1",
				"1.1.1",
				"1.1.1.",
				"1.1.1.256",
				"...."
		};
		for (String param : falseParams) {
			int result = IpAddrUtil.str2Int(param);
			Assert.assertEquals(0, result);
		}
		//
		{
			int result = IpAddrUtil.str2Int("1.1.1.255");
			Assert.assertEquals(-16711423, result);
		}
		{
			int result = IpAddrUtil.str2Int("255.255.255.255");
			Assert.assertEquals(-1, result);
		}
		{
			int result = IpAddrUtil.str2Int("192.168.1.8");
			Assert.assertEquals(134326464, result);
		}
	}

	@Test
	public void intToStr() {
		{
			String result = IpAddrUtil.intToStr(0);
			Assert.assertEquals("0.0.0.0", result);
		}
		{
			String result = IpAddrUtil.intToStr(Integer.MAX_VALUE);
			Assert.assertEquals("255.255.255.127", result);
		}
		{
			String result = IpAddrUtil.intToStr(Integer.MIN_VALUE);
			Assert.assertEquals("0.0.0.128", result);
		}
		{
			String result = IpAddrUtil.intToStr(-1);
			Assert.assertEquals("255.255.255.255", result);
		}
		{
			String result = IpAddrUtil.intToStr(134326464);
			Assert.assertEquals("192.168.1.8", result);
		}
	}
}
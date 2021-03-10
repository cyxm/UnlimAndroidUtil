package com.un.utilj.regular;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RegularUtilTest {

	@Test
	public void isMatch() {
		//边界条件检查
		String[][] falseParams = new String[][]{
				{null, null},
				{null, ""},
				{"", null}
		};
		String[][] trueParams = new String[][]{
				{"", ""}
		};
		for (String[] paramGroup : falseParams) {
			Assert.assertFalse(RegularUtil.isMatch(paramGroup[0], paramGroup[1]));
		}
		for (String[] paramGroup : trueParams) {
			Assert.assertTrue(RegularUtil.isMatch(paramGroup[0], paramGroup[1]));
		}
	}

	@Test
	public void isNumber() {
		//边界条件检查
		String[] falseParams = new String[]{
				null,
				"",
				"a",
				"A",
				"_",
				"-",
				"1.1",
				"2-2"
		};
		String[] trueParams = new String[]{
				"1",
				"11111",
				"1234567890"
		};
		for (String param : falseParams) {
			Assert.assertFalse(param, RegularUtil.isNumber(param));
		}
		for (String param : trueParams) {
			Assert.assertTrue(param, RegularUtil.isNumber(param));
		}
	}

	@Test
	public void splitWithUpcase() {
		//边界条件检查
		{
			List<String> result = RegularUtil.splitWithUpcase(null);
			Assert.assertArrayEquals(new String[]{}, result.toArray());
		}
		{
			List<String> result = RegularUtil.splitWithUpcase("");
			Assert.assertArrayEquals(new String[]{}, result.toArray());
		}
		//用例
		{
			List<String> result = RegularUtil.splitWithUpcase("aboutSomething");
			Assert.assertArrayEquals(new String[]{"Something"}, result.toArray());
		}
		{
			List<String> result = RegularUtil.splitWithUpcase("AboutSomething");
			Assert.assertArrayEquals(new String[]{"About", "Something"}, result.toArray());
		}
		{
			List<String> result = RegularUtil.splitWithUpcase("About_Something");
			Assert.assertArrayEquals(new String[]{"About", "Something"}, result.toArray());
		}
		{
			List<String> result = RegularUtil.splitWithUpcase("AboutTSomething");
			Assert.assertArrayEquals(new String[]{"About", "T", "Something"}, result.toArray());
		}
		{
			List<String> result = RegularUtil.splitWithUpcase("Aboutsomething");
			Assert.assertArrayEquals(new String[]{"Aboutsomething"}, result.toArray());
		}
		{
			List<String> result = RegularUtil.splitWithUpcase("_Aboutsomething");
			Assert.assertArrayEquals(new String[]{"Aboutsomething"}, result.toArray());
		}
	}
}
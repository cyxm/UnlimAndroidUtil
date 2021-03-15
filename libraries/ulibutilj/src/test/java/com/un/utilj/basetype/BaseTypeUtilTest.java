package com.un.utilj.basetype;

import org.junit.Assert;
import org.junit.Test;

public class BaseTypeUtilTest {

	@Test
	public void getByteSize() {
		{
			float f = 8.3F;
			int i = (int) f;
			Assert.assertEquals(8, i);
		}
		{
			double d = 8.8;
			int i = (int) d;
			Assert.assertEquals(8, i);
		}
	}
}
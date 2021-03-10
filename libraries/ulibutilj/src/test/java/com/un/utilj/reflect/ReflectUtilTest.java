package com.un.utilj.reflect;

import org.junit.Assert;
import org.junit.Test;

public class ReflectUtilTest {

	@Test
	public void constructor() {
		String obj = new String(new char[]{'a', 'b', 'c'}, 0, 1);
		String objReflect = (String) ReflectUtil.constructor(
				String.class,
				new Class[]{char[].class, int.class, int.class},
				new Object[]{new char[]{'a', 'b', 'c'}, 0, 1}
		);
		Assert.assertEquals(obj, objReflect);
	}
}
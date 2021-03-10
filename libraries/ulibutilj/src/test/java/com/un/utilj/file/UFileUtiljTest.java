package com.un.utilj.file;

import org.junit.Assert;
import org.junit.Test;

public class UFileUtiljTest {

	@Test
	public void createDirs() {
		String fullPath = "D:/testDir";
		UFileUtilj.createDirs(fullPath);
		boolean result = UFileUtilj.isFileOrDirExist(fullPath);
		Assert.assertTrue(result);
	}
}
package com.un.utilj.file;

import java.io.File;
import java.io.IOException;

/**
 * 文件和目录相关的操作
 */
public class UFileUtilj {

	/**
	 * 创建目录
	 *
	 * @param file
	 * 		File对象
	 *
	 * @return 是否创建成功
	 */
	public static boolean createDirs(File file) {
		return file != null && (file.exists() || file.mkdirs());
	}

	/**
	 * 创建目录
	 *
	 * @param path
	 * 		目录路径
	 *
	 * @return 是否创建成功
	 */
	public static boolean createDirs(String path) {
		if (path == null) {
			return false;
		}
		File file = new File(path);
		return createDirs(file);
	}

	/**
	 * 判断文件或目录是否存在
	 *
	 * @param fullPath
	 * 		文件或目录的完整路径,可以为null
	 *
	 * @return 文件或目录是否存在
	 */
	public static boolean isFileOrDirExist(String fullPath) {
		if (fullPath == null || fullPath.isEmpty()) {
			return false;
		}
		File file = new File(fullPath);
		return file.exists();
	}

	/**
	 * 创建文件
	 *
	 * @param filepath
	 *
	 * @return
	 */
	public static boolean createFile(String filepath) {
		File file = new File(filepath);
		File dir = file.getParentFile();
		if (dir == null) {
			return false;
		}
		boolean createRet = createDirs(dir);
		if (!createRet) {
			return false;
		}
		if (!file.exists()) {
			boolean isCreate = false;
			try {
				isCreate = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return isCreate;
		} else {
			return true;
		}
	}

	/**
	 * 递归删除文件或文件夹
	 *
	 * @param filepath
	 *
	 * @return
	 */
	public static boolean deleteFileOrDirs(String filepath) {
		File file = new File(filepath);
		if (file.isDirectory()) {
			File[] childrenFile = file.listFiles();
			if (childrenFile == null || childrenFile.length == 0) {
				return file.delete();
			}
			for (File child : childrenFile) {
				deleteFileOrDirs(child.getAbsolutePath());
			}
			return true;
		} else {
			return file.delete();
		}
	}
}

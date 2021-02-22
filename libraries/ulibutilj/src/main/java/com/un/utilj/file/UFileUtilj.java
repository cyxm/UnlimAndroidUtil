package com.un.utilj.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	 * 创建前置目录,不创建自身
	 *
	 * @param path
	 *
	 * @return
	 */
	public static boolean createParentDirs(String path) {
		if (path == null) {
			return false;
		}

		File file = new File(path);
		File parentFile = file.getParentFile();
		if (parentFile == null) {
			return false;
		}

		if (!parentFile.exists()) {
			return createDirs(parentFile);
		} else {
			return true;
		}
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
	public static boolean deleteFilesOrDirs(String filepath) {
		File file = new File(filepath);
		return deleteFilesOrDirs(file);
	}

	/**
	 * 递归删除文件或文件夹
	 *
	 * @param file
	 *
	 * @return
	 */
	public static boolean deleteFilesOrDirs(File file) {
		return deleteFilesOrDirs(file, true);
	}

	/**
	 * 递归删除子文件或子文件夹
	 *
	 * @param filePath
	 *
	 * @return
	 */
	public static boolean deleteChildrenFilesOrDirs(String filePath) {
		File file = new File(filePath);
		return deleteChildrenFilesOrDirs(file);
	}

	/**
	 * 递归删除子文件或子文件夹
	 *
	 * @param file
	 *
	 * @return
	 */
	public static boolean deleteChildrenFilesOrDirs(File file) {
		return deleteFilesOrDirs(file, false);
	}

	/**
	 * 删除所有子文件和子文件夹
	 *
	 * @param file
	 * 		待删除的文件或文件夹
	 * @param isDeleteSelf
	 * 		如果自身为文件夹,是否删除自身
	 *
	 * @return
	 */
	public static boolean deleteFilesOrDirs(File file, boolean isDeleteSelf) {
		if (file == null || !file.exists()) {
			return true;
		}
		if (file.isDirectory()) {
			File[] childrenFile = file.listFiles();
			if (childrenFile == null || childrenFile.length == 0) {
				if (isDeleteSelf) {
					return file.delete();
				} else {
					return true;
				}
			}
			for (File child : childrenFile) {
				deleteFilesOrDirs(child, true);
			}
			if (isDeleteSelf) {
				return file.delete();
			} else {
				return true;
			}
		} else {
			return file.delete();
		}
	}

	/**
	 * 获取文件更改时间
	 *
	 * @param path
	 *
	 * @return
	 */
	public static long getModifyTime(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.lastModified();
		} else {
			return 0;
		}
	}

	public static void copy(File source, File target) {
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new FileInputStream(source);
			fos = new FileOutputStream(target);
			byte[] buf = new byte[4096];
			int i;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

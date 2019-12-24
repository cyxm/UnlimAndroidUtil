package com.un.utilj.file;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class UStreamUtilj {

	public static void transFileToFile(String fileName, String path) {
		if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(path)) {
			return;
		}
		File file = new File(fileName);

		// 构建目标文件
		File fileCopy = new File(path);
		InputStream in = null;
		OutputStream out = null;
		try {
			// 目标文件不存在就创建
			if (!(fileCopy.exists())) {
				fileCopy.createNewFile();
			}
			// 源文件创建输入流
			in = new FileInputStream(file);
			// 目标文件创建输出流
			out = new FileOutputStream(fileCopy, true);
			// 创建字节数组
			byte[] temp = new byte[1024];
			int length = 0;
			// 源文件读取一部分内容
			while ((length = in.read(temp)) != -1) {
				// 目标文件写入一部分内容
				out.write(temp, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭文件输入输出流
				in.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取文本文件
	 */
	public static String readStringUtf8(String fullPath) {
		File file = new File(fullPath);
		if (!file.exists() || file.isDirectory()) {
			return null;
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			String bufferStr;
			while ((bufferStr = reader.readLine()) != null) {
				sb.append(bufferStr);
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取java对象
	 */
	public static Object readObject(String fullPath) {
		File file = new File(fullPath);
		if (!file.exists() || file.isDirectory()) {
			return null;
		}
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
			Object obj = objectInputStream.readObject();
			objectInputStream.close();
			return obj;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写入java对象
	 */
	public static void writeObject(Object obj, String fullPath) {
		File file = new File(fullPath);
		if (!file.exists() || file.isDirectory()) {
			return;
		}
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制整个文件夹内容
	 *
	 * @param oldPath
	 * 		String 原文件路径 如：c:/fqf
	 * @param newPath
	 * 		String 复制后路径 如：f:/fqf/ff
	 *
	 * @return boolean
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" +
							(temp.getName()).toString());
					byte[] b = new byte[1024*5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {//如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	public static boolean copyFile(String oldPath, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时
				inStream = new FileInputStream(oldPath); //读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					//System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (fs != null) {
					fs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}

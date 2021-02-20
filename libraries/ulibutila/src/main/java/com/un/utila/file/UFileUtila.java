package com.un.utila.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.un.utilj.file.UFileUtilj;

import java.io.File;

public class UFileUtila {

	public static boolean createExternalFilesDir(Context context) {
		File file = context.getExternalFilesDir(null);
		return UFileUtilj.createDirs(file);
	}

	public static String getExternalFilesDir(Context context) {
		File file = context.getExternalFilesDir(null);
		return file == null ? "" : file.getAbsolutePath();
	}

	public static boolean createExternalPicturesDir(Context context) {
		File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return UFileUtilj.createDirs(file);
	}

	public static String getExternalPicturesDir(Context context) {
		File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return file == null ? "" : file.getAbsolutePath();
	}

	public static boolean haveMemory(String path, int needsize) {

		if (path.contains(Environment.getExternalStorageDirectory().getAbsolutePath())) {
			return getSDFreeSize() >= needsize;
		} else if (path.contains(Environment.getDataDirectory().getAbsolutePath())) {
			return getRomAvailableSize() >= needsize;
		} else {
			return false;
		}
	}

	public static long getSDFreeSize() {
		//取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		//获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		//空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		//返回SD卡空闲大小
		//return freeBlocks * blockSize;  //单位Byte
		//return (freeBlocks * blockSize)/1024;   //单位KB
		return (freeBlocks*blockSize)/1024/1024; //单位MB
	}

	public static long getSDAllSize() {
		//取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		//获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		//获取所有数据块数
		long allBlocks = sf.getBlockCount();
		//返回SD卡大小
		//return allBlocks * blockSize; //单位Byte
		//return (allBlocks * blockSize)/1024; //单位KB
		return (allBlocks*blockSize)/1024/1024; //单位MB
	}

	public static long getRomTotalSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long allBlocks = stat.getBlockCount();
		return (allBlocks*blockSize)/1024/1024; //单位MB
	}

	private static long getRomAvailableSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return (blockSize*availableBlocks)/1024/1024; //单位MB
	}
}

package com.un.utila.sharefile;

import android.content.Context;

import com.un.utila.util.OsUtil;

import java.util.List;

/**
 * 用于处理MediaStore相关
 */
public class ShareManagerUtil {

	/**
	 * 添加媒体文件到媒体库中
	 *
	 * @param context
	 * @param oriFileFullPath
	 * @param relativePath
	 */
	public static void addFile(
			Context context,
			String oriFileFullPath,
			String relativePath
	) {
		if (OsUtil.isGeAndroid10()) {
			ShareManagerGEQ.add(context, oriFileFullPath, relativePath, true);
		} else {
			ShareManagerLQ.add(context, oriFileFullPath, relativePath, true);
		}
	}

	/**
	 * 获取特定路径下的所有媒体文件
	 *
	 * @param context
	 * @param relativePath
	 *
	 * @return
	 */
	public static List<ModelMediaUri> getAll(
			Context context,
			String relativePath
	) {
		if (OsUtil.isGeAndroid10()) {
			return ShareManagerGEQ.getAll(context, relativePath);
		} else {
			return ShareManagerLQ.getAll(context, relativePath);
		}
	}

	/**
	 * 删除媒体文件
	 *
	 * @param context
	 * @param id
	 * @param mimeType
	 */
	public static void deleteFile(Context context, long id, String mimeType) {
		if (OsUtil.isGeAndroid10()) {
			ShareManagerGEQ.delete(context, id, mimeType);
		} else {
			ShareManagerLQ.delete(context, id, mimeType);
		}
	}
}

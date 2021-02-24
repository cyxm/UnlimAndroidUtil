package com.un.utila.sharefile;

import android.content.Context;

import com.un.utila.util.OsUtil;

import java.util.List;

/**
 * 用于处理MediaStore相关
 */
public class ShareManagerUtil {

	public static void saveJpg(
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

	public static void deleteFile(Context context, long id, String mimeType) {
		if (OsUtil.isGeAndroid10()) {
			ShareManagerGEQ.delete(context, id, mimeType);
		} else {
			ShareManagerLQ.delete(context, id, mimeType);
		}
	}
}

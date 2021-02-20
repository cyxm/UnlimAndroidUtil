package com.un.utila.util;

import android.content.ContentValues;
import android.content.Context;
import android.provider.MediaStore;

import com.un.utilj.file.UFileUtilj;

import java.io.File;

/**
 * 系统相册相关
 */
public class SystemAlbumUtil {

	/**
	 * 针对非系统文件夹下的文件,使用该方法
	 * 插入时初始化公共字段
	 *
	 * @param filePath
	 * 		文件
	 *
	 * @return ContentValues
	 */
	private static ContentValues initCommonContentValues(String albumPath, String filePath) {
		ContentValues values = new ContentValues();
		File saveFile = new File(filePath);
		long timeMillis = saveFile.lastModified();
		values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
		values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
		values.put(MediaStore.MediaColumns.DATE_ADDED, timeMillis);
		values.put(MediaStore.MediaColumns.DATE_MODIFIED, timeMillis);
		values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.SIZE, saveFile.length());
//		if (OsUtil.isGeAndroid10()) {
//			values.put(
//					MediaStore.MediaColumns.RELATIVE_PATH,
//					String.format(
//							"%s/%s",
//							Environment.DIRECTORY_PICTURES,
//							albumPath
//					)
//			);
//		}
		return values;
	}

	/**
	 * 保存到照片到本地，并插入MediaStore以保证相册可以查看到,这是更优化的方法，防止读取的照片获取不到宽高
	 *
	 * @param context
	 * 		上下文
	 * @param filePath
	 * 		文件路径
	 */
	public static void insertImageToMediaStore(Context context, String albumPath, String filePath) {
		if (!UFileUtilj.isFileOrDirExist(filePath)) {
			return;
		}
		ContentValues values = initCommonContentValues(albumPath, filePath);
		values.put(MediaStore.MediaColumns.MIME_TYPE, getPhotoMimeType(filePath));
		context.getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}

	/**
	 * 获取照片的mine_type
	 *
	 * @param path
	 *
	 * @return
	 */
	private static String getPhotoMimeType(String path) {
		String lowerPath = path.toLowerCase();
		if (lowerPath.endsWith("jpg") || lowerPath.endsWith("jpeg")) {
			return "image/jpeg";
		} else if (lowerPath.endsWith("png")) {
			return "image/png";
		} else if (lowerPath.endsWith("gif")) {
			return "image/gif";
		}
		return "image/jpeg";
	}

	//	/**
	//	 * 保存到视频到本地，并插入MediaStore以保证相册可以查看到,这是更优化的方法，防止读取的视频获取不到宽高
	//	 *
	//	 * @param context
	//	 * 		上下文
	//	 * @param filePath
	//	 * 		文件路径
	//	 * @param createTime
	//	 * 		创建时间 <=0时为当前时间 ms
	//	 * @param duration
	//	 * 		视频长度 ms
	//	 * @param width
	//	 * 		宽度
	//	 * @param height
	//	 * 		高度
	//	 */
	//	public static void insertVideoToMediaStore(Context context, String filePath, long createTime, int width, int height, long duration) {
	//		if (!checkFile(filePath))
	//			return;
	//		createTime = getTimeWrap(createTime);
	//		ContentValues values = initCommonContentValues(filePath, createTime);
	//		values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, createTime);
	//		if (duration > 0)
	//			values.put(MediaStore.Video.VideoColumns.DURATION, duration);
	//		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
	//			if (width > 0)
	//				values.put(MediaStore.Video.VideoColumns.WIDTH, width);
	//			if (height > 0)
	//				values.put(MediaStore.Video.VideoColumns.HEIGHT, height);
	//		}
	//		values.put(MediaStore.MediaColumns.MIME_TYPE, getVideoMimeType(filePath));
	//		context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
	//	}

}

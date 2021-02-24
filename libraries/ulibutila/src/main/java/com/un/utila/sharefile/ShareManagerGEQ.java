package com.un.utila.sharefile;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 共享媒体文件的管理类,版本号<10
 * 直接使用外部文件路径进行文件管理
 */
public class ShareManagerGEQ {

	@TargetApi(android.os.Build.VERSION_CODES.Q)
	public static void add(
			Context context,
			String oriFileFullPath,
			String relativePath,
			boolean isCopy
	) {
		//添加数据库字段
		ContentValues values = new ContentValues();
		values.put(
				MediaStore.MediaColumns.RELATIVE_PATH,
				relativePath
		);

		//
		Uri baseUri = null;
		String mimeType = FileMimeTypeUtil.getMimeType(oriFileFullPath);
		if (FileMimeTypeUtil.isImage(mimeType)) {
			baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		} else if (FileMimeTypeUtil.isVideo(mimeType)) {
			baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		}

		//不支持的类型
		if (baseUri == null) {
			return;
		}

		//插入索引
		ContentResolver contentResolver = context.getContentResolver();
		Uri insert = contentResolver.insert(
				baseUri,
				values
		);

		//复制文件
		if (isCopy) {
			FileInputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				File saveFile = new File(oriFileFullPath);
				inputStream = new FileInputStream(saveFile);
				outputStream = contentResolver.openOutputStream(insert);
				FileUtils.copy(inputStream, outputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@TargetApi(android.os.Build.VERSION_CODES.Q)
	public static List<ModelMediaUri> getAll(
			Context context,
			String relativePath
	) {
		List<ModelMediaUri> result = new ArrayList<>();

		List<ModelMediaUri> imageResult = get(
				context,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				Environment.DIRECTORY_PICTURES,
				relativePath
		);
		List<ModelMediaUri> videoResult = get(
				context,
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				Environment.DIRECTORY_MOVIES,
				relativePath
		);

		result.addAll(imageResult);
		result.addAll(videoResult);

		return result;
	}

	@TargetApi(android.os.Build.VERSION_CODES.Q)
	public static List<ModelMediaUri> get(
			Context context,
			Uri baseUri,
			String publicDir,
			String relativePath
	) {
		List<ModelMediaUri> result = new ArrayList<>();

		//查询
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(
				baseUri,
				null,
				MediaStore.MediaColumns.RELATIVE_PATH + "=?",
				new String[]{
						publicDir + "/" + relativePath
				},
				null
		);

		if (cursor == null || !cursor.moveToFirst()) {
			return result;
		}

		int idColumn = cursor.getColumnIndex(MediaStore.MediaColumns._ID);
		int timeColumn = cursor.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED);
		int mimeColumn = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE);

		while (!cursor.isAfterLast()) {
			long id = cursor.getLong(idColumn);
			long time = cursor.getLong(timeColumn);
			String mimeType = cursor.getString(mimeColumn);

			Uri imgUri = ContentUris.withAppendedId(
					baseUri,
					id
			);
			ModelMediaUri modelMediaUri = new ModelMediaUri(id, imgUri, time*1000, mimeType);
			result.add(modelMediaUri);
			cursor.moveToNext();
		}
		cursor.close();

		return result;
	}

	@TargetApi(android.os.Build.VERSION_CODES.Q)
	public static void delete(Context context, long id, String mimeType) {
		//
		Uri baseUri = null;
		if (FileMimeTypeUtil.isImage(mimeType)) {
			baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		} else if (FileMimeTypeUtil.isVideo(mimeType)) {
			baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		}
		ContentResolver contentResolver = context.getContentResolver();
		contentResolver.delete(
				baseUri,
				MediaStore.MediaColumns._ID + "=?",
				new String[]{String.valueOf(id)}
		);
	}
}

package com.un.utila.sharefile;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.un.utilj.file.UFileUtilj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 共享媒体文件的管理类,版本号<10
 * 直接使用外部文件路径进行文件管理
 */
public class ShareManagerLQ {

	public static void add(
			Context context,
			String oriFileFullPath,
			String relativePath,
			boolean isCopy
	) {
		File saveFile = new File(oriFileFullPath);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
		values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
		values.put(MediaStore.MediaColumns.SIZE, saveFile.length());
		values.put(MediaStore.MediaColumns.DATE_MODIFIED, saveFile.lastModified()/1000);

		//
		String fileName = oriFileFullPath.substring(oriFileFullPath.lastIndexOf("/") + 1);
		String newFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
		newFilePath = newFilePath + "/" + relativePath;
		newFilePath += fileName;
		values.put(
				MediaStore.MediaColumns.DATA,
				newFilePath
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

		ContentResolver contentResolver = context.getContentResolver();
		contentResolver.insert(baseUri, values);

		//复制文件
		if (isCopy) {
			UFileUtilj.createParentDirs(newFilePath);
			UFileUtilj.copy(new File(oriFileFullPath), new File(newFilePath));
		}
	}

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
				MediaStore.MediaColumns.DATA + " like '%" + publicDir + "/" + relativePath + "%'",
				null,
				null
		);

		if (cursor == null || !cursor.moveToFirst()) {
			return result;
		}

		int idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
		int timeColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED);
		int mimeColumn = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE);
		int dataColumn = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

		while (!cursor.isAfterLast()) {
			long id = cursor.getLong(idColumn);
			long time = cursor.getLong(timeColumn);
			String mimeType = cursor.getString(mimeColumn);
			String data = cursor.getString(dataColumn);
			Log.i("PwLog", data);

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

	public static void delete(Context context, long id, String mimeType) {
		//
		Uri baseUri = null;
		if (FileMimeTypeUtil.isImage(mimeType)) {
			baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		} else if (FileMimeTypeUtil.isVideo(mimeType)) {
			baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		}
		//
		ContentResolver contentResolver = context.getContentResolver();
		//查找
		Cursor cursor = contentResolver.query(
				baseUri,
				null,
				MediaStore.MediaColumns._ID + "=?",
				new String[]{
						String.valueOf(id)
				},
				null
		);
		if (!cursor.moveToFirst()) {
			return;
		}
		int dataCol = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
		String filePath = cursor.getString(dataCol);

		//删除索引
		contentResolver.delete(
				baseUri,
				MediaStore.MediaColumns._ID + "=?",
				new String[]{String.valueOf(id)}
		);
		//删除文件
		UFileUtilj.deleteFilesOrDirs(filePath);
	}
}

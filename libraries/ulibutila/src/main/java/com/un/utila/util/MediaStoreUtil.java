package com.un.utila.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;

import com.un.utilj.file.UFileUtilj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于处理MediaStore相关
 */
public class MediaStoreUtil {

	public static void saveJpg(
			Context context,
			String albumPath,
			String fileFullPath,
			String fileName
	) {
		File saveFile = new File(fileFullPath);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
		values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
		values.put(MediaStore.MediaColumns.SIZE, saveFile.length());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
		values.put(MediaStore.MediaColumns.DATE_MODIFIED, saveFile.lastModified()/1000);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			values.put(
					MediaStore.MediaColumns.RELATIVE_PATH,
					String.format(
							"%s/%s/",
							Environment.DIRECTORY_PICTURES,
							albumPath
					)
			);
			//插入索引并复制文件
			Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			ContentResolver contentResolver = context.getContentResolver();
			Uri insert = contentResolver.insert(contentUri, values);

			FileInputStream inputStream = null;
			OutputStream outputStream = null;
			try {
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
		} else {
			//
			String newFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
			newFilePath = newFilePath + "/" + albumPath;
			newFilePath += "/" + fileName;
			values.put(
					MediaStore.MediaColumns.DATA,
					newFilePath
			);
			Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			ContentResolver contentResolver = context.getContentResolver();
			Uri insert = contentResolver.insert(contentUri, values);
			//
			UFileUtilj.createParentDirs(newFilePath);
			UFileUtilj.copy(new File(fileFullPath), new File(newFilePath));
		}
	}

	public static List<ModelMediaUri> getJpg(
			Context context,
			String albumPath
	) {
		List<ModelMediaUri> result = new ArrayList<>();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			ContentResolver contentResolver = context.getContentResolver();
			Cursor cursor = contentResolver.query(
					contentUri,
					null,
					MediaStore.MediaColumns.RELATIVE_PATH + "=?",
					new String[]{
							String.format(
									"%s/%s/",
									Environment.DIRECTORY_PICTURES,
									albumPath
							)
					},
					null
			);


			if (!cursor.moveToFirst()) {
				return result;
			}

			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
			int timeColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED);
			while (!cursor.isAfterLast()) {
				long id = cursor.getLong(idColumn);
				long time = cursor.getLong(timeColumn);
				Uri imgUri = ContentUris.withAppendedId(
						contentUri,
						id
				);
				ModelMediaUri modelMediaUri = new ModelMediaUri(id, imgUri, time*1000);
				result.add(modelMediaUri);
				cursor.moveToNext();
			}

			cursor.close();
		} else {
			//
			String newFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
			newFilePath = newFilePath + "/" + albumPath;
			File newFile = new File(newFilePath);
			File[] files = newFile.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					Uri imgUri = Uri.fromFile(file);
					ModelMediaUri modelMediaUri = new ModelMediaUri(0, imgUri, file.lastModified());
					result.add(modelMediaUri);
				}
			}
		}
		return result;
	}

	public static void deleteFile(Context context, long id) {
		if (OsUtil.isGeAndroid10()) {
			Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			ContentResolver contentResolver = context.getContentResolver();
			contentResolver.delete(
					contentUri,
					MediaStore.MediaColumns._ID + "=?",
					new String[]{String.valueOf(id)}
			);
		} else {
ff
		}
	}

	public static class ModelMediaUri {
		long id;
		Uri mediaUri;
		long fileTime;

		public ModelMediaUri(long id, Uri mediaUri, long fileTime) {
			this.id = id;
			this.mediaUri = mediaUri;
			this.fileTime = fileTime;
		}

		public Uri getMediaUri() {
			return mediaUri;
		}

		public void setMediaUri(Uri mediaUri) {
			this.mediaUri = mediaUri;
		}

		public long getFileTime() {
			return fileTime;
		}

		public void setFileTime(long fileTime) {
			this.fileTime = fileTime;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}
}

package com.un.componentax.component;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.un.componentax.act.ActivityBase;
import com.un.componentax.act.IOnActivityResult;

import java.io.File;

public class ComponentSysUtil {

	/**
	 * 跳转到系统画廊
	 *
	 * @param activityBase
	 * @param onActivityResult
	 */
	public static void goGallery(ActivityBase activityBase, IOnActivityResult onActivityResult) {
		Intent intent = new Intent();
		if (Build.VERSION.SDK_INT < 19) {
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
		} else {
			intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		}

		activityBase.addActivityResult(0, onActivityResult);
		activityBase.startActivityForResult(intent, 0);
	}

	/**
	 * 跳转到系统wifi设置
	 *
	 * @param context
	 */
	public static void goWifiSetting(Context context) {
		context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
	}

	public static void goShare(Context context, String title, String intentType, String authority, String path) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);

		Uri uri;
		if (Build.VERSION.SDK_INT >= 24) {
			uri = FileProvider.getUriForFile(context, authority, new File(path));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		} else {
			uri = Uri.fromFile(new File(path));
		}
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		intent.setType(intentType);

		Intent shareIntent = Intent.createChooser(intent, title);

		context.startActivity(shareIntent);
	}
}

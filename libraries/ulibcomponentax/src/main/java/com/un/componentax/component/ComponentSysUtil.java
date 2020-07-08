package com.un.componentax.component;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;

import com.un.componentax.act.ActivityBase;
import com.un.componentax.act.IOnActivityResult;

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
}

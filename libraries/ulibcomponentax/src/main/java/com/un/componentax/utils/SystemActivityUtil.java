package com.un.componentax.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.fragment.app.Fragment;

/**
 * 跳转到系统的Activity
 */
public class SystemActivityUtil {

	/**
	 * wifi设置界面
	 */
	public static void goWifiSetting(Activity activity) {
		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		activity.startActivityForResult(intent, 100);
	}

	/**
	 * wifi设置界面
	 */
	public static void goWifiSetting(Fragment fragment) {
		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		fragment.startActivityForResult(intent, 0);
	}

	/**
	 *
	 */
	public static boolean goNotificationListenerSetting(final Activity activity) {
		Intent intent = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
			intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
		} else {
			intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
		}
		try {
			activity.startActivity(intent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	/**
	 *
	 */
	public static void goNotificationSetting(final Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Intent intent = new Intent();
			intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
			intent.putExtra("app_package", activity.getPackageName());
			intent.putExtra("app_uid", activity.getApplicationInfo().uid);
			try {
				activity.startActivity(intent);
			} catch (Exception e) {
			}
		} else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
			Intent intent = new Intent();
			intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.setData(Uri.parse("package:" + activity.getPackageName()));
			try {
				activity.startActivity(intent);
			} catch (Exception e) {
			}
		} else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			goApplicationSetting(activity);
		}
	}

	public static void goApplicationSetting(final Activity activity) {
		Intent localIntent = new Intent();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
		} else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
			localIntent.setAction(Intent.ACTION_VIEW);
			localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
		}
		try {
			activity.startActivity(localIntent);
		} catch (Exception e) {
		}
	}

}

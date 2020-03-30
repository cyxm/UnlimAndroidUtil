package com.un.utila.display;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil {

	public static DisplayMetrics getDisplayMetrics(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		if (wm == null) {
			return null;
		}
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

	public static DisplayMetrics getDisplayMetrics(WindowManager wm) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

	public static DisplayMetrics getRealDisplayMetrics(WindowManager wm) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			DisplayMetrics displayMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getRealMetrics(displayMetrics);
			return displayMetrics;
		} else {
			return getDisplayMetrics(wm);
		}
	}
}

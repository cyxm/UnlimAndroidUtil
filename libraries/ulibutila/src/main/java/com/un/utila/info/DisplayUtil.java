package com.un.utila.info;

import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil {

	public static DisplayMetrics getDisplayMetrics(WindowManager wm) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

}

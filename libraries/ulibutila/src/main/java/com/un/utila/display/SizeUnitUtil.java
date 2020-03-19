package com.un.utila.display;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class SizeUnitUtil {

	public static int dp2px(WindowManager wm, int dp) {
		DisplayMetrics dm = DisplayUtil.getDisplayMetrics(wm);
		return (int) (dm.density*dp);
	}

	public static int getPx(Context context, int dp) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		if (wm == null) {
			return dp;
		}
		DisplayMetrics dm = DisplayUtil.getDisplayMetrics(wm);
		return (int) (dp*dm.density);
	}
}

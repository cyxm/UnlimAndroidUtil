package com.un.utila.display;

import android.util.DisplayMetrics;
import android.view.WindowManager;

public class SizeUnitUtil {

	public static int dp2px(WindowManager wm, int dp) {
		DisplayMetrics dm = DisplayUtil.getDisplayMetrics(wm);
		return (int) (dm.density*dp);
	}

}

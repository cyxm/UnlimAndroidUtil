package com.un.utila.ui;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 系统UI处理工具类
 */
public class SystemUIUtil {

	/**
	 * 设置状态栏完全透明
	 *
	 * @param window
	 *
	 * @return
	 */
	public static boolean setStatusBarTransparent(Window window) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return setStatusBarColor(window, Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams attributes = window.getAttributes();
			int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			attributes.flags |= flagTranslucentStatus;
			window.setAttributes(attributes);
			return true;
		} else {
			//do nothing:unsuport feature
			return false;
		}
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param window
	 * @param color
	 *
	 * @return 设置功能是否支持
	 */
	public static boolean setStatusBarColor(Window window, int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(color);
			return true;
		} else {
			//do nothing:unsuport feature
			return false;
		}
	}

	/**
	 * 在允许的范围内设置内容布局全屏
	 *
	 * @param window
	 *
	 * @return
	 */
	public static boolean setLayoutFullScreen(Window window) {
		View decorView = window.getDecorView();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
			decorView.setSystemUiVisibility(option);
			return true;
		} else {
			//do nothing:unsuport feature
			return false;
		}
	}

}

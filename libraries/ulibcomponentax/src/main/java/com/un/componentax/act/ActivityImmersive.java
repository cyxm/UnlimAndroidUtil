package com.un.componentax.act;

import android.view.Window;
import android.view.WindowManager;

import com.un.utila.ui.SystemUIUtil;

public abstract class ActivityImmersive extends ActivityBase {

	@Override
	protected void onWindowSetup() {
		Window window = getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SystemUIUtil.setLayoutFullScreen(window);

		onSetStatusBarTheme();
	}

	/**
	 * 设置状态栏的主题
	 */
	protected void onSetStatusBarTheme() {

	}
}

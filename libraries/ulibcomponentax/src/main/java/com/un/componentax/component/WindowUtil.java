package com.un.componentax.component;

import android.app.Activity;
import android.view.WindowManager;

public class WindowUtil {
	/**
	 * 切换全屏模式
	 *
	 * @param isFullScreen
	 */
	public static void setFullScreen(Activity activity, boolean isFullScreen) {
		if (isFullScreen) {
			//切换到全屏模式
			//添加一个全屏的标记
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			//			//请求横屏
			//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

			//			//设置视频播放控件的布局的高度是match_parent
			//			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mVideoRootView.getLayoutParams();
			//			//将默认的高度缓存下来
			//			mVideoHeight = layoutParams.height;
			//			layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
			//			mVideoRootView.setLayoutParams(layoutParams);
		} else {
			//切换到默认模式
			//清除全屏标记
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			//			//请求纵屏
			//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			//
			//			//设置视频播放控件的布局的高度是200
			//			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mVideoRootView.getLayoutParams();
			//			layoutParams.height = mVideoHeight;  //这里的单位是px
			//			mVideoRootView.setLayoutParams(layoutParams);
		}
	}
}

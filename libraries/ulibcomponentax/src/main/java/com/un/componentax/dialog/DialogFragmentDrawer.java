package com.un.componentax.dialog;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.un.componentax.utils.RepoColor;
import com.un.componentax.utils.StatusBarUtil;
import com.un.utila.display.DisplayUtil;
import com.un.utila.ui.SystemUIUtil;

public abstract class DialogFragmentDrawer extends DialogFragmentNoModal {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentActivity activity = getActivity();
		if (activity == null) {
			return;
		}

		final DisplayMetrics dm = DisplayUtil.getDisplayMetrics(activity.getWindowManager());

		setDialogWindowSetting(new ItfDialogWindowSetting() {
			@Override
			public int getWidth() {
				return (int) (dm.widthPixels*0.8);
			}

			@Override
			public int getHeight() {
				return dm.heightPixels;
			}

			@Override
			public int getWidthMax() {
				return 0;
			}

			@Override
			public int getHeightMax() {
				return 0;
			}

			@Override
			public int getGravity() {
				return Gravity.START;
			}
		});
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		//		Window window = getDialog().getWindow();
		//		window.requestFeature(Window.FEATURE_NO_TITLE);

		Window window = getDialog().getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SystemUIUtil.setLayoutFullScreen(window);
		//设置背景为透明
		SystemUIUtil.setStatusBarTransparent(window);
		if (!StatusBarUtil.setStatusBarDarkTheme(getActivity(), true)) {
			SystemUIUtil.setStatusBarColor(window, RepoColor.COLOR_QUARTER_TRANSPARENT_BLACK);
		}

		super.onActivityCreated(savedInstanceState);


		int dialogHeight = getContextRect(getActivity());
		//设置弹窗大小为会屏
		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
		//去除阴影
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		layoutParams.dimAmount = 0.0f;
		window.setAttributes(layoutParams);
	}

	//获取内容区域
	private int getContextRect(Activity activity) {
		//应用区域
		Rect outRect1 = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
		return outRect1.height();
	}
}

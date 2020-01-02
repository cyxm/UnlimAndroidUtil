package com.un.componentax.dialog;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.un.componentax.utils.RepoColor;
import com.un.componentax.utils.StatusBarUtil;
import com.un.utila.ui.SystemUIUtil;

public abstract class DialogFragmentDrawer extends DialogFragmentNoModal {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentActivity activity = getActivity();
		if (activity == null) {
			return;
		}

		final Rect outRect = new Rect();
		getActivity().getWindow().getDecorView().getGlobalVisibleRect(outRect);

		setDialogWindowSetting(new ItfDialogWindowSetting() {
			@Override
			public int getWidth() {
				return (int) (outRect.width()*0.8);
			}

			@Override
			public int getHeight() {
				return outRect.height();
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

		Window window = getDialog().getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SystemUIUtil.setLayoutFullScreen(window);
		//设置背景为透明
		SystemUIUtil.setStatusBarTransparent(window);
		if (!StatusBarUtil.setStatusBarDarkTheme(getActivity(), true)) {
			SystemUIUtil.setStatusBarColor(window, RepoColor.COLOR_QUARTER_TRANSPARENT_BLACK);
		}

		super.onActivityCreated(savedInstanceState);
	}
}

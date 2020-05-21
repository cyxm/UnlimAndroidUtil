package com.un.componentax.dialog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.un.utila.display.DisplayUtil;

public abstract class DialogFragmentPrompt extends DialogFragmentModal {

	protected FragmentActivity mFragmentActivity;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragmentActivity = getActivity();

		FragmentActivity activity = getActivity();
		if (activity == null) {
			return;
		}

		final DisplayMetrics dm = DisplayUtil.getDisplayMetrics(activity.getWindowManager());
		setDialogWindowSetting(new ItfDialogWindowSetting() {
			@Override
			public int getWidth() {
				return (int) (dm.widthPixels*0.9);
			}

			@Override
			public int getHeight() {
				return WindowManager.LayoutParams.WRAP_CONTENT;
			}

			@Override
			public int getWidthMax() {
				return 0;
			}

			@Override
			public int getHeightMax() {
				return (int) (dm.widthPixels*0.8);
			}

			@Override
			public int getGravity() {
				return Gravity.CENTER;
			}
		});
	}
}

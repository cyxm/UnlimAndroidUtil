package com.un.componentax.dialog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.un.utila.display.DisplayUtil;

/**
 * 模糊进度条dialog
 */
public abstract class DialogFragmentIndistinctProgress extends DialogFragmentModal {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentActivity activity = getActivity();
		if (activity == null) {
			return;
		}

		final DisplayMetrics dm = DisplayUtil.getDisplayMetrics(activity.getWindowManager());
		int size = Math.min(dm.widthPixels, dm.heightPixels);
		size = (int) (size*0.4);

		final int finalSize = size;
		setDialogWindowSetting(new ItfDialogWindowSetting() {
			@Override
			public int getWidth() {
				return finalSize;
			}

			@Override
			public int getHeight() {
				return finalSize;
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
				return Gravity.CENTER;
			}
		});
	}
}

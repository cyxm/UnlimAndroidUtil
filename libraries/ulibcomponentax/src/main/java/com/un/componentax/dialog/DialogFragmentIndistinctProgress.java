package com.un.componentax.dialog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.un.utila.display.DisplayUtil;

public abstract class DialogFragmentIndistinctProgress extends DialogFragmentModal {

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
				return (int) (dm.widthPixels*0.4);
			}

			@Override
			public int getHeight() {
				return (int) (dm.widthPixels*0.4);
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

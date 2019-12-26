package com.un.componentax.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.un.utila.info.DisplayUtil;
import com.un.utila.viewhelp.ViewClipUtil;

public abstract class DialogFragmentCommon extends AppCompatDialogFragment {

	private float widthRatio = 1;
	private float heigthRatioMax = 1;

	private int width;
	private int heigthMax;

	private int radius = 0;

	private int layout;

	public void init(int layout, float widthRatio, float heigthRatioMax, int radius) {
		this.layout = layout;
		this.widthRatio = widthRatio;
		this.heigthRatioMax = heigthRatioMax;
		this.radius = radius;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentActivity activity = getActivity();
		if (activity == null) {
			return;
		}
		DisplayMetrics dm = DisplayUtil.getDisplayMetrics(activity.getWindowManager());
		width = (int) (dm.widthPixels*widthRatio);
		heigthMax = (int) (dm.heightPixels*heigthRatioMax);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		if (dialog == null) {
			return;
		}

		onDialogSet(dialog);

		Window window = dialog.getWindow();
		if (window == null) {
			return;
		}

		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);

		window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(layout, container, false);
		ViewClipUtil.clipRound(v, radius);
		v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				int contentHeight = v.getHeight();

				if (contentHeight > heigthMax) {
					Dialog dialog = getDialog();
					if (dialog == null) {
						return;
					}

					Window window = dialog.getWindow();
					if (window == null) {
						return;
					}

					window.setLayout(
							width,
							heigthMax);
				}
			}
		});
		return v;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		Dialog dialog = getDialog();
		if (dialog == null) {
			return;
		}

		Window window = dialog.getWindow();
		if (window == null) {
			return;
		}

		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setBackgroundDrawableResource(android.R.color.transparent);
	}

	public void show(FragmentManager fragmentManager) {
		if (!isAdded()) {
			show(fragmentManager, "");
		}
	}

	protected abstract void onDialogSet(Dialog dialog);
}

package com.un.componentax.dialog;

import android.app.Dialog;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;

import com.un.componentax.R;
import com.un.utila.viewhelp.ViewClipUtil;

public abstract class DialogFragmentBase extends AppCompatDialogFragment {

	private ItfDialogWindowSetting dialogWindowSetting;

	private ItfDialogSetting dialogSetting;

	private ItfDialogViewSetting dialogViewSetting;

	public void setDialogWindowSetting(ItfDialogWindowSetting dialogWindowSetting) {
		this.dialogWindowSetting = dialogWindowSetting;
	}

	public void setDialogSetting(ItfDialogSetting dialogSetting) {
		this.dialogSetting = dialogSetting;
	}

	public void setDialogViewSetting(ItfDialogViewSetting dialogViewSetting) {
		this.dialogViewSetting = dialogViewSetting;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		if (dialog == null) {
			return;
		}

		if (dialogSetting != null) {
			dialogSetting.onDialogSetting(dialog);
		}

		Window window = dialog.getWindow();
		if (window == null) {
			return;
		}

		if (dialogWindowSetting != null) {
			WindowManager.LayoutParams params = window.getAttributes();
			params.gravity = dialogWindowSetting.getGravity();
			window.setAttributes(params);

			int width = dialogWindowSetting.getWidth();
			int height = dialogWindowSetting.getHeight();
			window.setLayout(width, height);
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(getResId(), container, false);
		ViewClipUtil.clipRoundRect(v, 10);
		return v;
	}

	protected abstract int getResId();

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
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.setCustomAnimations(R.anim.translate_enter_from_left, R.anim.translate_exit_to_left);
			show(ft, "");
		}
	}

	public void show(FragmentActivity fragmentActivity) {
		show(fragmentActivity.getSupportFragmentManager());
	}
}

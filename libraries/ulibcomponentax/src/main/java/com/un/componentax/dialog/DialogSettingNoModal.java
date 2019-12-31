package com.un.componentax.dialog;

import android.app.Dialog;

public class DialogSettingNoModal implements ItfDialogSetting {
	@Override
	public void onDialogSetting(Dialog dialog) {
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
	}
}

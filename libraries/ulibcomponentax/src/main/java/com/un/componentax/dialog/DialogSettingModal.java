package com.un.componentax.dialog;

import android.app.Dialog;

public class DialogSettingModal implements ItfDialogSetting {
	@Override
	public void onDialogSetting(Dialog dialog) {
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
	}
}

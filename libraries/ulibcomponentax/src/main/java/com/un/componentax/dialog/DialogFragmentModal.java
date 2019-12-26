package com.un.componentax.dialog;

import android.app.Dialog;

public class DialogFragmentModal extends DialogFragmentCommon {

	@Override
	protected void onDialogSet(Dialog dialog) {
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
	}
}

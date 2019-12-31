package com.un.componentax.dialog;

import android.view.View;

import com.un.utila.viewhelp.ViewClipUtil;

public class DialogViewSettingRound implements ItfDialogViewSetting {
	@Override
	public void onViewCreated(View v) {
		ViewClipUtil.clipRound(v, 10);
	}
}

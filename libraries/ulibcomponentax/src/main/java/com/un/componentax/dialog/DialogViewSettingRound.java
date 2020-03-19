package com.un.componentax.dialog;

import android.view.View;

import com.un.utila.display.SizeUnitUtil;
import com.un.utila.viewhelp.ViewClipUtil;

public class DialogViewSettingRound implements ItfDialogViewSetting {
	@Override
	public void onViewCreated(View v) {
		ViewClipUtil.clipRoundRect(v, SizeUnitUtil.getPx(v.getContext(), 8));
	}
}

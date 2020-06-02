package com.un.componentax.dialog;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 模态dialog,不能点击外部取消
 */
public abstract class DialogFragmentModal extends DialogFragmentBase {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setDialogSetting(new DialogSettingModal());
	}
}

package com.un.componentax.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.un.utila.viewhelp.ViewClipUtil;

public class LinearLayoutClipable extends LinearLayout {
	public LinearLayoutClipable(Context context) {
		super(context);
		init();
	}

	public LinearLayoutClipable(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LinearLayoutClipable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		ViewClipUtil.clipRoundRect(this, 200);
	}
}

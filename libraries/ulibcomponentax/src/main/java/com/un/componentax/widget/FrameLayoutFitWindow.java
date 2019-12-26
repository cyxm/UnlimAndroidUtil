package com.un.componentax.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FrameLayoutFitWindow extends FrameLayout {

	public FrameLayoutFitWindow(@NonNull Context context) {
		super(context);
		setFitsSystemWindows(true);
	}

	public FrameLayoutFitWindow(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setFitsSystemWindows(true);
	}

	public FrameLayoutFitWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setFitsSystemWindows(true);
	}
}

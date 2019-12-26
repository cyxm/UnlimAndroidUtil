package com.un.componentax.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LinearLayoutFitWindow extends LinearLayout {

	public LinearLayoutFitWindow(@NonNull Context context) {
		super(context);
		setFitsSystemWindows(true);
	}

	public LinearLayoutFitWindow(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setFitsSystemWindows(true);
	}

	public LinearLayoutFitWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setFitsSystemWindows(true);
	}
}

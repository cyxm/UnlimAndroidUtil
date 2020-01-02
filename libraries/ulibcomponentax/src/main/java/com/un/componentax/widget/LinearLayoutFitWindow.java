package com.un.componentax.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.un.componentax.utils.StatusBarUtil;

public class LinearLayoutFitWindow extends LinearLayout {

	public LinearLayoutFitWindow(@NonNull Context context) {
		super(context);
		init(context);
	}

	public LinearLayoutFitWindow(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LinearLayoutFitWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		setPadding(0, StatusBarUtil.getStatusBarHeight(context), 0, 0);
	}
}

package com.un.componentax.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.SwitchCompat;

public class SwitchNoWatch extends SwitchCompat {

	OnCheckedChangeListener onCheckedChangeListener;

	public SwitchNoWatch(Context context) {
		super(context);
	}

	public SwitchNoWatch(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwitchNoWatch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOnCheckedChangeListenerNoWatch(OnCheckedChangeListener onCheckedChangeListener) {
		super.setOnCheckedChangeListener(onCheckedChangeListener);
		this.onCheckedChangeListener = onCheckedChangeListener;
	}

	public void setCheckedNoWatch(boolean isChecked) {
		setOnCheckedChangeListener(null);
		setChecked(isChecked);
		setOnCheckedChangeListener(this.onCheckedChangeListener);
	}
}

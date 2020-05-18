package com.un.componentax.widget;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 提供忽略编辑文字监听的文字设置功能
 */
public class EditTextNoWatch extends AppCompatEditText {

	TextWatcher singleWatcher;

	public EditTextNoWatch(Context context) {
		super(context);
	}

	public EditTextNoWatch(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EditTextNoWatch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setTextChangedListener(TextWatcher watcher) {
		this.singleWatcher = watcher;
		addTextChangedListener(watcher);
	}

	/**
	 * 忽略文字更改监听
	 *
	 * @param text
	 */
	public void setTextNoWatch(CharSequence text) {
		if (getText().toString().equals(text)) {
			return;
		}
		if (this.singleWatcher == null) {
			setText(text);
		} else {
			removeTextChangedListener(this.singleWatcher);
			setText(text);
			addTextChangedListener(this.singleWatcher);
		}
	}
}

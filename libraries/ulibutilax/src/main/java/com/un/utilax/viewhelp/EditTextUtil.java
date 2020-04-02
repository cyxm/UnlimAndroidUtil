package com.un.utilax.viewhelp;

import android.text.InputType;
import android.widget.EditText;

public class EditTextUtil {

	public static void setPasswordVisible(EditText v, boolean isVisible) {
		if (isVisible) {
			v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		} else {
			v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}

	public static void setSelectionLast(EditText v) {
		String text = v.getText().toString();
		v.setSelection(text.length());
	}
}

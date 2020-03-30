package com.un.utila.res;

import android.content.Context;
import android.util.TypedValue;

import com.un.utila.display.DisplayUtil;

public class ResUtil {

	public static int getColorAttr(Context context, int attr) {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(attr, typedValue, true);
		return typedValue.data;
	}

	public static int getDimenAttr(Context context, int attr) {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(attr, typedValue, true);
		float dimen = typedValue.getDimension(DisplayUtil.getDisplayMetrics(context));
		return (int) dimen;
	}
}

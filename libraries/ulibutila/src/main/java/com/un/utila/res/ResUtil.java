package com.un.utila.res;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;

import com.un.utila.display.DisplayUtil;

public class ResUtil {

	public static int getColor(Context context, int colorRes) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return context.getResources().getColor(colorRes, context.getTheme());
		} else {
			return context.getResources().getColor(colorRes);
		}
	}

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

	public static String getString(Context context, int res) {
		return context.getResources().getString(res);
	}

	public static boolean getBool(Context context, int res) {
		return context.getResources().getBoolean(res);
	}
}

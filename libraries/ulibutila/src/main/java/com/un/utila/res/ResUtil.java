package com.un.utila.res;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;

import com.un.utila.display.DisplayUtil;

/**
 * 用于获取内置资源
 */
public class ResUtil {

	/**
	 * 获取属性中的颜色资源
	 *
	 * @param context
	 * @param attr
	 *
	 * @return
	 */
	public static int getColorAttr(Context context, int attr) {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(attr, typedValue, true);
		return typedValue.data;
	}

	/**
	 * 获取属性中的尺寸资源
	 *
	 * @param context
	 * @param attr
	 *
	 * @return
	 */
	public static int getDimenAttr(Context context, int attr) {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(attr, typedValue, true);
		float dimen = typedValue.getDimension(DisplayUtil.getDisplayMetrics(context));
		return (int) dimen;
	}

	/**
	 * 获取颜色资源
	 *
	 * @param context
	 * @param colorRes
	 *
	 * @return
	 */
	public static int getColor(Context context, int colorRes) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return context.getResources().getColor(colorRes, context.getTheme());
		} else {
			return context.getResources().getColor(colorRes);
		}
	}

	/**
	 * 获取string资源
	 *
	 * @param context
	 * @param res
	 *
	 * @return
	 */
	public static String getString(Context context, int res) {
		return context.getResources().getString(res);
	}

	/**
	 * 获取string数组资源
	 *
	 * @param context
	 * @param res
	 *
	 * @return
	 */
	public static String[] getStringArray(Context context, int res) {
		return context.getResources().getStringArray(res);
	}

	/**
	 * 获取int资源
	 *
	 * @param context
	 * @param res
	 *
	 * @return
	 */
	public static int[] getIntArray(Context context, int res) {
		return context.getResources().getIntArray(res);
	}

	/**
	 * 获取bool资源
	 *
	 * @param context
	 * @param res
	 *
	 * @return
	 */
	public static boolean getBool(Context context, int res) {
		return context.getResources().getBoolean(res);
	}
}

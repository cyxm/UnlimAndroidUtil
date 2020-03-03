package com.un.utila.res;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

public class ResUtil {

	public static int getColorAttr(Context context, int attr) {
		int[] attrsArray = {attr};
		TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
		int color = typedArray.getColor(0, Color.WHITE);
		typedArray.recycle();
		return color;
	}

}

package com.un.componentax.animation;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;

public class AnimatedDrawableUtil {
	public static RotateDrawable getRotateDrawable(Drawable base) {
		RotateDrawable drawable = new RotateDrawable();
		drawable.setFromDegrees(0);
		drawable.setToDegrees(359);
		drawable.setPivotXRelative(true);
		drawable.setPivotX(0.5F);
		drawable.setPivotYRelative(true);
		drawable.setPivotY(0.5F);
		drawable.setDrawable(base);
		return drawable;
	}
}

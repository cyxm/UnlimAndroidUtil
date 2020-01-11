package com.un.utila.viewhelp;

import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

public class ViewClipUtil {

	public static void clipRoundRect(final View v, final float radius) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			v.setClipToOutline(true);
			v.setOutlineProvider(new ViewOutlineProvider() {
				@Override
				public void getOutline(View view, Outline outline) {
					outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
				}
			});
		}
	}


	public static void clipOval(final View v) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			v.setClipToOutline(true);
			v.setOutlineProvider(new ViewOutlineProvider() {
				@Override
				public void getOutline(View view, Outline outline) {
					outline.setOval(0, 0, view.getWidth(), view.getHeight());
				}
			});
		}
	}

}

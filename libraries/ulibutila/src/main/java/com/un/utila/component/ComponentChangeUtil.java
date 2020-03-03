package com.un.utila.component;

import android.app.Activity;
import android.content.Intent;

public class ComponentChangeUtil {

	public static void startActivity(Activity activity, Class clz) {
		Intent intent = new Intent(activity, clz);
		activity.startActivity(intent);
	}
}

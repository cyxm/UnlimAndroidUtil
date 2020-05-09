package com.un.componentax.component;

import android.app.Activity;
import android.content.Intent;

import com.un.componentax.act.ActivityBase;
import com.un.componentax.act.IOnActivityResult;

public class ComponentChangeUtil {

	public static void startActivity(Activity activity, Class clz) {
		Intent intent = new Intent(activity, clz);
		activity.startActivity(intent);
	}

	public static void finishWithResult(Activity activity, int resultCode, Intent data) {
		activity.setResult(resultCode, data);
		activity.finish();
	}

	//	public static void startActivityWithResult(ActivityBase activityBase, Intent data, IOnActivityResult onActivityResult) {
	//		activityBase.addActivityResult(0, onActivityResult);
	//		activityBase.startActivityForResult(data, 0);
	//	}

	public static void startActivityWithResult(ActivityBase activityBase, Class clz, IOnActivityResult onActivityResult) {
		Intent intent = new Intent(activityBase, clz);
		activityBase.addActivityResult(0, onActivityResult);
		activityBase.startActivityForResult(intent, 0);
	}
}

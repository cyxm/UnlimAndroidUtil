package com.un.utila.service;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ActivityManagerUtil {

	public static void moveToForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
		for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
			if (taskInfo.topActivity == null) {
				continue;
			}
			if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
				activityManager.moveTaskToFront(taskInfo.id, 0);
				break;
			}
		}
	}
}

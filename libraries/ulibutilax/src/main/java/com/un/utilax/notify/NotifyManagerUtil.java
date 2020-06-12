package com.un.utilax.notify;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

public class NotifyManagerUtil {

	public static void notify(Context context, int id, Notification notification) {
		NotificationManagerCompat.from(context).notify(id, notification);
	}

}

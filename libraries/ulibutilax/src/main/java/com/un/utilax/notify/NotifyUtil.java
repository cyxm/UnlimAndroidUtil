package com.un.utilax.notify;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;

public class NotifyUtil {

	public static Notification createNotify(
			Context context,
			String channelId,
			int iconRes,
			String title,
			String content
	) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
		return builder.setSmallIcon(iconRes)
				.setContentTitle(title)
				.setContentText(content)
				.setAutoCancel(true)
				.build();
	}
}

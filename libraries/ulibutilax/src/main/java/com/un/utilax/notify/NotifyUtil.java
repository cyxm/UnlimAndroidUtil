package com.un.utilax.notify;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyUtil {

	public static void norify(Context context, String channelId, int iconRes, String title, String content) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
		builder.setSmallIcon(iconRes)
				.setContentTitle(title)
				.setContentText(content)
				.build();

		NotificationManagerCompat.from(context).notify(1, builder.build());
	}

}

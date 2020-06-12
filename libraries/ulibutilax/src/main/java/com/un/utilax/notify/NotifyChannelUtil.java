package com.un.utilax.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

public class NotifyChannelUtil {

	/**
	 * 创建通知渠道
	 *
	 * @param context
	 * @param channelId
	 * @param channelName
	 * @param channelDes
	 */
	public static void createChannel(Context context, String channelId, String channelName, String channelDes, int lockVisible) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
			mChannel.setDescription(channelDes);
			mChannel.setLockscreenVisibility(lockVisible);

			NotificationManager notificationManager = (NotificationManager) context.getSystemService(
					Context.NOTIFICATION_SERVICE
			);
			if (notificationManager == null) {
				return;
			}
			notificationManager.createNotificationChannel(mChannel);
		}
	}

}

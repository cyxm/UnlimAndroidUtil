package com.un.utila.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

public class NetConnectUtil {

	public static void registerNetConnect(Context context) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

			NetworkRequest.Builder builder = new NetworkRequest.Builder();
			builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
			builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
			builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
			NetworkRequest request = builder.build();

			final ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm == null) {
				return;
			}

			cm.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {
				@Override
				public void onAvailable(Network network) {
					super.onAvailable(network);

					NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
					//连接wifi网络
					if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

					}
					//连接移动网络
					else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

					}
					//其他
					else {

					}
				}

				@Override
				public void onLost(Network network) {
					super.onLost(network);
				}
			});
		}
	}
}

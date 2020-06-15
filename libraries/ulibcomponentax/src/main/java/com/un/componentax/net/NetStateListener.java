package com.un.componentax.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

public class NetStateListener {

	private OnNetStateChange mOnNetStateChange;

	private ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() {

		@Override
		public void onAvailable(Network network) {
			super.onAvailable(network);
			if (mOnNetStateChange != null) {
				mOnNetStateChange.onAvailable();
			}
		}

		@Override
		public void onLost(Network network) {
			super.onLost(network);
		}

		@Override
		public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
			super.onLinkPropertiesChanged(network, linkProperties);
		}

		@Override
		public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
			super.onCapabilitiesChanged(network, networkCapabilities);

			Log.i("PwLog", "onCapabilitiesChanged");

			boolean result = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);

			if (mOnNetStateChange != null) {
				if (result) {
					mOnNetStateChange.onCellularAvailable();
				} else {
					mOnNetStateChange.onCellularUnavailable();
				}
			}
		}

		@Override
		public void onLosing(Network network, int maxMsToLive) {
			super.onLosing(network, maxMsToLive);
		}

		@Override
		public void onUnavailable() {
			super.onUnavailable();
		}
	};

	public void start(Context context, OnNetStateChange onNetStateChange) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mOnNetStateChange = onNetStateChange;

			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return;
			}
			NetworkRequest request = new NetworkRequest.Builder()
					.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
					.addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
					.build();
			connectivityManager.requestNetwork(request, mNetworkCallback);
		}
	}

	public void stop(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return;
			}
			connectivityManager.unregisterNetworkCallback(mNetworkCallback);

			mOnNetStateChange = null;
		}
	}

	//可清除的单例模式
	private static volatile NetStateListener sInstance;

	private NetStateListener() {
	}

	public static NetStateListener getInstance() {
		if (sInstance == null) {
			synchronized (NetStateListener.class) {
				if (sInstance == null) {
					sInstance = new NetStateListener();
				}
			}
		}
		return sInstance;
	}

	public static void clearInstance() {
		if (sInstance != null) {
			synchronized (NetStateListener.class) {
				if (sInstance != null) {
					sInstance = null;
				}
			}
		}
	}
}

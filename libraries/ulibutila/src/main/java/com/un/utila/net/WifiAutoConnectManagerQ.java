package com.un.utila.net;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.Q)
public class WifiAutoConnectManagerQ {

	private final Object syncObj = new Object();

	private Context appContext;

	private String ssid;

	private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
		@Override
		public void onAvailable(Network network) {
			super.onAvailable(network);

			ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return;
			}
			connectivityManager.bindProcessToNetwork(network);
		}

		@Override
		public void onLost(Network network) {
			super.onLost(network);

			ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return;
			}

			connectivityManager.bindProcessToNetwork(null);
			connectivityManager.unregisterNetworkCallback(this);
		}
	};

	/**
	 * 初始化无密码的热点连接
	 */
	public void initNoPsw(Context context, String pSsid) {
		synchronized (syncObj) {
			this.appContext = context.getApplicationContext();
			this.ssid = pSsid;
		}
	}

	public void release() {
		synchronized (syncObj) {
			this.appContext = null;
			this.ssid = null;
		}
	}

	/**
	 * 开始连接
	 */
	public void enable() {
		synchronized (syncObj) {
			if (appContext == null || ssid == null || ssid.isEmpty()) {
				return;
			}

			//自动连接wifi
			WifiNetworkSpecifier wifiNetworkSpecifier = new WifiNetworkSpecifier.Builder()
					.setSsid(ssid)
					.build();

			NetworkRequest networkRequest = new NetworkRequest.Builder()
					.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
					.setNetworkSpecifier(wifiNetworkSpecifier)
					.build();

			ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return;
			}
			connectivityManager.requestNetwork(networkRequest, networkCallback);
		}
	}

	/**
	 * 停止连接
	 */
	public void disable() {
		synchronized (syncObj) {
			//清除网络连接信息
			ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return;
			}
			connectivityManager.unregisterNetworkCallback(networkCallback);
		}
	}

	//可清除的单例模式
	private static volatile WifiAutoConnectManagerQ sInstance;

	private WifiAutoConnectManagerQ() {
	}

	public static WifiAutoConnectManagerQ getInstance() {
		if (sInstance == null) {
			synchronized (WifiAutoConnectManagerQ.class) {
				if (sInstance == null) {
					sInstance = new WifiAutoConnectManagerQ();
				}
			}
		}
		return sInstance;
	}

	public static void clearInstance() {
		if (sInstance != null) {
			synchronized (WifiAutoConnectManagerQ.class) {
				if (sInstance != null) {
					sInstance = null;
				}
			}
		}
	}
}

package com.un.utila.net;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;


/**
 * 用于管理Android Q以下版本的WiFi自动连接问题
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WifiAutoConnectManagerL {

	private final Object syncObj = new Object();

	private Context appContext;

	/**
	 * 自动连接前的NetId
	 */
	private int oriNetId;

	/**
	 * 需要自动连接的NetId
	 */
	private int autoNetId;

	private String ssid;

	//	private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
	//		@Override
	//		public void onAvailable(Network network) {
	//			super.onAvailable(network);
	//
	//			//保证通过wifi连接,而非移动网络
	//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
	//				final ConnectivityManager cm = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	//				if (cm == null) {
	//					return;
	//				}
	//				cm.bindProcessToNetwork(network);
	//			}
	//		}
	//	};

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
			this.oriNetId = -1;
			this.autoNetId = -1;
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
			final WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
			if (wifiManager == null) {
				return;
			}
			final WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			WifiConfiguration wifiConfiguration = WifiUtil.getWifiConfig(
					wifiManager,
					ssid,
					"",
					WifiUtil.WIFICIPHER_NOPASS
			);
			autoNetId = wifiManager.addNetwork(wifiConfiguration);

			if (wifiInfo == null || autoNetId < 0) {
				return;
			}
			int currentNetId = wifiInfo.getNetworkId();

			//连接的WiFi一致,不进行重新连接的动作
			if (currentNetId == autoNetId) {
				return;
			}
			//记录原WiFi的NetId
			oriNetId = currentNetId;

			//连接
			wifiManager.enableNetwork(autoNetId, true);
		}
	}

	/**
	 * 停止连接
	 */
	public void disable() {
		synchronized (syncObj) {
			//清除网络连接信息
			//连接原WiFi
			final WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
			if (wifiManager == null) {
				return;
			}
			wifiManager.enableNetwork(oriNetId, true);

			oriNetId = -1;
			autoNetId = -1;
		}
	}

	//可清除的单例模式
	private static volatile WifiAutoConnectManagerL sInstance;

	private WifiAutoConnectManagerL() {
	}

	public static WifiAutoConnectManagerL getInstance() {
		if (sInstance == null) {
			synchronized (WifiAutoConnectManagerL.class) {
				if (sInstance == null) {
					sInstance = new WifiAutoConnectManagerL();
				}
			}
		}
		return sInstance;
	}

	public static void clearInstance() {
		if (sInstance != null) {
			synchronized (WifiAutoConnectManagerL.class) {
				if (sInstance != null) {
					sInstance = null;
				}
			}
		}
	}
}

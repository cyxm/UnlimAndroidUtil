package com.un.utila.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.util.Log;

public class WifiUtil {

	public static final int WIFICIPHER_NOPASS = 0,
			WIFICIPHER_WEP = 1,
			WIFICIPHER_WPA = 2;

	public static void connectWifi(Context context) {
		WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		if (wifiManager == null) {
			return;
		}
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();

		// 指定对应的SSID
		config.SSID = "\"" + "IPC365_AP-406A8E006AC5" + "\"";
		config.preSharedKey = "\"" + "\"";
		config.hiddenSSID = true;
		config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		config.status = WifiConfiguration.Status.ENABLED;

		int netId = wifiManager.addNetwork(config);
		Log.e("TAG", netId + "   ");
		// 这个方法的第一个参数是需要连接wifi网络的networkId，第二个参数是指连接当前wifi网络是否需要断开其他网络
		// 无论是否连接上，都返回true。。。。
		wifiManager.enableNetwork(netId, true);
	}


	/**
	 * 有密码连接
	 *
	 * @param ssid
	 * @param psw
	 */
	public static void connectWifiWithWpa(Context context, String ssid, String psw) {
		WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		if (wifiManager == null) {
			return;
		}
		wifiManager.disableNetwork(wifiManager.getConnectionInfo().getNetworkId());
		int netId = wifiManager.addNetwork(getWifiConfig(wifiManager, ssid, psw, WIFICIPHER_WPA));
		wifiManager.enableNetwork(netId, true);
	}

	/**
	 * 无密码连接
	 *
	 * @param ssid
	 */
	public static void connectWifiWithoutPsw(Context context, final String ssid) {
		if (android.os.Build.VERSION.SDK_INT >= 29) {

			WifiNetworkSpecifier specifier = new WifiNetworkSpecifier.Builder()
					.setSsid(ssid)
					.build();

			NetworkRequest request = new NetworkRequest.Builder()
					.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
					.setNetworkSpecifier(specifier)
					.build();

			final ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm == null) {
				return;
			}
			cm.requestNetwork(request, new ConnectivityManager.NetworkCallback() {
				@Override
				public void onAvailable(Network network) {
					super.onAvailable(network);

					cm.bindProcessToNetwork(network);
				}

				@Override
				public void onLost(Network network) {
					super.onLost(network);
					Log.i("PwLog", "onLost");
				}

				@Override
				public void onUnavailable() {
					super.onUnavailable();
					Log.i("PwLog", "onUnavailable");
				}
			});
		} else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
			if (wifiManager == null) {
				return;
			}
			final WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			WifiConfiguration wifiConfiguration = getWifiConfig(wifiManager, ssid, "", WIFICIPHER_NOPASS);
			final int netId = wifiManager.addNetwork(wifiConfiguration);

			if (wifiInfo != null && netId > 0) {
				int oriNetId = wifiInfo.getNetworkId();
				//连接的WiFi一致,不进行重新连接的动作
				if (oriNetId == netId) {
					return;
				}
				boolean disableResult = wifiManager.disableNetwork(oriNetId);
				Log.i("PwLog", "disableResult=" + (disableResult ? "1" : "0"));
				wifiManager.enableNetwork(netId, true);
			}

			//			new Timer().schedule(new TimerTask() {
			//				@Override
			//				public void run() {
			//					if (wifiInfo != null) {
			//						wifiManager.enableNetwork(wifiInfo.getNetworkId(), true);
			//					}
			//				}
			//			}, 10000);
		}
	}

	/**
	 * wifi设置
	 *
	 * @param ssid
	 * @param psw
	 */
	public static WifiConfiguration getWifiConfig(WifiManager wifiManager, String ssid, String psw, int type) {

		//初始化WifiConfiguration
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();

		//指定对应的SSID
		config.SSID = "\"" + ssid + "\"";

		//不需要密码的场景
		if (type == WIFICIPHER_NOPASS) {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		}
		//以WEP加密的场景
		else if (type == WIFICIPHER_WEP) {
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + psw + "\"";
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;

		}
		//以WPA加密的场景，自己测试时，发现热点以WPA2建立时，同样可以用这种配置连接
		else if (type == WIFICIPHER_WPA) {
			config.preSharedKey = "\"" + psw + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}

		return config;
	}
}

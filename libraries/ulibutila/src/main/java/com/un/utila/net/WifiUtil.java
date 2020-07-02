package com.un.utila.net;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WifiUtil {

	private static final int WIFICIPHER_NOPASS = 0;
	private static final int WIFICIPHER_WEP = 1;
	private static final int WIFICIPHER_WPA = 2;

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
	public static void connectWifiWithoutPsw(Context context, String ssid) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

			final WifiNetworkSuggestion suggestion =
					new WifiNetworkSuggestion.Builder()
							.setSsid(ssid)
							.build();

			final List<WifiNetworkSuggestion> suggestionsList = new ArrayList<>();
			suggestionsList.add(suggestion);

			final WifiManager wifiManager =
					(WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

			if (wifiManager == null) {
				return;
			}

			wifiManager.addNetworkSuggestions(suggestionsList);
		} else {
			WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
			if (wifiManager == null) {
				return;
			}
			int netId = wifiManager.addNetwork(getWifiConfig(wifiManager, ssid, "", WIFICIPHER_NOPASS));
			wifiManager.enableNetwork(netId, true);
		}
	}

	/**
	 * wifi设置
	 *
	 * @param ssid
	 * @param psw
	 */
	private static WifiConfiguration getWifiConfig(WifiManager wifiManager, String ssid, String psw, int type) {

		//初始化WifiConfiguration
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();

		//指定对应的SSID
		config.SSID = "\"" + ssid + "\"";

		//		//如果之前有类似的配置
		//		WifiConfiguration tempConfig = isExist(wifiManager, ssid);
		//		if (tempConfig != null) {
		//			//则清除旧有配置
		//			wifiManager.removeNetwork(tempConfig.networkId);
		//		}

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

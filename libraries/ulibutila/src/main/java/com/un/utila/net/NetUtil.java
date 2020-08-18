package com.un.utila.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Administrator on 2016/3/1.
 */
public class NetUtil {
	private Context mContext;
	private WifiManager mWm;
	private ConnectivityManager mCm;

	public NetUtil(Context context) {
		try {
			mContext = context.getApplicationContext();
			mWm = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
			mCm = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
			//mTm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isWifiConnected() {
		NetworkInfo info = mCm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (info == null) {
			return false;
		}
		// Log.e("NET","WifiConnected---->"+info.toString());
		if (info.isConnected()) {
			return true;
		}
		return false;
	}

	public boolean isWifiEnabled() {
		return mWm.isWifiEnabled();
	}

	public static boolean isMobileNetConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (info == null) {
			return false;
		}
		if (info.isConnected()) {
			return true;
		}
		return false;
	}

	public boolean isHaveAvailableNet() {
		NetworkInfo info = mCm.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		// Log.e("NET","isHaveAvailableNet "+info.isConnected()+" "+info.isAvailable());
		return info.isConnected() && info.isAvailable();
	}


	//	public boolean setWifiEnable(boolean enable) {
	//		return mWm.setWifiEnabled(enable);
	//	}

	//	public boolean setMobileNetEnable(boolean enable) {
	//		Class<?> conMgrClass = null; // ConnectivityManager类
	//		Field iConMgrField = null; // ConnectivityManager类中的字段
	//		Object iConMgr = null; // IConnectivityManager类的引用
	//		Class<?> iConMgrClass = null; // IConnectivityManager类
	//		Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法
	//
	//		try {
	//			// 取得ConnectivityManager类
	//			conMgrClass = Class.forName(mCm.getClass().getName());
	//			// 取得ConnectivityManager类中的对象mService
	//			iConMgrField = conMgrClass.getDeclaredField("mService");
	//			// 设置mService可访问
	//			iConMgrField.setAccessible(true);
	//			// 取得mService的实例化类IConnectivityManager
	//			iConMgr = iConMgrField.get(mCm);
	//			// 取得IConnectivityManager类
	//			iConMgrClass = Class.forName(iConMgr.getClass().getName());
	//			// 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
	//			setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	//			// 设置setMobileDataEnabled方法可访问
	//			setMobileDataEnabledMethod.setAccessible(true);
	//			// 调用setMobileDataEnabled方法
	//			setMobileDataEnabledMethod.invoke(iConMgr, enable);
	//			return true;
	//		} catch (ClassNotFoundException e) {
	//			e.printStackTrace();
	//		} catch (NoSuchFieldException e) {
	//			e.printStackTrace();
	//		} catch (SecurityException e) {
	//			e.printStackTrace();
	//		} catch (NoSuchMethodException e) {
	//			e.printStackTrace();
	//		} catch (IllegalArgumentException e) {
	//			e.printStackTrace();
	//		} catch (IllegalAccessException e) {
	//			e.printStackTrace();
	//		} catch (InvocationTargetException e) {
	//			e.printStackTrace();
	//		}
	//		return false;
	//	}


	//	public static enum NetProvider {
	//		Yidong, Liantong, Dianxin, UnKnown
	//	}

	//    public NetProvider getProvider(){
	//        NetProvider provider= NetProvider.UnKnown;
	//        try {
	//            String imsi = mTm.getSubscriberId();
	//            if(TextUtils.isEmpty(imsi)){
	//                if(TelephonyManager.SIM_STATE_READY == mTm.getSimState()){
	//                    String op = mTm.getSimOperator();
	//                    if(!TextUtils.isEmpty(op)){
	//                        switch (op){
	//                            case "46000":
	//                            case "46002":
	//                            case "46007":
	//                                provider= NetProvider.Yidong;
	//                                break;
	//                            case "46001":
	//                                provider= NetProvider.Liantong;
	//                                break;
	//                            case "46003":
	//                                provider= NetProvider.Dianxin;
	//                                break;
	//                            default:
	//                                provider= NetProvider.UnKnown;
	//                                break;
	//                        }
	//                    }
	//                }
	//            }else {
	//                if (imsi.startsWith("46000") || imsi.startsWith("46002")|| imsi.startsWith("46007")) {
	//                    provider = NetProvider.Yidong;
	//                } else if (imsi.startsWith("46001")) {
	//                    provider = NetProvider.Liantong;
	//                } else if (imsi.startsWith("46003")) {
	//                    provider = NetProvider.UnKnown;
	//                }
	//            }
	//        }catch (Exception e){e.printStackTrace();}
	//        return provider;
	//    }
	public enum WifiWorkType {
		Wifi2G, Wifi5G, WifiUnKnown
	}

	/**
	 * 获取网络连接类型
	 *
	 * @return
	 */
	public WifiWorkType getWifiWorkType() {
		String curSSID = getWifiSSID();
		if (TextUtils.isEmpty(curSSID)) {
			return WifiWorkType.WifiUnKnown;
		}
		List<ScanResult> scanResults = mWm.getScanResults();
		for (ScanResult scanResult : scanResults) {
			if (scanResult.SSID.equals(curSSID)) {
				int freq = scanResult.frequency;
				return getWifiWorkTypeByFreq(freq);
			}
		}
		return WifiWorkType.WifiUnKnown;
		//WifiInfo info=mWm.getConnectionInfo();
		//int freq = info.getFrequency();
	}

	public WifiWorkType getWifiWorkTypeByFreq(int freq) {
		WifiWorkType type = WifiWorkType.WifiUnKnown;
		if (freq > 2400 && freq < 2500) {
			type = WifiWorkType.Wifi2G;
		} else if (freq > 4900 && freq < 5900) {
			type = WifiWorkType.Wifi5G;
		}
		return type;
	}

	/**
	 * 判断是否为5Gwifi
	 *
	 * @return
	 */
	public boolean is5GWifi(String ssid) {
		return (getWifiWorkType() == WifiWorkType.Wifi5G) ||
				ssid.toLowerCase().contains("5g");
	}

	public WifiWorkType getWifiWorkType_discard() {
		WifiWorkType type = WifiWorkType.WifiUnKnown;
		try {
			Class<WifiManager> wmClazz = WifiManager.class;
			Method method = wmClazz.getDeclaredMethod("getFrequencyBand");
			if (method != null) {
				int iret = (int) method.invoke(mWm);
				switch (iret) {
					case 0://auto
						type = WifiWorkType.WifiUnKnown;
						break;
					case 1://5GHZ
						type = WifiWorkType.Wifi5G;
						break;
					case 2://2.4GHZ
						type = WifiWorkType.Wifi2G;
						break;
					case -1://fai
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	//	public enum NetWorkDetailType {
	//		Net2G, Net3G, Net4G, NetWifi, NetUnknown
	//	}
	//    public NetWorkDetailType getNetDetailType(){
	//        NetWorkDetailType nettype= NetWorkDetailType.NetUnknown;
	//        try{
	//            NetworkInfo info = mCm.getActiveNetworkInfo();
	//            if(info!=null&&info.isConnected()&&info.isAvailable()){
	//                if(info.getType() == ConnectivityManager.TYPE_WIFI){
	//                    nettype = NetWorkDetailType.NetWifi;
	//                }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
	//                    switch (mTm.getNetworkType()){
	//                        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
	//                            nettype= NetWorkDetailType.NetUnknown;
	//                            break;
	//                        case TelephonyManager.NETWORK_TYPE_GPRS://2G(2.5) General Packet Radia Service 114kbps
	//                        case TelephonyManager.NETWORK_TYPE_EDGE://2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
	//                        case TelephonyManager.NETWORK_TYPE_CDMA://2G 电信 Code Division Multiple Access 码分多址
	//                        case TelephonyManager.NETWORK_TYPE_1xRTT://2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
	//                        case TelephonyManager.NETWORK_TYPE_IDEN://2G Integrated Dispatch Enhanced Networks 集成数字增强型网络
	//                            nettype= NetWorkDetailType.Net2G;
	//                            break;
	//                        case TelephonyManager.NETWORK_TYPE_UMTS:// 3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
	//                        case TelephonyManager.NETWORK_TYPE_EVDO_0://3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
	//                        case TelephonyManager.NETWORK_TYPE_EVDO_A://3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
	//                        case TelephonyManager.NETWORK_TYPE_HSDPA://3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
	//                        case TelephonyManager.NETWORK_TYPE_HSUPA://3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
	//                        case TelephonyManager.NETWORK_TYPE_HSPA://3G (分HSDPA,HSUPA) High Speed Packet Access
	//                        case TelephonyManager.NETWORK_TYPE_EVDO_B://3G EV-DO Rev.B 14.7Mbps 下行 3.5G
	//                        case TelephonyManager.NETWORK_TYPE_EHRPD://3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
	//                        case TelephonyManager.NETWORK_TYPE_HSPAP://3G HSPAP 比 HSDPA 快些
	//                            nettype= NetWorkDetailType.Net3G;
	//                            break;
	//                        case TelephonyManager.NETWORK_TYPE_LTE:
	//                            nettype= NetWorkDetailType.Net4G;
	//                            break;
	//                        default:
	//                            nettype= NetWorkDetailType.NetUnknown;
	//                            break;
	//
	//                    }
	//                }
	//            }
	//        }catch (Exception e){e.printStackTrace();}
	//        return nettype;
	//    }

	public String getWifiSSID() {
		WifiInfo info = mWm.getConnectionInfo();
		try {
			if (info != null) {
				return eraseStringQuotes(info.getSSID());
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] getWifiBSSIDArr() {
		byte[] bssidarr = new byte[6];
		String bssid = getWifiBSSID();
		String[] split_ssid = bssid.split(":");
		if (split_ssid.length != 6) {
			return bssidarr;
		}
		for (int i = 0; i < split_ssid.length; i++) {
			bssidarr[i] = (byte) Integer.parseInt(split_ssid[i], 16);
		}
		return bssidarr;
	}

	public String getWifiBSSID() {
		WifiInfo info = mWm.getConnectionInfo();
		if (info != null) {
			return info.getBSSID();
		} else {
			return null;
		}
	}

	public int getRssi() {
		WifiInfo info = mWm.getConnectionInfo();
		return info.getRssi();
	}

	//	public byte[] getOriginSSID() {
	//		if (!getMobileModel()) {
	//			return null;
	//		}
	//		WifiInfo info = mWm.getConnectionInfo();
	//		try {
	//			Field fd = WifiInfo.class.getDeclaredField("mWifiSsid");
	//			fd.setAccessible(true);
	//			Object wifissid = fd.get(info);
	//			//LogUtils.e("TestNet","----wifissid "+wifissid);
	//			Field osFd = wifissid.getClass().getDeclaredField("octets");
	//			ByteArrayOutputStream bos = (ByteArrayOutputStream) osFd.get(wifissid);
	//			//LogUtils.e("TestNet","----bos "+bos);
	//			return bos.toByteArray();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			return null;
	//		}
	//	}

	//	public String getMacAddr() {
	//		String mac = getMacFromWifi();
	//		if (TextUtils.isEmpty(mac)) {
	//			mac = getMacFromCmd();
	//		}
	//		return mac;
	//	}

	private String getMacFromWifi() {
		WifiInfo info = mWm.getConnectionInfo();
		if (info == null) {
			return null;
		}
		return info.getMacAddress();
	}

	//	private String getMacFromCmd() {//需要设备支持busybox工具
	//		String result = "";
	//		result = CmdUtil.callCmd("busybox ifconfig", "HWaddr");
	//
	//		if (result == null || result.length() <= 0) {
	//			return null;
	//		}
	//		// 对该行数据进行解析
	//		// 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
	//		if (result.length() > 0 && result.contains("HWaddr") == true) {
	//			String Mac = result.substring(result.indexOf("HWaddr") + 6,
	//					result.length() - 1);
	//			if (Mac.length() > 1) {
	//				result = Mac.replaceAll(" ", "");
	//			}
	//		}
	//		return result;
	//	}

	public String getGateWay() {
		DhcpInfo di = mWm.getDhcpInfo();
		long gatewayipl = di.gateway;
		return intToIp((int) gatewayipl);
	}

	/**
	 * @return
	 */
	public String getBroadcastIp() {
		int gateWay = getGateWayInt();
		int submask = getSubMaskInt();
		int broadcastIp = gateWay | ~submask;
		return intToIp(broadcastIp);
	}

	public String getSubMask() {
		DhcpInfo di = mWm.getDhcpInfo();
		long mask = di.netmask;
		return intToIp((int) mask);
	}

	public int getGateWayInt() {
		DhcpInfo di = mWm.getDhcpInfo();
		return di.gateway;
	}

	public int getSubMaskInt() {
		DhcpInfo di = mWm.getDhcpInfo();
		return di.netmask;
	}

	public String getIp() {
		NetType type = getNetType();
		if (type == NetType.NetUnKnown) {
			return null;
		} else if (type == NetType.NetWifi) {
			return getWifiIp();
		} else if (type == NetType.NetMobile) {
			return getNetMobileIp();
		}
		return null;
	}

	public long ipStrToInt(String ip) {
		String[] mIpArr = ip.split("\\.");
		long ipint = 0L;
		for (int i = mIpArr.length - 1; i >= 0; i--) {
			ipint = (ipint << 8) | Integer.valueOf(mIpArr[i]);
		}
		return ipint;
	}

	public static enum NetType {
		NetMobile, NetWifi, NetUnKnown
	}

	public NetType getNetType() {
		NetworkInfo networkInfo = mCm.getActiveNetworkInfo();
		if (networkInfo == null) {
			return NetType.NetUnKnown;
		}
		if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return NetType.NetWifi;
		}
		if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return NetType.NetMobile;
		}
		return NetType.NetUnKnown;
	}

	private String getNetMobileIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private String getWifiIp() {
		WifiInfo wInfo = mWm.getConnectionInfo();
		int ipaddr = wInfo.getIpAddress();
		return intToIp(ipaddr);
	}

	public static String intToIp(int i) {
		return (i & 0xFF) + "." +
				((i >> 8) & 0xFF) + "." +
				((i >> 16) & 0xFF) + "." +
				(i >> 24 & 0xFF);
	}

	public String getCurCipherType() {
		if (!isWifiConnected()) {
			return null;
		}
		WifiInfo curInfo = mWm.getConnectionInfo();
		return getCipherType(curInfo.getSSID());
	}

	private String eraseStringQuotes(String target) {
		String strOut = target.trim();
		return strOut.replace("\"", "");
	}

	public String getCipherType(String ssid) {
		List<ScanResult> list = mWm.getScanResults();
		String curSsid = eraseStringQuotes(ssid);
		for (ScanResult scanResult : list) {
			// LogUtils.v("CipherType",scanResult.SSID+
			// "  "+scanResult.capabilities);
			if (scanResult.SSID.contains(curSsid)) {
				return scanResult.capabilities;
			}
		}
		return null;
	}

	public String getCurWifiCapability() {
		String capability = this.getCurCipherType();
		if (capability == null) {
			return null;
		}
		if (capability.contains("WEP")) {
			return "WEPAUTO";
		} else if (capability.contains("WPA-EAP")) {
			if (capability.contains("TKIP+CCMP") || capability.contains("CCMP")) {
				return "WPA";
			} else {
				return "WPA";
			}
		} else if (capability.contains("WPA2-EAP")) {
			if (capability.contains("TKIP+CCMP") || capability.contains("CCMP")) {
				return "WPA2";
			} else {
				return "WPA2";
			}

		} else if (capability.contains("WPA-PSK")) {
			if (capability.contains("TKIP+CCMP") || capability.contains("CCMP")) {
				return "WPA";
			} else {
				return "WPA";
			}
		} else if (capability.contains("WPA2-PSK")) {
			if (capability.contains("TKIP+CCMP") || capability.contains("CCMP")) {
				return "WPA2";
			} else {
				return "WPA2";
			}
		} else {
			return "OPEN";
		}
	}

	public List<String> getUseableWifiList() {
		List<String> ret = new ArrayList<String>();
		List<ScanResult> r = mWm.getScanResults();
		for (ScanResult s : r) {
			ret.add(s.SSID);
		}
		return ret;
	}

	//	private boolean getMobileModel() {
	//		String model = android.os.Build.MODEL;
	//		if (!StringUtil.isNotEmpty(model)) {
	//			return false;
	//		}
	//		if (model.equals("vivo Y35")) {
	//			return false;
	//		}
	//		return true;
	//	}

	public static boolean isNetTypeC(int ip) {
		int net = ip & 0xFF;
		if (net >= 0xC0 && net < 0xE0) {
			return true;
		} else {
			return false;
		}
	}

	public static void registerNetState(Context context) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			final ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
			if (cm == null) {
				return;
			}

			NetworkRequest.Builder builder = new NetworkRequest.Builder();
			NetworkRequest request = builder
					.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
					.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
					.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
					.build();

			cm.requestNetwork(request, new ConnectivityManager.NetworkCallback() {

				@Override
				public void onAvailable(Network network) {
					NetworkInfo.State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
					if (NetworkInfo.State.CONNECTED == state) {
						Log.i("通知", "WIFI网络已连接");
					} else {
						Log.i("通知", "WIFI网络已断开");
					}
				}

				@Override
				public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
					super.onCapabilitiesChanged(network, networkCapabilities);
				}
			});
		}
	}
}

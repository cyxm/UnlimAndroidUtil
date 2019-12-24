package com.un.utilj.net;

public class IpAddrUtil {

	public static int str2Int(String ip) {
		String[] mIpArr = ip.split("\\.");
		int ipint = 0;
		for (int i = mIpArr.length - 1; i >= 0; i--) {
			ipint = (ipint << 8) | Integer.valueOf(mIpArr[i]);
		}
		return ipint;
	}
}

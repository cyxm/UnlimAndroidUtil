package com.un.utilj.net;

public class IpAddrUtil {

	public static int str2Int(String ip) {
		String[] mIpArr = ip.split("\\.");

		int ipint = 0;

		if (mIpArr.length != 4) {
			return ipint;
		}

		try {
			for (int i = mIpArr.length - 1; i >= 0; i--) {
				int seg = Integer.parseInt(mIpArr[i]);
				if (seg > 255) {
					ipint = 0;
					break;
				}
				ipint = (ipint << 8) | seg;
			}
		} catch (Exception e) {
			ipint = 0;
		}

		return ipint;
	}

	public static String intToStr(int i) {
		return (i & 0xFF) + "." +
				((i >> 8) & 0xFF) + "." +
				((i >> 16) & 0xFF) + "." +
				(i >> 24 & 0xFF);
	}
}

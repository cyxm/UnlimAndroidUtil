package com.un.utila.sharefile;

public class FileMimeTypeUtil {
	/**
	 * 获取mine_type
	 *
	 * @param path
	 *
	 * @return
	 */
	public static String getMimeType(String path) {
		String lowerPath = path.toLowerCase();
		if (lowerPath.endsWith("jpg") || lowerPath.endsWith("jpeg")) {
			return "image/jpeg";
		} else if (lowerPath.endsWith("png")) {
			return "image/png";
		} else if (lowerPath.endsWith("gif")) {
			return "image/gif";
		} else if (lowerPath.endsWith("mp4") || lowerPath.endsWith("mpeg4")) {
			return "video/mp4";
		} else if (lowerPath.endsWith("3gp")) {
			return "video/3gp";
		}
		return "";
	}

	public static boolean isImage(String type) {
		return type != null && type.contains("image");
	}

	public static boolean isVideo(String type) {
		return type != null && type.contains("video");
	}
}

package com.un.utila.res;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 */
public class AssetUtil {

	public static byte[] readFileByteArray(Context context, String fileName) {
		try {
			InputStream inStream = context.getResources().getAssets().open(fileName);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			outStream.close();
			inStream.close();
			return outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

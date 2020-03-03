package com.un.utila.media;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Range;

import java.io.IOException;
import java.util.List;

public class MediaCodecUtil {

	/**
	 * 检查硬件解码支持
	 *
	 * @param outSupportFormat
	 * 		如果支持硬件解码,此列表返回支持的颜色格式<br/>
	 * 		枚举值: {@link MediaCodecInfo.CodecCapabilities}
	 *
	 * @return 是否支持硬件解码
	 */
	public static boolean checkDecoder(List<Integer> outSupportFormat) {
		MediaCodec mediaCodec;
		try {
			mediaCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
			MediaCodecInfo mediaCodecInfo = mediaCodec.getCodecInfo();
			MediaCodecInfo.CodecCapabilities c =
					mediaCodecInfo.getCapabilitiesForType(MediaFormat.MIMETYPE_VIDEO_AVC);

			Range<Integer> rangeWidth = c.getVideoCapabilities().getSupportedWidths();
			Range<Integer> rangeHeight = c.getVideoCapabilities().getSupportedHeights();

			for (int format : c.colorFormats) {
				outSupportFormat.add(format);
			}

			mediaCodec.release();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static MediaCodecInfo selectCodec(String mimeType) {
		int numCodecs = MediaCodecList.getCodecCount();
		for (int i = 0; i < numCodecs; i++) {
			MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
			if (codecInfo.isEncoder()) {
				continue;
			}
			String[] types = codecInfo.getSupportedTypes();
			for (int j = 0; j < types.length; j++) {
				if (types[j].equalsIgnoreCase(mimeType)) {
					return codecInfo;
				}
			}
		}
		return null;
	}
}

package com.un.utila.sharefile;

import android.net.Uri;

public class ModelMediaUri {
	long id;
	Uri mediaUri;
	long fileTime;
	String mimeType;

	public ModelMediaUri(long id, Uri mediaUri, long fileTime, String mimeType) {
		this.id = id;
		this.mediaUri = mediaUri;
		this.fileTime = fileTime;
		this.mimeType = mimeType;
	}

	public Uri getMediaUri() {
		return mediaUri;
	}

	public void setMediaUri(Uri mediaUri) {
		this.mediaUri = mediaUri;
	}

	public long getFileTime() {
		return fileTime;
	}

	public void setFileTime(long fileTime) {
		this.fileTime = fileTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public boolean isImage() {
		return mimeType != null && mimeType.contains("image");
	}

	public boolean isVideo() {
		return mimeType != null && mimeType.contains("video");
	}
}

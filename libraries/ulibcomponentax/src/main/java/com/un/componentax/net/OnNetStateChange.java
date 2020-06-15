package com.un.componentax.net;

public interface OnNetStateChange {
	void onAvailable();

	void onCellularAvailable();

	void onCellularUnavailable();
}

package com.un.componentax.app;

import android.app.Application;
import android.content.res.Configuration;

public class AppBase extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}
}

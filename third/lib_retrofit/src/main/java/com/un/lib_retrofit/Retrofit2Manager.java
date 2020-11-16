package com.un.lib_retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Manager {

	private Retrofit retrofit;

	public Retrofit createWithGsonConverter(String url) {
		retrofit = new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl(url)
				.build();
		return retrofit;
	}

	public <T> T getApi(Class<T> clz) {
		return retrofit.create(clz);
	}

	//单例
	private Retrofit2Manager() {
	}

	private static class SingletonInstance {
		private static final Retrofit2Manager INSTANCE = new Retrofit2Manager();
	}

	public static Retrofit2Manager getInstance() {
		return SingletonInstance.INSTANCE;
	}
}

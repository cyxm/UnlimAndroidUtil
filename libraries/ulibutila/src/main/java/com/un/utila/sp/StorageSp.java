package com.un.utila.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class StorageSp implements IStorageSp {

	private Context context;
	private String spName;

	@Override
	public void init(Context context, String spName) {
		this.context = context;
		this.spName = spName;
	}

	@Override
	public SharedPreferences getSp() {
		return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public boolean getBooleanDefaultFalse(String key) {
		return getSp().getBoolean(key, false);
	}

	@Override
	public boolean getBooleanDefaultTrue(String key) {
		return getSp().getBoolean(key, true);
	}

	@Override
	public void setBoolean(String key, boolean value) {
		getSp().edit().putBoolean(key, value).apply();
	}

	@Override
	public int getIntDefaultZero(String key) {
		return getSp().getInt(key, 0);
	}

	@Override
	public int getIntDefaultMinus(String key) {
		return getSp().getInt(key, -1);
	}

	@Override
	public void setInt(String key, int value) {
		getSp().edit().putInt(key, value).apply();
	}

	@Override
	public long getLongDefaultZero(String key) {
		return getSp().getLong(key, 0);
	}

	@Override
	public long getLongDefaultMinus(String key) {
		return getSp().getLong(key, -1);
	}

	@Override
	public void setLong(String key, long value) {
		getSp().edit().putLong(key, value).apply();
	}

	@Override
	public float getFloatDefaultZero(String key) {
		return getSp().getFloat(key, 0);
	}

	@Override
	public float getFloatDefaultMinus(String key) {
		return getSp().getFloat(key, -1);
	}

	@Override
	public void setFloat(String key, float value) {
		getSp().edit().putFloat(key, value).apply();
	}

	@Override
	public String getStringDefaultNull(String key) {
		return getSp().getString(key, null);
	}

	@Override
	public String getStringDefaultEmpty(String key) {
		return getSp().getString(key, "");
	}

	@Override
	public void setString(String key, String value) {
		getSp().edit().putString(key, value).apply();
	}

	@Override
	public Set<String> getStringSetDefaultNull(String key) {
		return getSp().getStringSet(key, null);
	}

	@Override
	public Set<String> getStringSetDefaultEmpty(String key) {
		return getSp().getStringSet(key, new HashSet<String>());
	}

	@Override
	public void setStringSet(String key, Set<String> value) {
		getSp().edit().putStringSet(key, value).apply();
	}

	@Override
	public void remove(String key) {
		getSp().edit().remove(key).apply();
	}
}

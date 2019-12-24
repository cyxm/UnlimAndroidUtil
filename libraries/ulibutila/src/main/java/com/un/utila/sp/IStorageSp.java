package com.un.utila.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * SharePrefrences的数据操作接口
 */
public interface IStorageSp {

	/**
	 * 初始化接口
	 *
	 * @param context
	 * 		推荐使用application的context,防止内存泄漏
	 * @param spName
	 * 		文件名
	 */
	void init(Context context, String spName);

	SharedPreferences getSp();

	/**
	 * 删除所有数据
	 *
	 * @return 是否删除成功
	 */
	boolean delete();

	boolean getBooleanDefaultFalse(String key);

	boolean getBooleanDefaultTrue(String key);

	void setBoolean(String key, boolean value);

	int getIntDefaultZero(String key);

	int getIntDefaultMinus(String key);

	void setInt(String key, int value);

	long getLongDefaultZero(String key);

	long getLongDefaultMinus(String key);

	void setLong(String key, long value);

	float getFloatDefaultZero(String key);

	float getFloatDefaultMinus(String key);

	void setFloat(String key, float value);

	String getStringDefaultNull(String key);

	String getStringDefaultEmpty(String key);

	void setString(String key, String value);

	Set<String> getStringSetDefaultNull(String key);

	Set<String> getStringSetDefaultEmpty(String key);

	void setStringSet(String key, Set<String> value);

	/**
	 * 移除key
	 *
	 * @param key
	 * 		key值
	 */
	void remove(String key);
}

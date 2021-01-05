package com.un.utilj.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射工具类
 */
public class ReflectUtil {

	public static Object constructor(Class clz, Class[] types, Object[] args) {
		//检查输入参数
		if (clz == null || types == null || args == null) {
			return null;
		}
		if (types.length != args.length) {
			return null;
		}
		//结果值
		Object result = null;
		//使用构造函数构造对象
		try {
			Constructor constructor = clz.getDeclaredConstructor(types);
			result = constructor.newInstance(args);
		} catch (NoSuchMethodException
				| IllegalAccessException
				| InstantiationException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
}

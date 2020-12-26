package com.un.utilj.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射工具类
 */
public class ReflectUtil {

	public static Object constructor(Class clz, Object... args) {
		//检查输入参数
		if (clz == null || args == null) {
			return null;
		}
		//结果值
		Object result = null;
		//从参数获取参数类型
		int length = args.length;
		Class[] paramClzArray = new Class[args.length];
		for (int i = 0; i < length; i++) {
			paramClzArray[i] = args[i].getClass();
		}
		//使用构造函数构造对象
		try {
			Constructor constructor = clz.getDeclaredConstructor(paramClzArray);
			result = constructor.newInstance(args);
		} catch (NoSuchMethodException
				| IllegalAccessException
				| InstantiationException
				| InvocationTargetException e) {
		}
		return result;
	}
}

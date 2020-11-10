package com.un.utilj.regular;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegularUtil {

	public static boolean isMatch(String param, String pattern) {
		return param != null && pattern != null && Pattern.compile(pattern).matcher(param).matches();
	}

	public static boolean isNumber(String param) {
		return param != null && Pattern.compile("[0-9]*").matcher(param).matches();
	}

	public static List<String> splitWithUpcase(String param) {
		List<String> array = new ArrayList<>();
		if (param == null || param.isEmpty()) {
			return array;
		}
		Pattern pattern = Pattern.compile("[A-Z]{1}[a-z0-9]*");
		Matcher matcher = pattern.matcher(param);
		while (matcher.find()) {
			array.add(matcher.group());
		}
		return array;
	}
}

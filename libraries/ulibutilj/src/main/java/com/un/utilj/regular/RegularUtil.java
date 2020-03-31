package com.un.utilj.regular;

import java.util.regex.Pattern;

public class RegularUtil {

	public static boolean isMatch(String param, String pattern) {
		return param != null && pattern != null && Pattern.compile(pattern).matcher(param).matches();
	}

	public static boolean isNumber(String param) {
		return param != null && Pattern.compile("[0-9]*").matcher(param).matches();
	}
}

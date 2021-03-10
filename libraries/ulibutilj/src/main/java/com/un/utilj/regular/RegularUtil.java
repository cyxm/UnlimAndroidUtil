package com.un.utilj.regular;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegularUtil {

	/**
	 * 判断输入字符串和模式是否匹配
	 *
	 * @param param
	 * 		待检验字符串
	 * @param pattern
	 * 		正则模式
	 *
	 * @return 是否匹配
	 */
	public static boolean isMatch(String param, String pattern) {
		return param != null && pattern != null && Pattern.compile(pattern).matcher(param).matches();
	}

	/**
	 * 判断输入字符串是否是纯数字
	 *
	 * @param param
	 * 		待检验字符串
	 *
	 * @return 是否是纯数字
	 */
	public static boolean isNumber(String param) {
		return param != null && Pattern.compile("[0-9]+").matcher(param).matches();
	}

	/**
	 * 分离以大写字母开头的单词
	 *
	 * @param param
	 * 		待分离的字符串
	 *
	 * @return 分离出的单词组
	 */
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

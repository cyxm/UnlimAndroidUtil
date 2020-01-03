package com.un.utilj.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MixCollectionUtil {

	public static void add(Map<String, List> map, String key, Object value) {
		List listInMap = map.get(key);
		if (listInMap == null) {
			listInMap = new ArrayList<>();
			listInMap.add(value);

			map.put(key, listInMap);
		} else {
			listInMap.add(value);
		}
	}

	public static void add(Map<String, List> map, List list, String key, Object value) {
		List listInMap = map.get(key);
		if (listInMap == null) {
			listInMap = new ArrayList<>();
			listInMap.add(value);

			map.put(key, listInMap);
			list.add(listInMap);
		} else {
			listInMap.add(value);
		}
	}
}

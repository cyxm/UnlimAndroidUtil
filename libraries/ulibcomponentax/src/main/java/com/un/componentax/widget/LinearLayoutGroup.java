package com.un.componentax.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.un.utilj.collect.MixCollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinearLayoutGroup extends LinearLayout {

	public LinearLayoutGroup(Context context) {
		super(context);
	}

	public LinearLayoutGroup(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public LinearLayoutGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public static class Build {

		ItfViewAdapter adapter;

		Map<String, List> dataMap = new HashMap<>();
		List<List> dataList = new ArrayList<>();

		Map<String, Object> mapItem = new HashMap<>();

		public Build add(String groupName, String key, Object child) {
			MixCollectionUtil.add(dataMap, dataList, groupName, child);
			mapItem.put(key, child);
			return this;
		}

		public Map<String, Object> getItems() {
			return mapItem;
		}

		public Build setAdapter(ItfViewAdapter adapter) {
			this.adapter = adapter;
			return this;
		}

		public LinearLayoutGroup build(Context context) {
			LinearLayoutGroup parent = new LinearLayoutGroup(context);
			parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			parent.setOrientation(LinearLayout.VERTICAL);
			if (dataList.isEmpty()) {
				return parent;
			} else {
				for (List childList : dataList) {
					ViewGroup[] groupView = adapter.genGroup(parent);
					ViewGroup firstGroup = groupView[0];
					ViewGroup lastGroup = groupView[groupView.length - 1];

					parent.addView(firstGroup);

					int childCount = childList.size();
					for (int i = 0; i < childCount; i++) {
						Object child = childList.get(i);
						View v = adapter.genView(lastGroup, child);
						if (v == null) {
							continue;
						}
						v.setTag(child);
						if (adapter != null) {
							adapter.adapter(v, child);
						}
						lastGroup.addView(v, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
						if (i != childCount - 1) {
							if (adapter != null) {
								lastGroup.addView(adapter.genSep(lastGroup));
							}

						}
					}
				}
				return parent;
			}
		}
	}
}

package com.un.componentax.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

		int resId;

		ItfViewAdapter adapter;

		ItfViewGen gen;

		Map<String, List> dataMap = new HashMap<>();
		List<List> dataList = new ArrayList<>();

		public Build add(String groupName, Object child) {
			MixCollectionUtil.add(dataMap, dataList, groupName, child);
			return this;
		}

		public Build setResId(int resId) {
			this.resId = resId;
			return this;
		}

		public Build setAdapter(ItfViewAdapter adapter) {
			this.adapter = adapter;
			return this;
		}

		public Build setGenerator(ItfViewGen gen) {
			this.gen = gen;
			return this;
		}

		public LinearLayoutGroup build(Context context) {
			LayoutInflater li = LayoutInflater.from(context);

			LinearLayoutGroup parent = new LinearLayoutGroup(context);
			parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			parent.setOrientation(LinearLayout.VERTICAL);
			if (dataList.isEmpty()) {
				return parent;
			} else {
				for (List childList : dataList) {
					ViewGroup[] groupView = gen.genGroup();
					ViewGroup firstGroup = groupView[0];
					ViewGroup lastGroup = groupView[groupView.length - 1];

					parent.addView(firstGroup);

					int childCount = childList.size();
					for (int i = 0; i < childCount; i++) {
						Object child = childList.get(i);
						View v = li.inflate(resId, lastGroup, false);
						if (adapter != null) {
							adapter.adapter(v, child);
						}
						lastGroup.addView(v, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					}
				}
				return parent;
			}
		}
	}
}

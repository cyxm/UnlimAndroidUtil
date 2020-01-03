package com.un.componentax.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
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

		Map<String, List> dataMap = new HashMap<>();
		List<List> dataList = new ArrayList<>();

		public Build add(String groupName, Object child) {
			MixCollectionUtil.add(dataMap, dataList, groupName, child);
			return this;
		}

		public void setAdapter() {

		}

		public LinearLayoutGroup build(Context context) {
			LinearLayoutGroup parent = new LinearLayoutGroup(context);
			parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			if (dataList.isEmpty()) {
				return parent;
			} else {
				for (List childList : dataList) {
					LinearLayout groupView = new LinearLayout(context);
					groupView.setBackgroundColor(Color.BLUE);
					//					group.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					parent.addView(groupView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

					for (Object child : childList) {
						LinearLayout childView = new LinearLayout(context);
						childView.setBackgroundColor(Color.YELLOW);
						groupView.addView(childView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50));
					}
				}
				return parent;
			}
		}
	}
}

package com.un.componentax.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.un.utila.viewhelp.ViewClipUtil;
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

		public LinearLayoutGroup build(Context context) {
			LayoutInflater li = LayoutInflater.from(context);

			LinearLayoutGroup parent = new LinearLayoutGroup(context);
			parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			parent.setOrientation(LinearLayout.VERTICAL);
			if (dataList.isEmpty()) {
				return parent;
			} else {
				for (List childList : dataList) {
					LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
					);
					ll.setMargins(20, 20, 20, 20);

					CardView cardView = new CardView(context);
					cardView.setCardElevation(6);

					parent.addView(cardView, ll);

					LinearLayout groupView = new LinearLayout(context);
					groupView.setOrientation(LinearLayout.VERTICAL);
					groupView.setBackgroundColor(Color.WHITE);
					ViewClipUtil.clipRound(groupView, 10);

					cardView.addView(groupView, new ViewGroup.LayoutParams(
									ViewGroup.LayoutParams.MATCH_PARENT,
									ViewGroup.LayoutParams.WRAP_CONTENT
							)
					);

					for (Object child : childList) {
						View v = li.inflate(resId, groupView, false);
						if (adapter != null) {
							adapter.adapter(v, child);
						}
						groupView.addView(v, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					}
				}
				return parent;
			}
		}
	}
}

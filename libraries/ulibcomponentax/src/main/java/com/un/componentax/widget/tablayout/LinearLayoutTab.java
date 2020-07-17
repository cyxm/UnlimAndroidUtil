package com.un.componentax.widget.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.un.utila.event.ThrottleClickEventAbstract;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class LinearLayoutTab extends LinearLayout implements ItfTabLayout {

	Map<Object, View> viewMap = new HashMap<>();

	Object currentTag = null;

	ItfOnTabLayoutChange onTabLayoutChange;

	ItfOnTabLayoutChange onTabLayoutChangeNotify;

	ItfTabViewStateAdapter tabViewStateAdapter;

	public LinearLayoutTab(Context context) {
		super(context);
		init();
	}

	public LinearLayoutTab(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LinearLayoutTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	protected void init() {
		setOrientation(HORIZONTAL);
	}

	@Override
	public void addTab(View v, Object tag) {
		viewMap.put(tag, v);

		v.setTag(tag);
		LinearLayout.LayoutParams layoutParams = null;
		if (getOrientation() == HORIZONTAL) {
			layoutParams = new LinearLayout.LayoutParams(
					0,
					LayoutParams.WRAP_CONTENT,
					1
			);
		} else {
			layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					0,
					1
			);
		}
		addView(v, layoutParams);

		v.setOnClickListener(new ThrottleClickEventAbstract() {
			@Override
			public void onThrottleClick(View v) {
				if (onTabLayoutChangeNotify != null) {
					boolean isHandled = onTabLayoutChangeNotify.onBefore(currentTag, v.getTag());
					if (isHandled) {
						return;
					}
					Object lastTag = currentTag;
					currentTag = v.getTag();
					onTabLayoutChangeNotify.onAfter(lastTag, currentTag);
				} else {
					changeTab(v.getTag());
				}
			}
		});
	}

	@Override
	public View getTab(Object tag) {
		return viewMap.get(tag);
	}

	@Override
	public void setOnTabChange(ItfOnTabLayoutChange itf) {
		this.onTabLayoutChange = itf;
	}

	@Override
	public void setOnTabChangeNotify(ItfOnTabLayoutChange itf) {
		this.onTabLayoutChangeNotify = itf;
	}

	@Override
	public void setTabViewAdapter(ItfTabViewStateAdapter itf) {
		this.tabViewStateAdapter = itf;
	}

	@Override
	public void changeTab(Object tag) {

		if (onTabLayoutChange != null) {
			boolean isHandled = onTabLayoutChange.onBefore(currentTag, tag);
			if (isHandled) {
				return;
			}
		}

		Object lastTag = currentTag;
		currentTag = tag;

		if (tabViewStateAdapter != null) {
			for (Object key : viewMap.keySet()) {
				if (key.equals(tag)) {
					tabViewStateAdapter.changeState(viewMap.get(key), key, true);
				} else {
					tabViewStateAdapter.changeState(viewMap.get(key), key, false);
				}
			}
		}

		if (onTabLayoutChange != null) {
			onTabLayoutChange.onAfter(lastTag, currentTag);
		}
	}

	@Override
	public void changeTabWithoutCallback(Object tag) {
		if (tabViewStateAdapter != null) {
			for (Object key : viewMap.keySet()) {
				if (key.equals(tag)) {
					tabViewStateAdapter.changeState(viewMap.get(key), key, true);
				} else {
					tabViewStateAdapter.changeState(viewMap.get(key), key, false);
				}
			}
		}
	}
}

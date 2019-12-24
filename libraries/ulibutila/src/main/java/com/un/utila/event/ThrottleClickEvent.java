package com.un.utila.event;

import android.view.View;

/**
 * 限制点击事件的速率,通过传参实现
 */
public class ThrottleClickEvent implements View.OnClickListener {

	/**
	 * 默认的点击最小间隔
	 * 单位:ms
	 */
	private int mClickMinInterval = 1000;

	/**
	 * 上次点击的时间,第一次创建默认为0
	 */
	private long mLastSystemMilli = 0;

	View.OnClickListener mOnClickEvent;

	public ThrottleClickEvent(View.OnClickListener onClickListener) {
		mOnClickEvent = onClickListener;
	}

	public ThrottleClickEvent(int interval, View.OnClickListener onClickListener) {
		mClickMinInterval = interval;
		mOnClickEvent = onClickListener;
	}

	/**
	 * 是否可触发下一个事件
	 *
	 * @return
	 */
	private boolean canTriggerNextEvent() {
		return System.currentTimeMillis() - mLastSystemMilli >= mClickMinInterval;
	}

	@Override
	public void onClick(View v) {
		if (!canTriggerNextEvent()) {
			return;
		}
		mLastSystemMilli = System.currentTimeMillis();
		if (mOnClickEvent != null) {
			mOnClickEvent.onClick(v);
		}
	}
}

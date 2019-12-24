package com.un.utila.event;

import android.view.View;

/**
 * 限制点击事件的速率,通过重载抽象函数实现
 */
public abstract class ThrottleClickEventAbstract implements View.OnClickListener {

	/**
	 * 默认的点击最小间隔
	 * 单位:ms
	 */
	private int mClickMinInterval = 1000;

	/**
	 * 上次点击的时间,第一次创建默认为0
	 */
	private long mLastSystemMilli = 0;

	public ThrottleClickEventAbstract() {
	}

	public ThrottleClickEventAbstract(int interval) {
		mClickMinInterval = interval;
	}

	@Override
	public void onClick(View v) {
		if (!canTriggerNextEvent()) {
			return;
		}
		mLastSystemMilli = System.currentTimeMillis();
		onThrottleClick(v);
	}

	/**
	 * 是否可触发下一个事件
	 *
	 * @return
	 */
	private boolean canTriggerNextEvent() {
		return System.currentTimeMillis() - mLastSystemMilli >= mClickMinInterval;
	}

	public abstract void onThrottleClick(View v);
}

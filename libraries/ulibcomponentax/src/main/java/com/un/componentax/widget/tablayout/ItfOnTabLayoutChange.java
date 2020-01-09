package com.un.componentax.widget.tablayout;

/**
 * Tab更改接口
 */
public interface ItfOnTabLayoutChange {

	boolean onBefore(Object tag);

	void onProgress(Object ori, Object target, int progress);

	void onAfter(Object ori, Object target);
}

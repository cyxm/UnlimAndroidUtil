package com.un.componentax.widget.tablayout;

public abstract class OnTabLayoutChangeSimple implements ItfOnTabLayoutChange {
	@Override
	public boolean onBefore(Object ori, Object target) {
		return false;
	}

	@Override
	public void onProgress(Object ori, Object target, int progress) {

	}
}

package com.un.utilax.livedata;

import androidx.lifecycle.Observer;

public abstract class ObserverCheck<T> implements Observer<T> {
	@Override
	public void onChanged(T t) {
		if (t == null) {
			return;
		}
		onChangeWithCheck(t);
	}

	public abstract void onChangeWithCheck(T t);
}

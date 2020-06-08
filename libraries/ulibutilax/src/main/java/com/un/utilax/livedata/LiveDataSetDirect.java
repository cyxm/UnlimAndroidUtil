package com.un.utilax.livedata;

import androidx.lifecycle.MutableLiveData;

public class LiveDataSetDirect<T> extends MutableLiveData<T> {

	public boolean isEmpty() {
		return getValue() == null;
	}

	public void post() {
		super.postValue(super.getValue());
	}

	public void set() {
		super.setValue(super.getValue());
	}

	public void clear() {
		postValue(null);
	}
}

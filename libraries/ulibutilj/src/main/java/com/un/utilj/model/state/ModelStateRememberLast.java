package com.un.utilj.model.state;


import com.un.utilj.model.state.base.IStateRememberLast;

public class ModelStateRememberLast<T> extends ModelStateContainer<T> implements IStateRememberLast<T> {

	/**
	 * 上次数据状态
	 */
	private int lastState;

	public ModelStateRememberLast(int initState, int state) {
		super(state);
		this.lastState = initState;
	}

	public ModelStateRememberLast(int initState, int state, T data) {
		super(state, data);
		this.lastState = initState;
	}

	@Override
	public int getLastState() {
		return lastState;
	}

	@Override
	public void setLastState(int state) {
		this.lastState = state;
	}

	@Override
	public void changeState(int state) {
		setLastState(getState());
		setState(state);
	}
}

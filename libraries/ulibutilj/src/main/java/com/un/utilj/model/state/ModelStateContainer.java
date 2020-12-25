package com.un.utilj.model.state;

import com.un.utilj.model.state.base.IState;

/**
 * 可记录上次状态的实现类
 *
 * @param <T>
 */
public abstract class ModelStateContainer<T> implements IState<T> {

	/**
	 * 数据状态
	 */
	private int state;

	/**
	 * 泛型数据
	 */
	private T data;

	/**
	 * 构造函数,必须有初始状态
	 *
	 * @param state
	 * 		初始状态
	 */
	public ModelStateContainer(int state) {
		this.state = state;
		this.data = null;
	}

	/**
	 * 构造函数,必须有初始状态
	 *
	 * @param state
	 * 		初始状态
	 * @param data
	 * 		数据
	 */
	public ModelStateContainer(int state, T data) {
		this.state = state;
		this.data = data;
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public void setData(T data) {
		this.data = data;
	}
}

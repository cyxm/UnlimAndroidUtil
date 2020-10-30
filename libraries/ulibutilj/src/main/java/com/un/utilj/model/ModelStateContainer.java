package com.un.utilj.model;

/**
 * 带状态的泛型模型
 *
 * @param <T>
 */
public class ModelStateContainer<T> {

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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

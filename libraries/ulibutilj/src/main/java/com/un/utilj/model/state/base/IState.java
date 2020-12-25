package com.un.utilj.model.state.base;

/**
 * 带状态的数据模型的通用接口
 *
 * @param <T>
 * 		存放的数据类型
 */
public interface IState<T> {
	/**
	 * 获取状态
	 *
	 * @return
	 */
	int getState();

	/**
	 * 设置状态
	 *
	 * @param state
	 */
	void setState(int state);

	/**
	 * 获取数据
	 *
	 * @return
	 */
	T getData();

	/**
	 * 设置数据
	 *
	 * @param data
	 */
	void setData(T data);
}

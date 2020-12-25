package com.un.utilj.model.state.base;

/**
 * 记忆上次状态的数据模型接口
 *
 * @param <T>
 */
public interface IStateRememberLast<T> extends IState<T> {

	/**
	 * 获取上次状态
	 *
	 * @return
	 */
	int getLastState();

	/**
	 * 设置上次状态
	 *
	 * @param state
	 */
	void setLastState(int state);

	/**
	 * 更改状态,需要记录上次状态
	 *
	 * @param state
	 */
	void changeState(int state);
}

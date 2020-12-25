package com.un.utilj.model.state.constant;

/**
 * 载入状态常亮,用于标识异步载入数据,如网络请求
 */
public class StateLoad {
	/**
	 * 初始化状态
	 */
	public static final int STATE_INIT = -1,
	/**
	 * 无数据
	 */
	STATE_NONE = 0,
	/**
	 * 加载中
	 */
	STATE_LOADING = 1,
	/**
	 * 加载失败
	 */
	STATE_FAILED = 2,
	/**
	 * 加载成功
	 */
	STATE_SUC = 3;
}
